package mage.cards.p;

import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldWithCounterTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.filter.common.FilterPermanentCard;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Perennation extends CardImpl {

    private static final FilterCard filter = new FilterPermanentCard("permanent card from your graveyard");

    public Perennation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{W}{B}{G}");

        // Return target permanent card from your graveyard to the battlefield with a hexproof counter and an indestructible counter on it.
        this.getSpellAbility().addEffect(new ReturnFromGraveyardToBattlefieldWithCounterTargetEffect(
                CounterType.HEXPROOF.createInstance(), CounterType.INDESTRUCTIBLE.createInstance()
        ));
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(filter));
    }

    private Perennation(final Perennation card) {
        super(card);
    }

    @Override
    public Perennation copy() {
        return new Perennation(this);
    }
}
