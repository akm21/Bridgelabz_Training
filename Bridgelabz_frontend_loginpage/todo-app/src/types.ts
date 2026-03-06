export type Priority = "Low" | "Medium" | "High";

export interface Todo{
    id: string;
    title: string;
    description: string;
    priority: Priority;
    completed: boolean;
    createdAt: string;
}