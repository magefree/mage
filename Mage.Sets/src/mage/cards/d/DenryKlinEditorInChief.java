package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.condition.common.SourceHasCountersCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCounterChoiceSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.Counter;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.UUID;

/**
 * @author Alex-Vasile
 */
public class DenryKlinEditorInChief extends CardImpl {

    public DenryKlinEditorInChief(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.addSubType(SubType.CAT, SubType.ADVISOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Denry Klin, Editor in Chief enters the battlefield with your choice of a +1/+1, first strike, or vigilance counter on it.
        this.addAbility(new EntersBattlefieldAbility(
                new AddCounterChoiceSourceEffect(CounterType.P1P1, CounterType.FIRST_STRIKE, CounterType.VIGILANCE)
        ));

        // Whenever a nontoken creature enters the battlefield under your control,
        // if Denry has counters on it, put the same number of each kind of counter on that creature.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new EntersBattlefieldControlledTriggeredAbility(
                        new DenryKlinEditorInChiefCopyCountersEffect(),
                        StaticFilters.FILTER_CONTROLLED_CREATURE_NON_TOKEN),
                SourceHasCountersCondition.instance,
                "Whenever a nontoken creature enters the battlefield under your control, " +
                "if Denry has counters on it, put the same number of each kind of counter on that creature.")
        );

    }

    private DenryKlinEditorInChief(final DenryKlinEditorInChief card) {
        super(card);
    }

    @Override
    public DenryKlinEditorInChief copy() {
        return new DenryKlinEditorInChief(this);
    }
}

class DenryKlinEditorInChiefCopyCountersEffect extends OneShotEffect {

    DenryKlinEditorInChiefCopyCountersEffect() {
        super(Outcome.Benefit);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent denryPermanent = game.getPermanent(source.getSourceId());
        Object enteringObject = getValue("permanentEnteringBattlefield");
        if (controller == null || denryPermanent == null || !(enteringObject instanceof Permanent)) {
            return false;
        }
        Permanent enteringCreature = (Permanent) enteringObject;

        for (Counter counter : denryPermanent.getCounters(game).values()) {
            enteringCreature.addCounters(counter, source, game);
        }
        return true;
    }

    private DenryKlinEditorInChiefCopyCountersEffect(final DenryKlinEditorInChiefCopyCountersEffect effect) {
        super(effect);
    }

    @Override
    public Effect copy() {
        return new DenryKlinEditorInChiefCopyCountersEffect(this);
    }
}