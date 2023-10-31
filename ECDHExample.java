import javax.crypto.KeyAgreement;
import java.security.*;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class ECDHExample {
    public static void main(String[] args) {
        try {
            // Initializing the ECDH KeyPairGenerator
            KeyPairGenerator kpg = KeyPairGenerator.getInstance("EC");
            kpg.initialize(256);

            // Generating KeyPair for Alice
            KeyPair kpA = kpg.generateKeyPair();
            PrivateKey privKeyA = kpA.getPrivate();
            PublicKey pubKeyA = kpA.getPublic();

            // Generating KeyPair for Bob
            KeyPair kpB = kpg.generateKeyPair();
            PrivateKey privKeyB = kpB.getPrivate();
            PublicKey pubKeyB = kpB.getPublic();

            // KeyAgreement for Alice
            KeyAgreement kaA = KeyAgreement.getInstance("ECDH");
            kaA.init(privKeyA);
            kaA.doPhase(pubKeyB, true);

            // KeyAgreement for Bob
            KeyAgreement kaB = KeyAgreement.getInstance("ECDH");
            kaB.init(privKeyB);
            kaB.doPhase(pubKeyA, true);

            // Generating and printing the shared secret for Alice and Bob
            byte[] sharedSecretA = kaA.generateSecret();
            byte[] sharedSecretB = kaB.generateSecret();

            System.out.println("Shared Secret (Alice): " + Base64.getEncoder().encodeToString(sharedSecretA));
            System.out.println("Shared Secret (Bob): " + Base64.getEncoder().encodeToString(sharedSecretB));

        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            e.printStackTrace();
        }
    }
}
