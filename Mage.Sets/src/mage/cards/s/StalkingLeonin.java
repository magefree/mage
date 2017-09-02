/*
 *  Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without modification, are
 *  permitted provided that the following conditions are met:
 *
 *     1. Redistributions of source code must retain the above copyright notice, this list of
 *        conditions and the following disclaimer.
 *
 *     2. Redistributions in binary form must reproduce the above copyright notice, this list
 *        of conditions and the following disclaimer in the documentation and/or other materials
 *        provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *  The views and conclusions contained in the software and documentation are those of the
 *  authors and should not be interpreted as representing official policies, either expressed
 *  or implied, of BetaSteward_at_googlemail.com.
 */
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import static mage.cards.s.StalkingLeonin.SECRET_OPPONENT;
import static mage.cards.s.StalkingLeonin.SECRET_OWNER;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterAttackingCreature;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;
import mage.target.common.TargetOpponent;
import mage.util.CardUtil;

/**
 *
 * @author LevelX2
 */
public class StalkingLeonin extends CardImpl {

    static final String SECRET_OPPONENT = "_secOpp";
    static final String SECRET_OWNER = "_secOwn";

    public StalkingLeonin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.CAT, SubType.ARCHER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When Stalking Leonin enters the battlefield, secretly choose an opponent.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new StalkingLeoninChooseOpponent(), false));
        // Reveal the player you chose: Exile target creature that's attacking you if it's controlled by the chosen player. Activate this ability only once.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new StalkingLeoninEffect(), new StalkingLeoninRevealOpponentCost());
        ability.addTarget(new TargetCreaturePermanent(new StalkingLeoninFilter()));
        this.addAbility(ability);
    }

    public StalkingLeonin(final StalkingLeonin card) {
        super(card);
    }

    @Override
    public StalkingLeonin copy() {
        return new StalkingLeonin(this);
    }
}

class StalkingLeoninChooseOpponent extends OneShotEffect {

    public StalkingLeoninChooseOpponent() {
        super(Outcome.Neutral);
        staticText = "secretly choose an opponent";
    }

    public StalkingLeoninChooseOpponent(final StalkingLeoninChooseOpponent effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject mageObject = game.getPermanentEntering(source.getSourceId());
        if (mageObject == null) {
            mageObject = game.getObject(source.getSourceId());
        }
        if (controller != null && mageObject != null) {
            TargetOpponent targetOpponent = new TargetOpponent();
            targetOpponent.setTargetName("opponent (secretly)");
            while (!controller.choose(outcome, targetOpponent, source.getSourceId(), game)) {
                if (!controller.canRespond()) {
                    return false;
                }
            }
            if (targetOpponent.getTargets().isEmpty()) {
                return false;
            }
            if (!game.isSimulation()) {
                game.informPlayers(mageObject.getName() + ": " + controller.getLogName() + " has secretly chosen an opponent.");
            }
            game.getState().setValue(mageObject.getId() + SECRET_OPPONENT, targetOpponent.getTargets().get(0));
            game.getState().setValue(mageObject.getId() + SECRET_OWNER, controller.getId());
            if (mageObject instanceof Permanent) {
                ((Permanent) mageObject).addInfo(SECRET_OPPONENT,
                        CardUtil.addToolTipMarkTags(controller.getLogName() + " has secretly chosen an opponent."), game);
            }
        }
        return false;
    }

    @Override
    public StalkingLeoninChooseOpponent copy() {
        return new StalkingLeoninChooseOpponent(this);
    }

}

class StalkingLeoninRevealOpponentCost extends CostImpl {

    public StalkingLeoninRevealOpponentCost() {
        this.text = "Reveal the player you chose";
    }

    public StalkingLeoninRevealOpponentCost(final StalkingLeoninRevealOpponentCost cost) {
        super(cost);
    }

    @Override
    public boolean canPay(Ability ability, UUID sourceId, UUID controllerId, Game game) {
        UUID playerThatChoseId = (UUID) game.getState().getValue(sourceId + SECRET_OWNER);
        if (playerThatChoseId == null || !playerThatChoseId.equals(controllerId)) {
            return false;
        }
        UUID opponentId = (UUID) game.getState().getValue(sourceId + SECRET_OPPONENT);
        return opponentId != null;
    }

    @Override
    public boolean pay(Ability ability, Game game, UUID sourceId, UUID controllerId, boolean noMana, Cost costToPay) {
        UUID playerThatChoseId = (UUID) game.getState().getValue(sourceId + SECRET_OWNER);
        if (playerThatChoseId == null || !playerThatChoseId.equals(controllerId)) {
            return false;
        }
        UUID opponentId = (UUID) game.getState().getValue(sourceId + SECRET_OPPONENT);
        if (opponentId != null) {
            game.getState().setValue(sourceId + SECRET_OWNER, null); // because only once, the vale is set to null
            Player controller = game.getPlayer(controllerId);
            Player opponent = game.getPlayer(opponentId);
            MageObject sourceObject = game.getObject(sourceId);
            if (controller != null && opponent != null && sourceObject != null) {
                if (sourceObject instanceof Permanent) {
                    ((Permanent) sourceObject).addInfo(SECRET_OPPONENT, null, game);
                }
                game.informPlayers(sourceObject.getLogName() + ": " + controller.getLogName() + " reveals the secretly chosen opponent " + opponent.getLogName());
            }
            paid = true;
        }
        return paid;
    }

    @Override
    public StalkingLeoninRevealOpponentCost copy() {
        return new StalkingLeoninRevealOpponentCost(this);
    }

}

class StalkingLeoninEffect extends OneShotEffect {

    public StalkingLeoninEffect() {
        super(Outcome.Exile);
        this.staticText = "Exile target creature that's attacking you if it's controlled by the chosen player. Activate this ability only once";
    }

    public StalkingLeoninEffect(final StalkingLeoninEffect effect) {
        super(effect);
    }

    @Override
    public StalkingLeoninEffect copy() {
        return new StalkingLeoninEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Permanent targetCreature = game.getPermanent(getTargetPointer().getFirst(game, source));
            if (targetCreature != null) {
                UUID opponentId = (UUID) game.getState().getValue(source.getSourceId() + SECRET_OPPONENT);
                if (opponentId != null && opponentId.equals(targetCreature.getControllerId())) {
                    controller.moveCards(targetCreature, Zone.EXILED, source, game);
                }
            }
            return true;
        }
        return false;
    }
}

class StalkingLeoninFilter extends FilterAttackingCreature {

    public StalkingLeoninFilter() {
        super("creature that's attacking you");
    }

    public StalkingLeoninFilter(final StalkingLeoninFilter filter) {
        super(filter);
    }

    @Override
    public StalkingLeoninFilter copy() {
        return new StalkingLeoninFilter(this);
    }

    @Override
    public boolean match(Permanent permanent, UUID sourceId, UUID playerId, Game game) {
        return super.match(permanent, sourceId, playerId, game)
                && playerId.equals(game.getCombat().getDefenderId(permanent.getId()));
    }
}
