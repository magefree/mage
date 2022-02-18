
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ColoredManaCost;
import mage.abilities.effects.common.RegenerateSourceEffect;
import mage.abilities.effects.common.SacrificeControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.ColoredManaSymbol;
import mage.constants.Zone;
import mage.filter.StaticFilters;

/**
 *
 * @author Loki
 */
public final class SpinedFluke extends CardImpl {

    public SpinedFluke(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}");
        this.subtype.add(SubType.WORM);
        this.subtype.add(SubType.HORROR);

        this.power = new MageInt(5);
        this.toughness = new MageInt(1);

        // When Spined Fluke enters the battlefield, sacrifice a creature.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SacrificeControllerEffect(StaticFilters.FILTER_PERMANENT_A_CREATURE, 1, "")));

        // {B}: Regenerate Spined Fluke.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new RegenerateSourceEffect(), new ColoredManaCost(ColoredManaSymbol.B)));
    }

    private SpinedFluke(final SpinedFluke card) {
        super(card);
    }

    @Override
    public SpinedFluke copy() {
        return new SpinedFluke(this);
    }
}
