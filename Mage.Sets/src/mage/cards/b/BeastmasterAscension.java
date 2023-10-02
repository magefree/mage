

package mage.cards.b;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.AttacksCreatureYouControlTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class BeastmasterAscension extends CardImpl {

    public BeastmasterAscension(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{G}");


        this.addAbility(new AttacksCreatureYouControlTriggeredAbility(new AddCountersSourceEffect(CounterType.QUEST.createInstance()), true));
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BeastmasterAscensionEffect()));
    }

    private BeastmasterAscension(final BeastmasterAscension card) {
        super(card);
    }

    @Override
    public BeastmasterAscension copy() {
        return new BeastmasterAscension(this);
    }

}

class BeastmasterAscensionEffect extends BoostControlledEffect {

    public BeastmasterAscensionEffect() {
        super(5, 5, Duration.WhileOnBattlefield);
        staticText = "As long as {this} has seven or more quest counters on it, creatures you control get +5/+5";
    }

    private BeastmasterAscensionEffect(final BeastmasterAscensionEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent != null && permanent.getCounters(game).getCount(CounterType.QUEST) > 6) {
            super.apply(game, source);
        }
        return false;
    }

    @Override
    public BeastmasterAscensionEffect copy() {
        return new BeastmasterAscensionEffect(this);
    }

}