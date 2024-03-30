package mage.cards.e;

import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.CompoundCondition;
import mage.abilities.condition.Condition;
import mage.abilities.condition.InvertCondition;
import mage.abilities.condition.common.HaventCastSpellFromHandThisTurnCondition;
import mage.abilities.condition.common.SourceMatchesFilterCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.abilities.effects.keyword.SurveilEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.permanent.token.custom.CreatureToken;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class EmergentHaunting extends CardImpl {

    private static final Condition condition = new CompoundCondition(
            "if you haven't cast a spell from your hand this turn and {this} isn't a creature",
            HaventCastSpellFromHandThisTurnCondition.instance,
            new InvertCondition(new SourceMatchesFilterCondition(new FilterCreaturePermanent()))
    );

    public EmergentHaunting(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{U}");

        // At the beginning of your end step, if you haven't cast a spell from your hand this turn and Emergent Haunting isn't a creature, it becomes a 3/3 Spirit creature with flying in addition to its other types.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                Zone.BATTLEFIELD,
                new BecomesCreatureSourceEffect(
                        new CreatureToken(3, 3, "3/3 Spirit creature with flying in addition to its other types")
                                .withSubType(SubType.SPIRIT).withAbility(FlyingAbility.getInstance()),
                        null, Duration.WhileOnBattlefield
                ),
                TargetController.YOU, condition, false
        ).addHint(HaventCastSpellFromHandThisTurnCondition.hint));

        // {2}{U}: Surveil 1.
        this.addAbility(new SimpleActivatedAbility(new SurveilEffect(1), new ManaCostsImpl<>("{2}{U}")));
    }

    private EmergentHaunting(final EmergentHaunting card) {
        super(card);
    }

    @Override
    public EmergentHaunting copy() {
        return new EmergentHaunting(this);
    }
}
