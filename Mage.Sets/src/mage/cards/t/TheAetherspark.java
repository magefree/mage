package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.DealsCombatDamageEquippedTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.AttachedToMatchesFilterCondition;
import mage.abilities.condition.common.MyTurnCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.dynamicvalue.common.SavedDamageValue;
import mage.abilities.effects.RestrictionEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.effects.mana.AddManaOfAnyColorEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TheAetherspark extends CardImpl {

    private static final Condition condition = new AttachedToMatchesFilterCondition(StaticFilters.FILTER_PERMANENT_CREATURE);

    public TheAetherspark(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.PLANESWALKER}, "{4}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.EQUIPMENT);
        this.setStartingLoyalty(4);

        // As long as The Aetherspark is attached to a creature, The Aetherspark can't be attacked and has "Whenever equipped creature deals combat damage during your turn, put that many loyalty counters on The Aetherspark."
        Ability ability = new SimpleStaticAbility(new TheAethersparkEffect());
        ability.addEffect(new ConditionalContinuousEffect(new GainAbilitySourceEffect(
                new DealsCombatDamageEquippedTriggeredAbility(new AddCountersSourceEffect(
                        CounterType.LOYALTY.createInstance(0), SavedDamageValue.MANY
                )).withTriggerCondition(MyTurnCondition.instance)
        ), condition, "and has \"Whenever equipped creature deals combat damage during your turn, put that many loyalty counters on {this}.\""));
        this.addAbility(ability);

        // +1: Attach The Aetherspark to up to one target creature you control. Put a +1/+1 counter on that creature.
        ability = new LoyaltyAbility(new AttachEffect(
                Outcome.BoostCreature, "attach {this} to up to one target creature you control"
        ), 1);
        ability.addEffect(new AddCountersTargetEffect(CounterType.P1P1.createInstance())
                .setText("put a +1/+1 counter on that creature"));
        ability.addTarget(new TargetControlledCreaturePermanent(0, 1));
        this.addAbility(ability);

        // -5: Draw two cards.
        this.addAbility(new LoyaltyAbility(new DrawCardSourceControllerEffect(2), -5));

        // -10: Add ten mana of any one color.
        this.addAbility(new LoyaltyAbility(new AddManaOfAnyColorEffect(10), -10));
    }

    private TheAetherspark(final TheAetherspark card) {
        super(card);
    }

    @Override
    public TheAetherspark copy() {
        return new TheAetherspark(this);
    }
}

class TheAethersparkEffect extends RestrictionEffect {

    TheAethersparkEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "as long as {this} is attached to a creature, {this} can't be attacked";
    }

    private TheAethersparkEffect(final TheAethersparkEffect effect) {
        super(effect);
    }

    @Override
    public TheAethersparkEffect copy() {
        return new TheAethersparkEffect(this);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return Optional
                .ofNullable(source.getSourceId())
                .map(game::getPermanent)
                .map(Permanent::getAttachedTo)
                .map(game::getPermanent)
                .map(p -> p.isCreature(game))
                .orElse(false);
    }

    @Override
    public boolean canAttack(Permanent attacker, UUID defenderId, Ability source, Game game, boolean canUseChooseDialogs) {
        return !source.getSourceId().equals(defenderId);
    }
}
