
package mage.cards.m;

import java.util.UUID;
import mage.abilities.effects.common.ExileGraveyardAllPlayersEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author Jgod
 */
public final class Morningtide extends CardImpl {

    public Morningtide(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{1}{W}");

        // Exile all cards from all graveyards.
        this.getSpellAbility().addEffect(new ExileGraveyardAllPlayersEffect());
    }

    private Morningtide(final Morningtide card) {
        super(card);
    }

    @Override
    public Morningtide copy() {
        return new Morningtide(this);
    }
}