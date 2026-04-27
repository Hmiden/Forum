export interface Category {
  id?: number;          // optionnel lors de la création
  name: string;
  description?: string;
  createdAt?: string;
  topicCount?: number; 
}