package mage.cards.f;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.SourceHasCounterCondition;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.effects.common.FlipSourceEffect;
import mage.abilities.effects.common.PreventDamageToTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.custom.CreatureToken;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class FaithfulSquire extends CardImpl {

    private static final Condition condition = new SourceHasCounterCondition(CounterType.KI, 2);

    public FaithfulSquire(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
        this.flipCard = true;
        this.flipCardName = "Kaiso, Memory of Loyalty";

        Ability ability = new SimpleActivatedAbility(
            new PreventDamageToTargetEffect(Duration.EndOfTurn, Integer.MAX_VALUE),
            new RemoveCountersSourceCost(CounterType.KI.createInstance()));
        ability.addTarget(new TargetCreaturePermanent());

        CreatureToken flipToken = new CreatureToken(3, 4, "", SubType.SPIRIT)
            .withName("Kaiso, Memory of Loyalty")
            .withSuperType(SuperType.LEGENDARY)
            .withColor("W")
            .withAbility(FlyingAbility.getInstance())
            .withAbility(ability);

        // Whenever you cast a Spirit or Arcane spell, you may put a ki counter on Faithful Squire.
        this.addAbility(new SpellCastControllerTriggeredAbility(
            new AddCountersSourceEffect(CounterType.KI.createInstance()),
            StaticFilters.FILTER_SPELL_SPIRIT_OR_ARCANE, true
        ));

        // At the beginning of the end step, if there are two or more ki counters on Faithful Squire, you may flip it
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
            TargetController.NEXT, new FlipSourceEffect(flipToken).setText("flip it"), true, condition
        ));
    }

    private FaithfulSquire(final FaithfulSquire card) {
        super(card);
    }

    @Override
    public FaithfulSquire copy() {
        return new FaithfulSquire(this);
    }
}
