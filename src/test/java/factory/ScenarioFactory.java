package factory;

import com.gabriel.desafiopicpay.domain.dto.request.TransactionRequest;
import com.gabriel.desafiopicpay.domain.dto.request.UserRequest;
import com.gabriel.desafiopicpay.domain.dto.response.TransactionResponse;
import com.gabriel.desafiopicpay.domain.model.Transaction;
import com.gabriel.desafiopicpay.domain.model.User;
import com.gabriel.desafiopicpay.domain.model.Wallet;
import com.gabriel.desafiopicpay.domain.model.enums.StatusTransaction;
import com.gabriel.desafiopicpay.domain.model.enums.UserType;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

public class ScenarioFactory {

    private ScenarioFactory () {}

    public static final User NEW_USER_COMMON_WITH_BALANCE_100 = newUserCommonWithBalance100();
    public static final User NEW_USER_COMMON_WITH_BALANCE_0 = newUserCommonWithBalance0();
    public static final User NEW_USER_STORE_WITH_BALANCE_0 = newUserStoreWithBalance0();
    public static final User NEW_USER_STORE_WITH_BALANCE_100 = newUserStoreWithBalance100();
    public static final Wallet NEW_WALLET_WITH_BALANCE_100 = newWalletWithBalance100();
    public static final Wallet NEW_WALLET_WITH_BALANCE_0 = newWalletWithBalance0();
    public static final TransactionRequest NEW_TRANSACTION_REQUEST_WITH_VALUE_10 = newTransactionRequest();
    public static final Transaction NEW_TRANSACTION_SUCCESS = newTransactionSuccess();
    public static final UserRequest NEW_USER_REQUEST = newUserRequest();
    public static final User NEW_USER_MAPPER = newUserMapper();
    public static final Transaction NEW_TRANSACTION_BUILD_CREATED = newTransactionBuildCreated();

    private static User newUserCommonWithBalance100() {
        return User.builder()
                .id(UUID.randomUUID())
                .name("TONY STARK")
                .document("99999999999")
                .email("tonystark@email.com")
                .password("123456")
                .userType(UserType.COMMON)
                .wallet(newWalletWithBalance100())
                .transactions(Set.of(new Transaction()))
                .creationDate(LocalDateTime.now())
                .lastUpdateDate(LocalDateTime.now())
                .build();
    }

    private static User newUserCommonWithBalance0() {
        return User.builder()
                .id(UUID.randomUUID())
                .name("TONY STARK")
                .document("99999999999")
                .email("tonystark@email.com")
                .password("123456")
                .userType(UserType.COMMON)
                .wallet(newWalletWithBalance0())
                .transactions(Set.of(new Transaction()))
                .creationDate(LocalDateTime.now())
                .lastUpdateDate(LocalDateTime.now())
                .build();
    }

    private static User newUserStoreWithBalance100() {
        return User.builder()
                .id(UUID.randomUUID())
                .name("TONY STARK")
                .document("99999999999")
                .email("tonystark@email.com")
                .password("123456")
                .userType(UserType.STORE)
                .wallet(newWalletWithBalance100())
                .transactions(Set.of(new Transaction()))
                .creationDate(LocalDateTime.now())
                .lastUpdateDate(LocalDateTime.now())
                .build();
    }

    private static User newUserStoreWithBalance0() {
        return User.builder()
                .id(UUID.randomUUID())
                .name("TONY STARK")
                .document("99999999999")
                .email("tonystark@email.com")
                .password("123456")
                .userType(UserType.STORE)
                .wallet(newWalletWithBalance0())
                .transactions(Set.of(new Transaction()))
                .creationDate(LocalDateTime.now())
                .lastUpdateDate(LocalDateTime.now())
                .build();
    }



    private static User newUserMapper() {
        return User.builder()
                .name("TONY STARK")
                .document("99999999999")
                .email("tonystark@email.com")
                .password("123456")
                .userType(UserType.COMMON)
                .build();
    }

    private static Wallet newWalletWithBalance100() {
        return Wallet.builder()
                .id(UUID.randomUUID())
                .balance(100)
                .creationDate(LocalDateTime.now())
                .lastUpdateDate(LocalDateTime.now())
                .build();
    }

    private static Wallet newWalletWithBalance0() {
        return Wallet.builder()
                .id(UUID.randomUUID())
                .balance(0)
                .creationDate(LocalDateTime.now())
                .lastUpdateDate(LocalDateTime.now())
                .build();
    }

    private static TransactionRequest newTransactionRequest() {
        return new TransactionRequest(10, UUID.randomUUID(), UUID.randomUUID());
    }

    private static Transaction newTransactionSuccess() {
        return Transaction.builder()
                .id(UUID.randomUUID())
                .payer(newUserCommonWithBalance100().getId())
                .payee(newUserStoreWithBalance0().getId())
                .value(10)
                .status(StatusTransaction.SUCCESS)
                .flagEstorno(false)
                .user(newUserCommonWithBalance100())
                .creationDate(LocalDateTime.now())
                .lastUpdateDate(LocalDateTime.now())
                .build();
    }

    private static UserRequest newUserRequest() {
        return new UserRequest("TONY STARK",
                "99999999",
                "gabriel@email.com",
                "123456",
                UserType.COMMON,
                100);
    }

    private static Transaction newTransactionBuildCreated() {
        return Transaction.builder()
                .payer(newUserCommonWithBalance100().getId())
                .payee(newUserStoreWithBalance0().getId())
                .user(newUserCommonWithBalance100())
                .value(10)
                .status(StatusTransaction.SUCCESS)
                .flagEstorno(false)
                .build();
    }

    public static TransactionResponse newTransactionResponseDynamicParams(Transaction transaction, User payer,
                                                                          User payee) {
        return new TransactionResponse(transaction.getId(),
                payer.getName(),
                payee.getName(),
                transaction.getValue(),
                StatusTransaction.SUCCESS);
    }

}
