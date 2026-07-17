
package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.SourceHasCounterCondition;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.effects.common.FlipSourceEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.TrampleAbility;
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
public final class BudokaPupil extends CardImpl {

    private static final Condition condition = new SourceHasCounterCondition(CounterType.KI, 2);

    public BudokaPupil(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}{G}");
        this.subtype.add(SubType.HUMAN, SubType.MONK);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
        this.flipCard = true;
        this.flipCardName = "Ichiga, Who Topples Oaks";

        Ability ability = new SimpleActivatedAbility(
                new BoostTargetEffect(2, 2, Duration.EndOfTurn),
                new RemoveCountersSourceCost(CounterType.KI.createInstance()));
        ability.addTarget(new TargetCreaturePermanent());

        CreatureToken flipToken = new CreatureToken(4, 3, "", SubType.SPIRIT)
            .withName("Ichiga, Who Topples Oaks")
            .withSuperType(SuperType.LEGENDARY)
            .withColor("G")
            .withAbility(TrampleAbility.getInstance())
            .withAbility(ability);

        // Whenever you cast a Spirit or Arcane spell, you may put a ki counter on Budoka Pupil.
        this.addAbility(new SpellCastControllerTriggeredAbility(new AddCountersSourceEffect(CounterType.KI.createInstance()), StaticFilters.FILTER_SPELL_SPIRIT_OR_ARCANE, true));

        // At the beginning of the end step, if there are two or more ki counters on Budoka Pupil, you may flip it.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                TargetController.NEXT, new FlipSourceEffect(flipToken).setText("flip it"), true, condition
        ));
    }

    private BudokaPupil(final BudokaPupil card) {
        super(card);
    }

    @Override
    public BudokaPupil copy() {
        return new BudokaPupil(this);
    }
}
