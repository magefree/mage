package mage.cards.r;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.target.TargetPermanent;
import mage.target.targetadjustment.VerseCounterAdjuster;

/**
 *
 * @author TheElk801
 */
public final class Recantation extends CardImpl {

    public Recantation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{U}{U}");

        // At the beginning of your upkeep, you may put a verse counter on Recantation.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD,
                new AddCountersSourceEffect(CounterType.VERSE.createInstance(), true), TargetController.YOU, true));

        // {U}, Sacrifice Recantation: Return up to X target permanents to their owners' hands, where X is the number of verse counters on Recantation.
        Effect effect = new ReturnToHandTargetEffect(true);
        effect.setText("Return up to X target permanents to their owners' hands, where X is the number of verse counters on {this}.");
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, effect, new ManaCostsImpl<>("{U}"));
        ability.addCost(new SacrificeSourceCost());
        ability.addTarget(new TargetPermanent(0, 0, new FilterPermanent("up to X target permanents, where X is the number of verse counters on {this}."), false));
        ability.setTargetAdjuster(VerseCounterAdjuster.instance);
        this.addAbility(ability);
    }

    private Recantation(final Recantation card) {
        super(card);
    }

    @Override
    public Recantation copy() {
        return new Recantation(this);
    }
}
