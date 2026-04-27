import { ResourceType } from "./resource-type";

export interface Resource {
    id?: number;
    title: string;
    description?: string;
    url: string;
  
    topicId: number;
    userId?: number;
    createdAt?: string;
    type: ResourceType; // ✅ FIX

    updatedAt?: string;
  }