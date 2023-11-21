package factory;

import com.gabriel.desafiopicpay.domain.dto.request.TransactionRequest;
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

    public static final User NEW_USER_COMMON = newUserCommon();
    public static final User NEW_USER_STORE = newUserStore();
    public static final Wallet NEW_WALLET_WITH_BALANCE_100 = newWalletWithBalance100();
    public static final Wallet NEW_WALLET_WITH_BALANCE_0 = newWalletWithBalance0();
    public static final TransactionRequest NEW_TRANSACTION_REQUEST = newTransactionRequest();
    public static final Transaction NEW_TRANSACTION_SUCCESS = newTransactionSuccess();

    private static User newUserCommon() {
        return User.builder()
                .id(UUID.randomUUID())
                .name("TONY STARK")
                .document("99999999999")
                .email("tonystark@email.com")
                .password("123456")
                .userType(UserType.COMMON)
                .wallet(new Wallet(100))
                .transactions(Set.of(new Transaction()))
                .creationDate(LocalDateTime.now())
                .lastUpdateDate(LocalDateTime.now())
                .build();
    }

    private static User newUserStore() {
        return User.builder()
                .id(UUID.randomUUID())
                .name("TONY STARK")
                .document("99999999999")
                .email("tonystark@email.com")
                .password("123456")
                .userType(UserType.STORE)
                .wallet(new Wallet(100))
                .transactions(Set.of(new Transaction()))
                .creationDate(LocalDateTime.now())
                .lastUpdateDate(LocalDateTime.now())
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
                .payer(newUserCommon().getId())
                .payee(newUserStore().getId())
                .value(10)
                .status(StatusTransaction.SUCCESS)
                .flagEstorno(false)
                .user(newUserCommon())
                .creationDate(LocalDateTime.now())
                .lastUpdateDate(LocalDateTime.now())
                .build();
    }

 }
