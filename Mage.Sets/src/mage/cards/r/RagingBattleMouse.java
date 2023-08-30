package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.CelebrationCondition;
import mage.abilities.decorator.ConditionalCostModificationEffect;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.cost.SpellsCostReductionControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.watchers.common.CelebrationWatcher;
import mage.watchers.common.SpellsCastWatcher;

import java.util.UUID;

/**
 *
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
        Ability ability = new ConditionalInterveningIfTriggeredAbility(
                new BeginningOfCombatTriggeredAbility(
                        new BoostTargetEffect(1, 1, Duration.EndOfTurn), TargetController.YOU, false
                ), CelebrationCondition.instance, "At the beginning of combat on your turn, "
                        + "if two or more nonland permanents entered the battlefield under your control this turn, "
                        + "target creature you control gets +1/+1 until end of turn."
        );
        ability.addTarget(new TargetControlledCreaturePermanent());
        ability.setAbilityWord(AbilityWord.CELEBRATION);
        ability.addHint(CelebrationCondition.getHint());
        this.addAbility(ability, new CelebrationWatcher());
    }

    private RagingBattleMouse(final RagingBattleMouse card) {
        super(card);
    }

    @Override
    public RagingBattleMouse copy() {
        return new RagingBattleMouse(this);
    }
}

enum YouCastExactOneSpellThisTurnCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        SpellsCastWatcher watcher = game.getState().getWatcher(SpellsCastWatcher.class);
        return watcher != null && watcher.getSpellsCastThisTurn(source.getControllerId()).size() == 1;
    }
}
