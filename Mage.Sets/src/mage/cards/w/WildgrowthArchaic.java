package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.EntersTheBattlefieldEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.dynamicvalue.common.ColorsOfManaSpentToCastCount;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SetTargetPointer;

/**
 *
 * @author muz
 */
public final class WildgrowthArchaic extends CardImpl {

    public WildgrowthArchaic(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2/G}{2/G}");

        this.subtype.add(SubType.AVATAR);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // Converge -- This creature enters with a +1/+1 counter on it for each color of mana spent to cast it.
        this.addAbility(new EntersBattlefieldAbility(
            new AddCountersSourceEffect(
                CounterType.P1P1.createInstance(), ColorsOfManaSpentToCastCount.getInstance()
            ), null, AbilityWord.CONVERGE.formatWord() + "{this} enters with a +1/+1 counter " +
            "on it for each color of mana spent to cast it.", null
        ));

        // Whenever you cast a creature spell, that creature enters with X additional +1/+1
        // counters on it, where X is the number of colors of mana spent to cast it.
        this.addAbility(new SpellCastControllerTriggeredAbility(
            new WildgrowthArchaicEffect(),
            StaticFilters.FILTER_SPELL_A_CREATURE,
            false, SetTargetPointer.SPELL
        ));

    }

    private WildgrowthArchaic(final WildgrowthArchaic card) {
        super(card);
    }

    @Override
    public WildgrowthArchaic copy() {
        return new WildgrowthArchaic(this);
    }
}

class WildgrowthArchaicEffect extends ReplacementEffectImpl {

    WildgrowthArchaicEffect() {
        super(Duration.EndOfTurn, Outcome.BoostCreature);
        this.staticText = "that creature enters with X additional +1/+1 counters on it, where X is the number of colors of mana spent to cast it";
    }

    private WildgrowthArchaicEffect(final WildgrowthArchaicEffect effect) {
        super(effect);
    }

    @Override
    public WildgrowthArchaicEffect copy() {
        return new WildgrowthArchaicEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Spell spell = game.getSpellOrLKIStack(getTargetPointer().getFirst(game, source));
        return spell != null && event.getTargetId().equals(spell.getSourceId());
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Permanent creature = ((EntersTheBattlefieldEvent) event).getTarget();
        if (creature == null) {
            return false;
        }
        Spell spell = game.getSpellOrLKIStack(getTargetPointer().getFirst(game, source));
        if (spell == null) {
            return false;
        }
        int colorCount = ColorsOfManaSpentToCastCount.countColors(
            spell.getSpellAbility().getManaCostsToPay().getUsedManaToPay()
        );
        creature.addCounters(CounterType.P1P1.createInstance(colorCount),
            source.getControllerId(), source, game, event.getAppliedEffects());
        return false; // Must return false, otherwise creature doesn't enter battlefield
    }
}
