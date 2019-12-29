package mage.cards.b;

import mage.MageObjectReference;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.filter.common.FilterPermanentCard;
import mage.filter.predicate.Predicate;
import mage.game.Game;
import mage.target.common.TargetCardInYourGraveyard;
import mage.watchers.common.CardsPutIntoGraveyardWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BroughtBack extends CardImpl {

    private static final FilterCard filter = new FilterPermanentCard(
            "permanent cards in your graveyard that were put there from the battlefield this turn"
    );

    static {
        filter.add(BroughtBackPredicate.instance);
    }

    public BroughtBack(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{W}{W}");

        // Choose up to two target permanent cards in your graveyard that were put there from the battlefield this turn. Return them to the battlefield tapped.
        this.getSpellAbility().addEffect(
                new ReturnFromGraveyardToBattlefieldTargetEffect(true)
                        .setText("Choose up to two target permanent cards in your graveyard " +
                                "that were put there from the battlefield this turn. " +
                                "Return them to the battlefield tapped.")
        );
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(0, 2, filter));
        this.getSpellAbility().addWatcher(new CardsPutIntoGraveyardWatcher());
    }

    private BroughtBack(final BroughtBack card) {
        super(card);
    }

    @Override
    public BroughtBack copy() {
        return new BroughtBack(this);
    }
}

enum BroughtBackPredicate implements Predicate<Card> {
    instance;

    @Override
    public boolean apply(Card input, Game game) {
        CardsPutIntoGraveyardWatcher watcher = game.getState().getWatcher(CardsPutIntoGraveyardWatcher.class);
        return watcher != null
                && watcher.getCardsPutToGraveyardFromBattlefield().contains(new MageObjectReference(input, game));
    }
}
