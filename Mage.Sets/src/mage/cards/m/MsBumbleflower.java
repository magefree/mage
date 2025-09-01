package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.DrawCardTargetEffect;
import mage.abilities.effects.common.IfAbilityHasResolvedXTimesEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCreaturePermanent;
import mage.target.common.TargetOpponent;
import mage.target.targetpointer.FirstTargetPointer;
import mage.target.targetpointer.SecondTargetPointer;
import mage.watchers.common.AbilityResolvedWatcher;

import java.util.UUID;

/**
 * @author DreamWaker
 */
public final class MsBumbleflower extends CardImpl {

    public MsBumbleflower(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}{W}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.RABBIT);
        this.subtype.add(SubType.CITIZEN);
        this.power = new MageInt(1);
        this.toughness = new MageInt(5);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Whenever you cast a spell, target opponent draws a card. Put a +1/+1 counter on target creature. It gains
        // flying until end of turn. If this is the second time this ability has resolved this turn, you draw two cards.
        Ability ability = new SpellCastControllerTriggeredAbility(
                new DrawCardTargetEffect(1), StaticFilters.FILTER_SPELL_A, false
        );
        ability.addTarget(new TargetOpponent());
        ability.addEffect(new AddCountersTargetEffect(CounterType.P1P1.createInstance())
                .setTargetPointer(new SecondTargetPointer()));
        ability.addEffect(new GainAbilityTargetEffect(FlyingAbility.getInstance())
                .setText("It gains flying until end of turn.").setTargetPointer(new SecondTargetPointer()));
        ability.addTarget(new TargetCreaturePermanent());
        ability.addEffect(new IfAbilityHasResolvedXTimesEffect(
                2, new DrawCardSourceControllerEffect(2, true
        )));
        this.addAbility(ability, new AbilityResolvedWatcher());
    }

    private MsBumbleflower(final MsBumbleflower card) {
        super(card);
    }

    @Override
    public MsBumbleflower copy() {
        return new MsBumbleflower(this);
    }
}
