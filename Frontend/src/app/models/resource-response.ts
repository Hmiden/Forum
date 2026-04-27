import { ResourceType } from "./resource-type";

export interface ResourceResponse {
    id: number;
    title: string;
    description: string;
    url: string;
    
    topicId: number;
    topicName: string; // 🔥 important
    type: ResourceType; // ✅ FIX

  }