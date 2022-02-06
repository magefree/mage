package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.StateTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.ExileSourceCost;
import mage.abilities.costs.common.PutCountersSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MazemindTome extends CardImpl {

    public MazemindTome(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        // {T}, Put a page counter on Mazemind Tome: Scry 1.
        Ability ability = new SimpleActivatedAbility(new ScryEffect(1, false), new TapSourceCost());
        ability.addCost(new PutCountersSourceCost(CounterType.PAGE.createInstance()));
        this.addAbility(ability);

        // {2}, {T}, Put a page counter on Mazemind Tome: Draw a card.
        ability = new SimpleActivatedAbility(new DrawCardSourceControllerEffect(1), new GenericManaCost(2));
        ability.addCost(new TapSourceCost());
        ability.addCost(new PutCountersSourceCost(CounterType.PAGE.createInstance()));
        this.addAbility(ability);

        // When there are four or more page counters on Mazemind Tome, exile it. If you do, you gain 4 life.
        this.addAbility(new MazemindTomeTriggeredAbility());
    }

    private MazemindTome(final MazemindTome card) {
        super(card);
    }

    @Override
    public MazemindTome copy() {
        return new MazemindTome(this);
    }
}

class MazemindTomeTriggeredAbility extends StateTriggeredAbility {

    MazemindTomeTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DoIfCostPaid(
                new GainLifeEffect(4), new ExileSourceCost(), null, false
        ));
    }

    private MazemindTomeTriggeredAbility(final MazemindTomeTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public MazemindTomeTriggeredAbility copy() {
        return new MazemindTomeTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent permanent = game.getPermanent(getSourceId());
        return permanent != null && permanent.getCounters(game).getCount(CounterType.PAGE) >= 4;
    }

    @Override
    public String getRule() {
        return "When there are four or more page counters on {this}, exile it. If you do, you gain 4 life.";
    }

}
