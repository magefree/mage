package mage.cards.l;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.SacrificeTargetEffect;
import mage.abilities.effects.keyword.SurveilEffect;
import mage.abilities.triggers.BeginningOfCombatTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.KlingonWarriorToken;
import mage.game.permanent.token.Token;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 *
 * @author notshauna
 */

public final class LRellFierceVisionary extends CardImpl {

    public LRellFierceVisionary(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.KLINGON);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        //At the beginning of combat on your turn, create a 2/1 red Klingon Warrior creature token with haste.
        // Sacrifice it at the beginning of the next end step.
        this.addAbility(new BeginningOfCombatTriggeredAbility(new LRellFierceVisionaryEffect()));

        //{2}, Sacrifice another creature or artifact: Surveil 1.
        Ability ability = new SimpleActivatedAbility(
                new SurveilEffect(1),
                new GenericManaCost(2));
        ability.addCost(new SacrificeTargetCost(StaticFilters.FILTER_CONTROLLED_ANOTHER_CREATURE_OR_ARTIFACT));
        this.addAbility(ability);
    }

    private LRellFierceVisionary(final LRellFierceVisionary card) {
        super(card);
    }

    @Override
    public LRellFierceVisionary copy() {
        return new LRellFierceVisionary(this);
    }
}

class LRellFierceVisionaryEffect extends OneShotEffect {

    LRellFierceVisionaryEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "create a 2/1 red Klingon Warrior creature token with haste. Sacrifice it at the beginning of the next end step.";
    }

    private LRellFierceVisionaryEffect(final LRellFierceVisionaryEffect effect) {
        super(effect);
    }

    @Override
    public LRellFierceVisionaryEffect copy() {
        return new LRellFierceVisionaryEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Token token = new KlingonWarriorToken();
        token.putOntoBattlefield(1, game, source);
        for (UUID tokenId : token.getLastAddedTokenIds()) {
            Permanent permanent = game.getPermanent(tokenId);
            if (permanent != null) {
                game.addDelayedTriggeredAbility(new AtTheBeginOfNextEndStepDelayedTriggeredAbility(new SacrificeTargetEffect("sacrifice it")
                        .setTargetPointer(new FixedTarget(permanent, game))), source);
            }
        }
        return true;
    }
}