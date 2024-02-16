
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;

/**
 * @author nantuko
 */
public final class SelflessCathar extends CardImpl {

    public SelflessCathar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {1}{W}, Sacrifice Selfless Cathar: Creatures you control get +1/+1 until end of turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostControlledEffect(1, 1, Duration.EndOfTurn), new ManaCostsImpl<>("{1}{W}"));
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private SelflessCathar(final SelflessCathar card) {
        super(card);
    }

    @Override
    public SelflessCathar copy() {
        return new SelflessCathar(this);
    }
}
