
package mage.cards.a;

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
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class AquusSteed extends CardImpl {

    public AquusSteed(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{U}");
        this.subtype.add(SubType.BEAST);

        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // {2}{U}, {T}: Target creature gets -2/-0 until end of turn.
        Ability secondAbility  = new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostTargetEffect(-2, 0, Duration.EndOfTurn), new ManaCostsImpl<>("{2}{U}"));
        secondAbility.addCost(new TapSourceCost());
        secondAbility.addTarget(new TargetCreaturePermanent());
        this.addAbility(secondAbility);
    }

    private AquusSteed(final AquusSteed card) {
        super(card);
    }

    @Override
    public AquusSteed copy() {
        return new AquusSteed(this);
    }
}
