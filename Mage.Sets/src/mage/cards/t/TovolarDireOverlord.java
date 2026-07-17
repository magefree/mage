package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.common.DealsDamageToAPlayerAllTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.GetXValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.DayboundAbility;
import mage.abilities.keyword.NightboundAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
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
public final class TovolarDireOverlord extends TransformingDoubleFacedCard {

    private static final FilterPermanent filter = new FilterControlledPermanent("you control three or more Wolves and/or Werewolves");
    private static final FilterPermanent filter2 = new FilterControlledPermanent("a Wolf or Werewolf you control");

    static {
        filter.add(Predicates.or(
                SubType.WOLF.getPredicate(),
                SubType.WEREWOLF.getPredicate()
        ));
        filter2.add(Predicates.or(
                SubType.WOLF.getPredicate(),
                SubType.WEREWOLF.getPredicate()
        ));
    }

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(filter, ComparisonType.MORE_THAN, 2);
    private static final Hint hint = new ValueHint("Wolves and Werewolves you control", new PermanentsOnBattlefieldCount(filter));

    public TovolarDireOverlord(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN, SubType.WEREWOLF}, "{1}{R}{G}",
                "Tovolar, the Midnight Scourge",
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.CREATURE}, new SubType[]{SubType.WEREWOLF}, "RG"
        );

        // Tovolar, Dire Overlord
        this.getLeftHalfCard().setPT(3, 3);

        // Whenever a Wolf or Werewolf you control deals combat damage to a player, draw a card.
        this.getLeftHalfCard().addAbility(new DealsDamageToAPlayerAllTriggeredAbility(
                new DrawCardSourceControllerEffect(1), filter2,
                false, SetTargetPointer.NONE, true
        ).setTriggerPhrase("Whenever a Wolf or Werewolf you control deals combat damage to a player, "));

        // At the beginning of your upkeep, if you control three or more Wolves and/or Werewolves, it becomes night. Then transform any number of Human Werewolves you control.
        this.getLeftHalfCard().addAbility(new BeginningOfUpkeepTriggeredAbility(new TovolarDireOverlordEffect()).withInterveningIf(condition).addHint(hint));

        // Daybound
        this.getLeftHalfCard().addAbility(new DayboundAbility());

        // Tovolar, the Midnight Scourge
        this.getRightHalfCard().setPT(4, 4);

        // Whenever a Wolf or Werewolf you control deals combat damage to a player, draw a card.
        this.getRightHalfCard().addAbility(new DealsDamageToAPlayerAllTriggeredAbility(
                new DrawCardSourceControllerEffect(1), filter2,
                false, SetTargetPointer.NONE, true
        ));

        // {X}{R}{G}: Target Wolf or Werewolf you control gets +X/+0 and gains trample until end of turn.
        Ability ability = new SimpleActivatedAbility(new GainAbilityTargetEffect(
                TrampleAbility.getInstance(), Duration.EndOfTurn
        ).setText("Target Wolf or Werewolf you control gets +X/+0"), new ManaCostsImpl<>("{X}{R}{G}"));
        ability.addEffect(new BoostTargetEffect(
                GetXValue.instance, StaticValue.get(0), Duration.EndOfTurn
        ).setText("and gains trample until end of turn"));
        ability.addTarget(new TargetPermanent(filter2));
        this.getRightHalfCard().addAbility(ability);

        // Nightbound
        this.getRightHalfCard().addAbility(new NightboundAbility());
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
        staticText = "it becomes night. Then transform any number of Human Werewolves you control";
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
