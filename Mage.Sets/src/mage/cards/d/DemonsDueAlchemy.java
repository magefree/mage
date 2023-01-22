package mage.cards.d;

import java.util.UUID;

import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.PutCards;

/**
 *
 * @author weirddan455
 * @author chesse20
 */
public final class DemonsDueAlchemy extends CardImpl {

    public DemonsDueAlchemy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{B}");
        this.getSpellAbility().addEffect(new LookLibraryAndPickControllerEffect(4, 2, PutCards.HAND, PutCards.GRAVEYARD));
        this.getSpellAbility().addEffect(new LoseLifeSourceControllerEffect(2));
    }

    private DemonsDueAlchemy(final DemonsDueAlchemy card) {
        super(card);
    }

    @Override
    public DemonsDueAlchemy copy() {
        return new DemonsDueAlchemy(this);
    }
}
