
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Quercitron
 */
public final class PradeshGypsies extends CardImpl {

    public PradeshGypsies(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.NOMAD);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {1}{G}, {tap}: Target creature gets -2/-0 until end of turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostTargetEffect(-2, 0, Duration.EndOfTurn), new ManaCostsImpl<>("{1}{G}"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private PradeshGypsies(final PradeshGypsies card) {
        super(card);
    }

    @Override
    public PradeshGypsies copy() {
        return new PradeshGypsies(this);
    }
}
