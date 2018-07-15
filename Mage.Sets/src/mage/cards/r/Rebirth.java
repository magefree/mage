package mage.cards.r;

import java.util.UUID;

import mage.abilities.effects.common.InfoEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author noahg
 */
public final class Rebirth extends CardImpl {

    public Rebirth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{G}{G}{G}");
        

        // Remove Rebirth from your deck before playing if you're not playing for ante.
        // Each player may ante the top card of their library. If a player does, that player’s life total becomes 20.
        this.getSpellAbility().addEffect(new InfoEffect("Remove Rebirth from your deck before playing if you're " +
                "not playing for ante.\nEach player may ante the top card of their library. If a player does, " +
                "that player’s life total becomes 20."));
    }

    public Rebirth(final Rebirth card) {
        super(card);
    }

    @Override
    public Rebirth copy() {
        return new Rebirth(this);
    }
}
