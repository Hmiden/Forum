export interface Report {
    id?: number;
    replyId: number;
    reason: string;
    status?: string;
    reportedAt?: string;
  }