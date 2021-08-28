package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.common.OneOrMoreDiceRolledTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.continuous.GainClassAbilitySourceEffect;
import mage.abilities.keyword.ClassLevelAbility;
import mage.abilities.keyword.ClassReminderAbility;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.RollDiceEvent;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BarbarianClass extends CardImpl {

    public BarbarianClass(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{R}");

        this.subtype.add(SubType.CLASS);

        // (Gain the next level as a sorcery to add its ability.)
        this.addAbility(new ClassReminderAbility());

        // If you would roll one or more dice, instead roll that many dice plus one and ignore the lowest roll.
        this.addAbility(new SimpleStaticAbility(new BarbarianClassEffect()));

        // {1}{R}: Level 2
        this.addAbility(new ClassLevelAbility(2, "{1}{R}"));

        // Whenever you roll one or more dice, target creature you control gets +2/+0 and gains menace until end of turn.
        Ability ability = new OneOrMoreDiceRolledTriggeredAbility(
                new BoostTargetEffect(2, 0)
                        .setText("target creature you control gets +2/+0")
        );
        ability.addEffect(new GainAbilityTargetEffect(
                new MenaceAbility(), Duration.EndOfTurn
        ).setText("and gains menace until end of turn"));
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(new SimpleStaticAbility(new GainClassAbilitySourceEffect(ability, 2)));

        // {2}{R}: Level 3
        this.addAbility(new ClassLevelAbility(3, "{2}{R}"));

        // Creatures you control have haste.
        this.addAbility(new SimpleStaticAbility(
                new GainClassAbilitySourceEffect(new GainAbilityControlledEffect(
                        HasteAbility.getInstance(), Duration.WhileOnBattlefield,
                        StaticFilters.FILTER_PERMANENT_CREATURES
                ), 3)
        ));
    }

    private BarbarianClass(final BarbarianClass card) {
        super(card);
    }

    @Override
    public BarbarianClass copy() {
        return new BarbarianClass(this);
    }
}

class BarbarianClassEffect extends ReplacementEffectImpl {

    BarbarianClassEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "if you would roll one or more dice, instead roll that many dice plus one and ignore the lowest roll";
    }

    private BarbarianClassEffect(final BarbarianClassEffect effect) {
        super(effect);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        RollDiceEvent rdEvent = (RollDiceEvent) event;
        rdEvent.incAmount(1);
        rdEvent.incIgnoreLowestAmount(1);
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ROLL_DICE
                && ((RollDiceEvent) event).getRollDieType() == RollDieType.NUMERICAL;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return source.isControlledBy(event.getPlayerId());
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public BarbarianClassEffect copy() {
        return new BarbarianClassEffect(this);
    }
}
