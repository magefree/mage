package mage.cards.c;

import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.card.PutIntoGraveFromBattlefieldThisTurnPredicate;
import mage.target.common.TargetCardInYourGraveyard;
import mage.watchers.common.CardsPutIntoGraveyardWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Continue extends CardImpl {

    private static final FilterCard filter = new FilterCreatureCard(
            "creature cards in your graveyard that were put there from the battlefield this turn"
    );

    static {
        filter.add(PutIntoGraveFromBattlefieldThisTurnPredicate.instance);
    }

    public Continue(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{W}");

        // Choose up to four target creature cards in your graveyard that were put there from the battlefield this turn. Return them to the battlefield.
        this.getSpellAbility().addEffect(
                new ReturnFromGraveyardToBattlefieldTargetEffect()
                        .setText("choose up to four target creature cards in your graveyard " +
                                "that were put there from the battlefield this turn. " +
                                "Return them to the battlefield")
        );
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(0, 4, filter));
        this.getSpellAbility().addWatcher(new CardsPutIntoGraveyardWatcher());
    }

    private Continue(final Continue card) {
        super(card);
    }

    @Override
    public Continue copy() {
        return new Continue(this);
    }
}
