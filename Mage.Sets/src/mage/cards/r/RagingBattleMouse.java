package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.CelebrationCondition;
import mage.abilities.condition.common.YouCastExactOneSpellThisTurnCondition;
import mage.abilities.decorator.ConditionalCostModificationEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.cost.SpellsCostReductionControllerEffect;
import mage.abilities.triggers.BeginningOfCombatTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.watchers.common.PermanentsEnteredBattlefieldWatcher;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class RagingBattleMouse extends CardImpl {

    public RagingBattleMouse(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.MOUSE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // The second spell you cast each turn costs {1} less to cast.
        this.addAbility(new SimpleStaticAbility(new ConditionalCostModificationEffect(
                new SpellsCostReductionControllerEffect(StaticFilters.FILTER_CARD, 1),
                YouCastExactOneSpellThisTurnCondition.instance, "the second spell you cast each turn costs {1} less to cast"
        )));

        // Celebration -- At the beginning of combat on your turn, if two or more nonland permanents entered the battlefield under your control this turn, target creature you control gets +1/+1 until end of turn.
        Ability ability = new BeginningOfCombatTriggeredAbility(
                new BoostTargetEffect(1, 1, Duration.EndOfTurn)
        ).withInterveningIf(CelebrationCondition.instance);
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability.setAbilityWord(AbilityWord.CELEBRATION).addHint(CelebrationCondition.getHint()), new PermanentsEnteredBattlefieldWatcher());
    }

    private RagingBattleMouse(final RagingBattleMouse card) {
        super(card);
    }

    @Override
    public RagingBattleMouse copy() {
        return new RagingBattleMouse(this);
    }
}
