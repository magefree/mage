package mage.cards.g;

import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.card.PutIntoGraveFromBattlefieldThisTurnPredicate;
import mage.target.common.TargetCardInGraveyard;
import mage.watchers.common.CardsPutIntoGraveyardWatcher;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class GrimReturn extends CardImpl {

    private static final FilterCard filter = new FilterCreatureCard(
            "creature card in a graveyard that was put there from the battlefield this turn"
    );

    static {
        filter.add(PutIntoGraveFromBattlefieldThisTurnPredicate.instance);
    }

    public GrimReturn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{B}");

        // Choose target creature card in a graveyard that was put there from the battlefield this turn. Put that card onto the battlefield under your control.
        this.getSpellAbility().addEffect(new ReturnFromGraveyardToBattlefieldTargetEffect()
                .setText("Choose target creature card in a graveyard that was put there from the " +
                        "battlefield this turn. Put that card onto the battlefield under your control"));
        this.getSpellAbility().addTarget(new TargetCardInGraveyard(filter));
        this.getSpellAbility().addWatcher(new CardsPutIntoGraveyardWatcher());
    }

    private GrimReturn(final GrimReturn card) {
        super(card);
    }

    @Override
    public GrimReturn copy() {
        return new GrimReturn(this);
    }
}
