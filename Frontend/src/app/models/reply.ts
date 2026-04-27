export interface Reply {
  id?: number;
  content: string;
  createdAt?: string;
  topicId: number;      // pour savoir à quel topic appartient la réponse
}