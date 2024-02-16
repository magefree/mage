
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.target.TargetPlayer;

/**
 *
 * @author Loki
 */
public final class ThunderscapeMaster extends CardImpl {

    public ThunderscapeMaster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{R}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {B}{B}, {tap}: Target player loses 2 life and you gain 2 life.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new LoseLifeTargetEffect(2), new ManaCostsImpl<>("{B}{B}"));
        ability.addEffect(new GainLifeEffect(2).setText("and you gain 2 life"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
        // {G}{G}, {tap}: Creatures you control get +2/+2 until end of turn.
        ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostControlledEffect(2, 2, Duration.EndOfTurn), new ManaCostsImpl<>("{G}{G}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private ThunderscapeMaster(final ThunderscapeMaster card) {
        super(card);
    }

    @Override
    public ThunderscapeMaster copy() {
        return new ThunderscapeMaster(this);
    }
}
