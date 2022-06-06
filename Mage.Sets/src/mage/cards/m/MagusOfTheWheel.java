
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DrawCardAllEffect;
import mage.abilities.effects.common.discard.DiscardHandAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

/**
 *
 * @author fireshoes
 */
public final class MagusOfTheWheel extends CardImpl {

    public MagusOfTheWheel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // {1}{R}, {T}, Sacrifice Magus of the Wheel: Each player discards their hand, then draws seven cards.
        Effect effect = new DrawCardAllEffect(7);
        effect.setText(", then draws seven cards");
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DiscardHandAllEffect(), new ManaCostsImpl<>("{1}{R}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        ability.addEffect(effect);
        this.addAbility(ability);
    }

    private MagusOfTheWheel(final MagusOfTheWheel card) {
        super(card);
    }

    @Override
    public MagusOfTheWheel copy() {
        return new MagusOfTheWheel(this);
    }
}
