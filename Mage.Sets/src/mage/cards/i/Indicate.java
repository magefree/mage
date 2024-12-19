package mage.cards.i;

import mage.abilities.effects.common.InfoEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author PurpleCrowbar
 */
public final class Indicate extends CardImpl {

    public Indicate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{0}");

        // Target permanent.
        this.getSpellAbility().addEffect(new InfoEffect("Target permanent."));
        this.getSpellAbility().addTarget(new TargetPermanent());
    }

    private Indicate(final Indicate card) {
        super(card);
    }

    @Override
    public Indicate copy() {
        return new Indicate(this);
    }
}
