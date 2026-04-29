package br.com.freelancehub.freelancehub.application.usecases;

import br.com.freelancehub.freelancehub.domain.User;
import br.com.freelancehub.freelancehub.domain.repository.UserRepository;

public class DeleteUserUseCase {

    private final UserRepository userRepository;

    public DeleteUserUseCase(
            UserRepository userRepository
    ) {
        this.userRepository = userRepository;
    }

    public void execute(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        user.deleteUser();
        userRepository.deleteUserById(userId);
    }

}
