
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.CanBlockOnlyFlyingAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageControllerEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;

/**
 *
 * @author LoneFox
 */
public final class StormcloudDjinn extends CardImpl {

    public StormcloudDjinn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{U}");
        this.subtype.add(SubType.DJINN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Stormcloud Djinn can block only creatures with flying.
        this.addAbility(new CanBlockOnlyFlyingAbility());
        // {R}{R}: Stormcloud Djinn gets +2/+0 until end of turn and deals 1 damage to you.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostSourceEffect(2, 0, Duration.EndOfTurn),
            new ManaCostsImpl<>("{R}{R}"));
        Effect effect = new DamageControllerEffect(1);
        effect.setText("and deals 1 damage to you");
        ability.addEffect(effect);
        this.addAbility(ability);
    }

    private StormcloudDjinn(final StormcloudDjinn card) {
        super(card);
    }

    @Override
    public StormcloudDjinn copy() {
        return new StormcloudDjinn(this);
    }
}
