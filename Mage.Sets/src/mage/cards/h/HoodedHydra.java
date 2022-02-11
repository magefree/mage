
package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.effects.AsTurnedFaceUpEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.EntersBattlefieldWithXCountersEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.MorphAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.permanent.token.SnakeToken;

/**
 *
 * @author LevelX2
 */
public final class HoodedHydra extends CardImpl {

    public HoodedHydra(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{X}{G}{G}");
        this.subtype.add(SubType.SNAKE);
        this.subtype.add(SubType.HYDRA);

        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Hooded Hydra enters the battlefield with X +1/+1 counters on it.
        this.addAbility(new EntersBattlefieldAbility(new EntersBattlefieldWithXCountersEffect(CounterType.P1P1.createInstance())));

        // When Hooded Hydra dies, create a 1/1 green Snake creature token for each +1/+1 counter on it.
        this.addAbility(new DiesSourceTriggeredAbility(new CreateTokenEffect(new SnakeToken(), new CountersSourceCount(CounterType.P1P1)), false));

        // Morph {3}{G}{G}
        this.addAbility(new MorphAbility(this, new ManaCostsImpl("{3}{G}{G}")));

        // As Hooded Hydra is turned face up, put five +1/+1 counters on it.
        Effect effect = new AddCountersSourceEffect(CounterType.P1P1.createInstance(5));
        effect.setText("put five +1/+1 counters on it");
        // TODO: Does not work because the ability is still removed from permanent while the effect checks if the ability still exists.
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new AsTurnedFaceUpEffect(effect, false));
        ability.setWorksFaceDown(true);
        this.addAbility(ability);
    }

    private HoodedHydra(final HoodedHydra card) {
        super(card);
    }

    @Override
    public HoodedHydra copy() {
        return new HoodedHydra(this);
    }
}
