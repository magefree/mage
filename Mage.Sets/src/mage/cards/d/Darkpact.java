package mage.cards.d;

import java.util.UUID;

import mage.abilities.effects.common.InfoEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author noahg
 */
public final class Darkpact extends CardImpl {

    public Darkpact(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{B}{B}{B}");
        

        // Remove Darkpact from your deck before playing if you're not playing for ante.
        // You own target card in the ante. Exchange that card with the top card of your library.
        this.getSpellAbility().addEffect(new InfoEffect("Remove Darkpact from your deck before playing if you're " +
                "not playing for ante.\nYou own target card in the ante. Exchange that card with the top card of" +
                " your library."));
    }

    public Darkpact(final Darkpact card) {
        super(card);
    }

    @Override
    public Darkpact copy() {
        return new Darkpact(this);
    }
}
