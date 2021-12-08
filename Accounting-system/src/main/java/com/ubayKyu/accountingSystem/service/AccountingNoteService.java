package com.ubayKyu.accountingSystem.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ubayKyu.accountingSystem.repository.AccountingNoteRepository;

@Service
public class AccountingNoteService {
	@Autowired
	private AccountingNoteRepository repository;

	public Long notecnt() {
		return repository.Notecnt();
	}
	
	public LocalDateTime firstrecord() {
		return repository.first();
	}
	
	public LocalDateTime lastrecord() {
		return repository.last();
	}
}
