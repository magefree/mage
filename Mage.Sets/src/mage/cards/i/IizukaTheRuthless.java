
package mage.cards.i;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.BushidoAbility;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author Loki
 */
public final class IizukaTheRuthless extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("Samurai");
    private static final FilterControlledCreaturePermanent filter2 = new FilterControlledCreaturePermanent("Samurai creatures");

    static {
        filter.add(SubType.SAMURAI.getPredicate());
        filter2.add(SubType.SAMURAI.getPredicate());
    }

    public IizukaTheRuthless(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{R}{R}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SAMURAI);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);
        this.addAbility(new BushidoAbility(2));
        // {2}{R}, Sacrifice a Samurai: Samurai creatures you control gain double strike until end of turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new GainAbilityControlledEffect(DoubleStrikeAbility.getInstance(), Duration.EndOfTurn, filter2, false), new ManaCostsImpl<>("{2}{R}"));
        ability.addCost(new SacrificeTargetCost(new TargetControlledCreaturePermanent(1, 1, filter, true)));
        this.addAbility(ability);
    }

    private IizukaTheRuthless(final IizukaTheRuthless card) {
        super(card);
    }

    @Override
    public IizukaTheRuthless copy() {
        return new IizukaTheRuthless(this);
    }
}
