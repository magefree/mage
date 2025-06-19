
package mage.cards.o;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetLandPermanent;
import mage.target.targetadjustment.XTargetsCountAdjuster;

import java.util.UUID;

/**
 *
 * @author jeffwadsworth
 *
 */
public final class OrcishSettlers extends CardImpl {

    public OrcishSettlers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");
        this.subtype.add(SubType.ORC);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {X}{X}{R}, {tap}, Sacrifice Orcish Settlers: Destroy X target lands.
        Ability ability = new SimpleActivatedAbility(new DestroyTargetEffect().setText("Destroy X target lands"),
                new ManaCostsImpl<>("{X}{X}{R}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        ability.addTarget(new TargetLandPermanent());
        ability.setTargetAdjuster(new XTargetsCountAdjuster());
        this.addAbility(ability);
    }

    private OrcishSettlers(final OrcishSettlers card) {
        super(card);
    }

    @Override
    public OrcishSettlers copy() {
        return new OrcishSettlers(this);
    }
}
