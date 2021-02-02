
package mage.cards.c;

import java.util.UUID;
import mage.Mana;
import mage.abilities.condition.common.CardsInControllerGraveyardCondition;
import mage.abilities.decorator.ConditionalManaEffect;
import mage.abilities.effects.mana.BasicManaEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author Plopman
 */
public final class CabalRitual extends CardImpl {

    public CabalRitual(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{B}");


        // Add {B}{B}{B}.
        // Threshold — Add {B}{B}{B}{B}{B} instead if seven or more cards are in your graveyard.
        this.getSpellAbility().addEffect(new ConditionalManaEffect(
                new BasicManaEffect(Mana.BlackMana(5)),
                new BasicManaEffect(Mana.BlackMana(3)),
                new CardsInControllerGraveyardCondition(7),
                "Add {B}{B}{B}.<br/><br/><i>Threshold</i> &mdash; Add {B}{B}{B}{B}{B} instead if seven or more cards are in your graveyard"));
    }

    private CabalRitual(final CabalRitual card) {
        super(card);
    }

    @Override
    public CabalRitual copy() {
        return new CabalRitual(this);
    }
}
