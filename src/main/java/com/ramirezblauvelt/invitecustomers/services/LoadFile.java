package com.ramirezblauvelt.invitecustomers.services;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Service
public class LoadFile {

	public List<String> readFile(Path filePath) throws IOException {
		if(Files.notExists(filePath)) {
			throw new IOException("File not found");
		}

		if(Files.size(filePath) == 0) {
			throw new IOException("File empty");
		}

		return Files.readAllLines(filePath);
	}

}
