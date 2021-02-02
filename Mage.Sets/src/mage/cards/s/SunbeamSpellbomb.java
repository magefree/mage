
package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ColoredManaCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ColoredManaSymbol;
import mage.constants.Zone;

/**
 *
 * @author Loki
 */
public final class SunbeamSpellbomb extends CardImpl {

    public SunbeamSpellbomb(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{1}");
        Ability firstAbility = new SimpleActivatedAbility(Zone.BATTLEFIELD, new GainLifeEffect(5), new ColoredManaCost(ColoredManaSymbol.W));
        firstAbility.addCost(new SacrificeSourceCost());
        this.addAbility(firstAbility);
        Ability secondAbility = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(1), new GenericManaCost(1));
        secondAbility.addCost(new SacrificeSourceCost());
        this.addAbility(secondAbility);
    }

    private SunbeamSpellbomb(final SunbeamSpellbomb card) {
        super(card);
    }

    @Override
    public SunbeamSpellbomb copy() {
        return new SunbeamSpellbomb(this);
    }
}
