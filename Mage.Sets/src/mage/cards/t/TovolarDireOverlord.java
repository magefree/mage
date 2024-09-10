package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.DealsDamageToAPlayerAllTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.DayboundAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TovolarDireOverlord extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent("a Wolf or Werewolf you control");

    static {
        filter.add(Predicates.or(
                SubType.WOLF.getPredicate(),
                SubType.WEREWOLF.getPredicate()
        ));
    }

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(filter, ComparisonType.MORE_THAN, 2);
    private static final Hint hint = new ValueHint("Wolves and Werewolves you control", new PermanentsOnBattlefieldCount(filter));

    public TovolarDireOverlord(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WEREWOLF);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);
        this.secondSideCardClazz = mage.cards.t.TovolarTheMidnightScourge.class;

        // Whenever a Wolf or Werewolf you control deals combat damage to a player, draw a card.
        this.addAbility(new DealsDamageToAPlayerAllTriggeredAbility(
                new DrawCardSourceControllerEffect(1), filter,
                false, SetTargetPointer.NONE, true
        ));

        // At the beginning of your upkeep, if you control three or more Wolves and/or Werewolves, it becomes night. Then transform any number of Human Werewolves you control.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new BeginningOfUpkeepTriggeredAbility(
                        new TovolarDireOverlordEffect(), TargetController.YOU, false
                ), condition, "At the beginning of your upkeep, if you control three or more Wolves " +
                "and/or Werewolves, it becomes night. Then transform any number of Human Werewolves you control."
        ));

        // Daybound
        this.addAbility(new DayboundAbility());
    }

    private TovolarDireOverlord(final TovolarDireOverlord card) {
        super(card);
    }

    @Override
    public TovolarDireOverlord copy() {
        return new TovolarDireOverlord(this);
    }
}

class TovolarDireOverlordEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterControlledPermanent("Human Werewolves you control");

    static {
        filter.add(SubType.HUMAN.getPredicate());
        filter.add(SubType.WEREWOLF.getPredicate());
    }

    TovolarDireOverlordEffect() {
        super(Outcome.Benefit);
    }

    private TovolarDireOverlordEffect(final TovolarDireOverlordEffect effect) {
        super(effect);
    }

    @Override
    public TovolarDireOverlordEffect copy() {
        return new TovolarDireOverlordEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        game.setDaytime(false);
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return true;
        }
        TargetPermanent target = new TargetPermanent(0, Integer.MAX_VALUE, filter, true);
        player.choose(outcome, target, source, game);
        for (UUID permanentId : target.getTargets()) {
            Permanent permanent = game.getPermanent(permanentId);
            if (permanent != null) {
                permanent.transform(source, game);
            }
        }
        return true;
    }
}
