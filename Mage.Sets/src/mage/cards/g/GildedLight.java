
package mage.cards.g;

import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.GainAbilityControllerEffect;
import mage.abilities.keyword.CyclingAbility;
import mage.abilities.keyword.ShroudAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public final class GildedLight extends CardImpl {

    public GildedLight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{W}");


        // You gain shroud until end of turn.
        this.getSpellAbility().addEffect(new GainAbilityControllerEffect(ShroudAbility.getInstance(), Duration.EndOfTurn));

        // Cycling {2}
        this.addAbility(new CyclingAbility(new ManaCostsImpl<>("{2}")));
    }

    private GildedLight(final GildedLight card) {
        super(card);
    }

    @Override
    public GildedLight copy() {
        return new GildedLight(this);
    }
}
