package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.SacrificeTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.StationAbility;
import mage.abilities.keyword.StationLevelAbility;
import mage.abilities.triggers.BeginningOfCombatTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
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

public final class D7BattleCruiser extends CardImpl {

    public D7BattleCruiser(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}{R}");

        this.subtype.add(SubType.SPACECRAFT);

        // Station
        this.addAbility(new StationAbility());

        // STATION 3+
        // At the beginning of combat on your turn, create a 2/1 red Klingon Warrior creature token with haste.
        // Sacrifice it at the beginning of the next end step.
        this.addAbility(new StationLevelAbility(3).withLevelAbility
                (new BeginningOfCombatTriggeredAbility(new D7BattleCruiserEffect())));

        // STATION 8+
        // Flying
        // 3/3
        this.addAbility(new StationLevelAbility(8).withLevelAbility(FlyingAbility.getInstance())
                .withPT(3,3));
        }

    private D7BattleCruiser(final D7BattleCruiser card) {
        super(card);
    }

    @Override
    public D7BattleCruiser copy() {
        return new D7BattleCruiser(this);
    }
}

class D7BattleCruiserEffect extends OneShotEffect {

    D7BattleCruiserEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "create a 2/1 red Klingon Warrior creature token with haste. Sacrifice it at the beginning of the next end step.";
    }

    private D7BattleCruiserEffect(final D7BattleCruiserEffect effect) {
        super(effect);
    }

    @Override
    public D7BattleCruiserEffect copy() {
        return new D7BattleCruiserEffect(this);
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