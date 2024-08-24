package com.example.myApplication.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.myApplication.Entity.BookService;
import com.example.myApplication.Entity.BookedServiceStatus;
import com.example.myApplication.Entity.ServiceProvider;

@Repository
public interface BookedServiceStatusRepository extends JpaRepository<BookedServiceStatus , Integer> {
	BookedServiceStatus findByBookService(BookService bookService);

	BookedServiceStatus findByBookServiceAndServiceProvider(BookService bookService, ServiceProvider serviceProvider);
}