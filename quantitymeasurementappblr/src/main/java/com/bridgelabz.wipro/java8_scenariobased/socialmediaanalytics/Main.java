package com.bridgelabz.wipro.java8_scenariobased.socialmediaanalytics;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {
    private static List<Post> posts = Arrays.asList(
            new Post("A", Arrays.asList("#java", "#spring"), 100),
            new Post("B", List.of("#java"), 200),
            new Post("C", List.of("#microservices"), 150),
            new Post("A", List.of("#java"), 50)
    );

    public static void main(String[] args) {
        Map<String, Long> hashtagCount = posts.stream()
                .flatMap(p -> p.getHashtags().stream())
                .collect(Collectors.groupingBy(h -> h, Collectors.counting()));
        Map.Entry<String, Long> mostUsedHashtag = hashtagCount.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .orElse(null);
        System.out.println("Most used hashtag: " + mostUsedHashtag.getKey() + " with count: " + mostUsedHashtag.getValue());

        Map<String, Integer> totalLikesPerUser = posts.stream()
                .collect(Collectors.groupingBy(Post::getUser, Collectors.summingInt(Post::getLikes)));
        totalLikesPerUser.forEach((user, likes) -> System.out.println("User: " + user + ", Total Likes: " + likes));

        List<Map.Entry<String, Long>> top2Trending = posts.stream().flatMap(post -> post.getHashtags().
                        stream()).collect(Collectors.groupingBy(tag -> tag, Collectors.counting()))
                .entrySet().stream().sorted(Map.Entry.<String, Long>comparingByValue().
                        reversed()).limit(2).toList();
        System.out.println("Top 2 trending hashtags:");
        top2Trending.forEach(entry -> System.out.println(entry.getKey() + " with count: " + entry.getValue()));

        List<String> usersWithJavaPosts = posts.stream()
                .filter(p -> p.getHashtags().contains("#java"))
                .map(Post::getUser)
                .distinct()
                .toList();
        System.out.println("Users who posted with #java: " + usersWithJavaPosts);
    }
}
