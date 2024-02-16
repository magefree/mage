package mage.cards.e;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class EagleOfDeliverance extends CardImpl {

    public EagleOfDeliverance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{W}{W}");

        this.subtype.add(SubType.BIRD);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Eagle of Deliverance enters the battlefield, put an indestructible counter on another target creature you control. Draw a card if that creature's power is 2 or less.
        TriggeredAbility trigger = new EntersBattlefieldTriggeredAbility(
                new AddCountersTargetEffect(CounterType.INDESTRUCTIBLE.createInstance())
        );
        trigger.addTarget(new TargetControlledCreaturePermanent(StaticFilters.FILTER_ANOTHER_TARGET_CREATURE_YOU_CONTROL));
        trigger.addEffect(new EagleOfDeliveranceEffect());
        this.addAbility(trigger);
    }

    private EagleOfDeliverance(final EagleOfDeliverance card) {
        super(card);
    }

    @Override
    public EagleOfDeliverance copy() {
        return new EagleOfDeliverance(this);
    }
}

class EagleOfDeliveranceEffect extends OneShotEffect {

    EagleOfDeliveranceEffect() {
        super(Outcome.DrawCard);
        staticText = "Draw a card if that creature's power is 2 or less";
    }

    private EagleOfDeliveranceEffect(final EagleOfDeliveranceEffect effect) {
        super(effect);
    }

    @Override
    public EagleOfDeliveranceEffect copy() {
        return new EagleOfDeliveranceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanentOrLKIBattlefield(source.getFirstTarget());
        if (permanent == null || permanent.getPower().getValue() > 2) {
            return false;
        }
        return new DrawCardSourceControllerEffect(1).apply(game, source);
    }
}