package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.SourceHasCounterCondition;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CounterUnlessPaysEffect;
import mage.abilities.effects.common.FlipSourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.custom.CreatureToken;
import mage.target.TargetSpell;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class CallowJushi extends CardImpl {

    private static final Condition condition = new SourceHasCounterCondition(CounterType.KI, 2);

    public CallowJushi(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}{U}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
        this.flipCard = true;
        this.flipCardName = "Jaraku the Interloper";

        Ability ability = new SimpleActivatedAbility(
            new CounterUnlessPaysEffect(new GenericManaCost(2)),
            new RemoveCountersSourceCost(CounterType.KI.createInstance()));
        ability.addTarget(new TargetSpell());

        CreatureToken flipToken = new CreatureToken(3, 3, "", SubType.HUMAN, SubType.WIZARD)
            .withName("Jaraku the Interloper")
            .withSuperType(SuperType.LEGENDARY)
            .withColor("U")
            .withAbility(ability);

        // Whenever you cast a Spirit or Arcane spell, you may put a ki counter on Callow Jushi.
        this.addAbility(new SpellCastControllerTriggeredAbility(new AddCountersSourceEffect(CounterType.KI.createInstance()), StaticFilters.FILTER_SPELL_SPIRIT_OR_ARCANE, true));

        // At the beginning of the end step, if there are two or more ki counters on Callow Jushi, you may flip it.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
            TargetController.NEXT, new FlipSourceEffect(flipToken).setText("flip it"), true, condition
        ));
    }

    private CallowJushi(final CallowJushi card) {
        super(card);
    }

    @Override
    public CallowJushi copy() {
        return new CallowJushi(this);
    }
}
