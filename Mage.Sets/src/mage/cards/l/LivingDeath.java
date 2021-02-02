package mage.cards.l;

import java.util.UUID;

import mage.abilities.effects.common.LivingDeathEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author Plopman
 */
public final class LivingDeath extends CardImpl {

    public LivingDeath(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{B}{B}");

        // Each player exiles all creature cards from their graveyard, then sacrifices all creatures they control, then puts all cards they exiled this way onto the battlefield.
        this.getSpellAbility().addEffect(new LivingDeathEffect());
    }

    private LivingDeath(final LivingDeath card) {
        super(card);
    }

    @Override
    public LivingDeath copy() {
        return new LivingDeath(this);
    }
}
