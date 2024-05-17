package factory;

import com.gabriel.desafiopicpay.api.client.dto.AuthorizedResponse;
import com.gabriel.desafiopicpay.domain.dto.request.TransactionRequest;
import com.gabriel.desafiopicpay.domain.dto.request.UserRequest;
import com.gabriel.desafiopicpay.domain.dto.response.TransactionResponse;
import com.gabriel.desafiopicpay.domain.model.Transaction;
import com.gabriel.desafiopicpay.domain.model.User;
import com.gabriel.desafiopicpay.domain.model.Wallet;
import com.gabriel.desafiopicpay.domain.model.enums.StatusTransaction;
import com.gabriel.desafiopicpay.domain.model.enums.UserType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

public class ScenarioFactory {

    private ScenarioFactory() {}


    private static User.UserBuilder newUser() {
        return User.builder()
                .id(1)
                .name("TONY STARK")
                .document("99999999999")
                .email("tonystark@email.com")
                .password("123456")
                .transactions(Set.of(new Transaction()))
                .creationDate(LocalDateTime.now())
                .lastUpdateDate(LocalDateTime.now());
    }

    public static User newUserCommonWithBalance100() {
        return newUser()
                .userType(UserType.COMMON)
                .wallet(newWalletWithBalance100())
                .build();

    }

    public static User newUserCommonWithBalance0() {
        return newUser()
                .userType(UserType.COMMON)
                .wallet(newWalletWithBalance0())
                .build();
    }

    public static User newUserStoreWithBalance100() {
        return newUser()
                .userType(UserType.STORE)
                .wallet(newWalletWithBalance100())
                .build();
    }

    public static User newUserStoreWithBalance0() {
        return newUser()
                .userType(UserType.STORE)
                .wallet(newWalletWithBalance0())
                .build();
    }


    public static User newUserMapper() {
        return User.builder()
                .name("TONY STARK")
                .document("99999999999")
                .email("tonystark@email.com")
                .password("123456")
                .userType(UserType.COMMON)
                .build();
    }

    private static Wallet.WalletBuilder newWallet() {
        return Wallet.builder()
                .id(UUID.randomUUID())
                .creationDate(LocalDateTime.now())
                .lastUpdateDate(LocalDateTime.now());
    }

    public static Wallet newWalletWithBalance100() {
        return newWallet()
                .balance(BigDecimal.valueOf(100))
                .build();
    }

    public static Wallet newWalletWithBalance0() {
        return newWallet()
                .balance(BigDecimal.ZERO)
                .build();
    }

    public static TransactionRequest newTransactionRequestWithValue10() {
        return new TransactionRequest(BigDecimal.TEN, 1, 2);
    }

    public static Transaction newTransactionSuccess() {
        return Transaction.builder()
                .id(UUID.randomUUID())
                .payer(newUserCommonWithBalance100().getId())
                .payee(newUserStoreWithBalance0().getId())
                .amount(BigDecimal.TEN)
                .status(StatusTransaction.SUCCESS)
                .flagEstorno(false)
                .user(newUserCommonWithBalance100())
                .creationDate(LocalDateTime.now())
                .lastUpdateDate(LocalDateTime.now())
                .build();
    }

    public static UserRequest newUserRequest() {
        return new UserRequest("PETER PARK",
                "44444444444",
                "peter@email.com",
                "peter123",
                UserType.COMMON,
                BigDecimal.valueOf(100));
    }

    public static Transaction newTransactionBuildCreated() {
        return Transaction.builder()
                .payer(newUserCommonWithBalance100().getId())
                .payee(newUserStoreWithBalance0().getId())
                .user(newUserCommonWithBalance100())
                .amount(BigDecimal.TEN)
                .status(StatusTransaction.SUCCESS)
                .flagEstorno(false)
                .build();
    }

    public static TransactionResponse newTransactionResponseDynamicParams(Transaction transaction, User payer,
                                                                          User payee) {
        return new TransactionResponse(transaction.getId(),
                payer.getName(),
                payee.getName(),
                transaction.getAmount(),
                StatusTransaction.SUCCESS);
    }

    public static AuthorizedResponse newAuthorizedResponseWithStatusAuthorized() {
        return new AuthorizedResponse("Authorized");
    }

    public static AuthorizedResponse newAuthorizedResponseWithStatusRandom() {
        return new AuthorizedResponse("teste");
    }


}
