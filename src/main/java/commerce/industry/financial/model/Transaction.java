package commerce.industry.financial.model;

import commerce.event.EventListeners;
import commerce.event.PurchaseEvent;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

import java.util.Collection;
import java.util.Set;

@UtilityClass
public class Transaction {

    private static final EventListeners.GlobalEventListener listener = EventListeners.getInstance();

    public static void assign(@NonNull BusinessEntity seller,  @NonNull Collection<Valuation> saleItems) {
        seller.addAssets(saleItems);
    }

    public static void purchase(@NonNull BusinessEntity seller, @NonNull Collection<Valuation> saleItems,
                                @NonNull BusinessEntity buyer,  @NonNull Collection<Valuation> purchaseInstruments) {
        Double totalOffer = saleItems.stream().map(Valuation::getPrice).reduce(0.0, Double::sum);
        Double totalBid = purchaseInstruments.stream().map(Valuation::getPrice).reduce(0.0, Double::sum);
        if (totalBid >= totalOffer) {
            saleItems.forEach(saleItem -> {
                seller.removeAsset(saleItem);
                buyer.addAsset(saleItem);
            });
            seller.addAssets(purchaseInstruments);
            listener.publish(PurchaseEvent.builder().build());
        }
    }
    public static void purchase(@NonNull BusinessEntity seller, @NonNull Valuation saleItem, @NonNull BusinessEntity buyer,
                                @NonNull Collection<Valuation> purchaseInstruments) {
        purchase(seller, Set.of(saleItem), buyer, purchaseInstruments);
    }

    public static void purchase(@NonNull BusinessEntity seller, @NonNull Collection<Valuation> saleItems, @NonNull BusinessEntity buyer,
                                @NonNull Valuation purchaseInstrument) {
        purchase(seller, saleItems, buyer, Set.of(purchaseInstrument));
    }

    public static void purchase(@NonNull BusinessEntity seller, @NonNull Valuation saleItem, @NonNull BusinessEntity buyer,
                                @NonNull Valuation purchaseInstrument) {
        purchase(seller, Set.of(saleItem), buyer, Set.of(purchaseInstrument));
    }
}
