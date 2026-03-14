package mage.cards.d;

import java.util.UUID;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.custom.CreatureToken;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.common.TargetControlledPermanent;
import mage.target.targetpointer.FixedTarget;
import mage.abilities.Ability;
import mage.abilities.common.BecomesClassLevelTriggeredAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.abilities.effects.common.MillCardsControllerEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureTargetEffect;
import mage.abilities.effects.common.continuous.GainClassAbilitySourceEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.ClassLevelAbility;
import mage.abilities.keyword.ClassReminderAbility;
import mage.abilities.triggers.BeginningOfCombatTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;

/**
 *
 * @author muz
 */
public final class DoesMachines extends CardImpl {

    public DoesMachines(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{U}");

        this.subtype.add(SubType.CLASS);

        // (Gain the next level as a sorcery to add its ability.)
        this.addAbility(new ClassReminderAbility());

        // When this Class enters, mill two cards, draw two cards, then discard two cards.
        Ability enterAbility = new EntersBattlefieldAbility(new MillCardsControllerEffect(2));
        enterAbility.addEffect(new DrawDiscardControllerEffect(2, 2));
        this.addAbility(enterAbility);

        // {1}{U}: Level 2
        this.addAbility(new ClassLevelAbility(2, "{1}{U}"));

        // When this Class becomes level 2, return up to two target artifact cards from your graveyard to your hand.
        Ability level2Ability = new BecomesClassLevelTriggeredAbility(new ReturnFromGraveyardToHandTargetEffect(), 2);
        level2Ability.addTarget(new TargetCardInYourGraveyard(0, 2, StaticFilters.FILTER_CARD_ARTIFACTS));
        this.addAbility(level2Ability);

        // {4}{U}: Level 3
        this.addAbility(new ClassLevelAbility(2, "{4}{U}"));

        // At the beginning of combat on your turn, put three +1/+1 counters on target artifact you control. If it isn't a creature, it becomes a 0/0 Robot creature in addition to its other types.
        Ability level3ability = new BeginningOfCombatTriggeredAbility(
            new AddCountersTargetEffect(CounterType.P1P1.createInstance(), StaticValue.get(3))
        );
        level3ability.addTarget(new TargetControlledPermanent(StaticFilters.FILTER_CONTROLLED_PERMANENT_ARTIFACT));
        level3ability.addEffect(new DoesMachinesEffect());
        this.addAbility(new SimpleStaticAbility(new GainClassAbilitySourceEffect(level3ability, 3)));
    }

    private DoesMachines(final DoesMachines card) {
        super(card);
    }

    @Override
    public DoesMachines copy() {
        return new DoesMachines(this);
    }
}
class DoesMachinesEffect extends OneShotEffect {

    DoesMachinesEffect() {
        super(Outcome.BecomeCreature);
        this.staticText = "If it isn't a creature, it becomes a 0/0 Robot creature in addition to its other types";
    }

    private DoesMachinesEffect(final DoesMachinesEffect effect) {
        super(effect);
    }

    @Override
    public DoesMachinesEffect copy() {
        return new DoesMachinesEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (!permanent.isCreature(game)) {
            ContinuousEffect continuousEffect = new BecomesCreatureTargetEffect(
                new CreatureToken(0, 0, "0/0 Robot creature").withSubType(SubType.ROBOT), false, true, Duration.Custom);
            continuousEffect.setTargetPointer(new FixedTarget(permanent, game));
            game.addEffect(continuousEffect, source);
            return true;
        }
        return false;
    }
}
