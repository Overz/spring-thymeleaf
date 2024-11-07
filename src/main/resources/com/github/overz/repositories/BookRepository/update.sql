UPDATE Book
SET nmBook        = :nmBook,
		nmAuthor      = :nmAuthor,
		dsDescription = :dsDescription,
		dtCreatedAt   = :dtCreatedAt,
		dtPublishedAt = :dtPublishedAt
WHERE cdBook = :cdBook;