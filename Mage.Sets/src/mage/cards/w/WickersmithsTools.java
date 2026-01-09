package mage.cards.w;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.mana.AnyColorManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.ScarecrowToken;

/**
 *
 * @author muz
 */
public final class WickersmithsTools extends CardImpl {

    public WickersmithsTools(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // Whenever one or more -1/-1 counters are put on a creature, put a charge counter on this artifact.
        this.addAbility(new WickersmithsToolsTriggeredAbility());

        // {T}: Add one mana of any color.
        this.addAbility(new AnyColorManaAbility());

        // {5}, {T}, Sacrifice this artifact: Create X tapped 2/2 colorless Scarecrow artifact creature tokens, where X is the number of charge counters on this artifact.
        Ability ability = new SimpleActivatedAbility(new CreateTokenEffect(new ScarecrowToken(), new CountersSourceCount(CounterType.CHARGE)), new ManaCostsImpl<>("{5}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private WickersmithsTools(final WickersmithsTools card) {
        super(card);
    }

    @Override
    public WickersmithsTools copy() {
        return new WickersmithsTools(this);
    }
}

class WickersmithsToolsTriggeredAbility extends TriggeredAbilityImpl {

    WickersmithsToolsTriggeredAbility() {
        super(Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.CHARGE.createInstance()));
        setTriggerPhrase("Whenever one or more -1/-1 counters are put on a creature, ");
    }

    private WickersmithsToolsTriggeredAbility(final WickersmithsToolsTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public WickersmithsToolsTriggeredAbility copy() {
        return new WickersmithsToolsTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.COUNTERS_ADDED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent permanent = game.getPermanentOrLKIBattlefield(event.getTargetId());
        if (permanent == null
                || !permanent.isCreature(game)
                || !event.getData().equals(CounterType.M1M1.getName())
                || event.getAmount() < 1) {
            return false;
        }
        return true;
    }
}
