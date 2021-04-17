package mage.cards.n;

import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.effects.common.ReturnToHandFromGraveyardAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.card.PutIntoGraveFromBattlefieldThisTurnPredicate;
import mage.watchers.common.CardsPutIntoGraveyardWatcher;

import java.util.UUID;

/**
 * @author anonymous
 */
public final class NoRestForTheWicked extends CardImpl {

    private static final FilterCard filter = new FilterCreatureCard();

    static {
        filter.add(PutIntoGraveFromBattlefieldThisTurnPredicate.instance);
    }

    public NoRestForTheWicked(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{B}");

        // Sacrifice No Rest for the Wicked: Return to your hand all creature cards in your graveyard that were put there from the battlefield this turn.
        this.addAbility(new SimpleActivatedAbility(
                new ReturnToHandFromGraveyardAllEffect(filter, TargetController.YOU)
                        .setText("return to your hand all creature cards in your graveyard " +
                                "that were put there from the battlefield this turn"),
                new SacrificeSourceCost()
        ), new CardsPutIntoGraveyardWatcher());
    }

    private NoRestForTheWicked(final NoRestForTheWicked card) {
        super(card);
    }

    @Override
    public NoRestForTheWicked copy() {
        return new NoRestForTheWicked(this);
    }
}
