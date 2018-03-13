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
package mage.cards.o;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.filter.FilterPlayer;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;

/**
 *
 * @author TheElk801
 */
public class OathOfMages extends CardImpl {

    private final UUID originalId;
    private static final FilterPlayer filter = new FilterPlayer();

    static {
        filter.add(new OathOfMagesPredicate());
    }

    public OathOfMages(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{R}");

        // At the beginning of each player's upkeep, that player chooses target player who has more life than he or she does and is his or her opponent. The first player may have Oath of Mages deal 1 damage to the second player.
        Ability ability = new BeginningOfUpkeepTriggeredAbility(new OathOfMagesEffect(), TargetController.ANY, false);
        ability.addTarget(new TargetPlayer(1, 1, false, filter));
        this.addAbility(ability);
        originalId = ability.getOriginalId();
    }

    @Override
    public void adjustTargets(Ability ability, Game game) {
        if (ability.getOriginalId().equals(originalId)) {
            Player activePlayer = game.getPlayer(game.getActivePlayerId());
            if (activePlayer != null) {
                ability.getTargets().clear();
                TargetPlayer target = new TargetPlayer(1, 1, false, filter);
                target.setTargetController(activePlayer.getId());
                ability.getTargets().add(target);
            }
        }
    }

    public OathOfMages(final OathOfMages card) {
        super(card);
        this.originalId = card.originalId;
    }

    @Override
    public OathOfMages copy() {
        return new OathOfMages(this);
    }
}

class OathOfMagesPredicate implements ObjectSourcePlayerPredicate<ObjectSourcePlayer<Player>> {

    @Override
    public boolean apply(ObjectSourcePlayer<Player> input, Game game) {
        Player targetPlayer = input.getObject();
        Player firstPlayer = game.getPlayer(game.getActivePlayerId());
        if (targetPlayer == null
                || firstPlayer == null
                || !firstPlayer.hasOpponent(targetPlayer.getId(), game)) {
            return false;
        }
        int lifeTotalTargetPlayer = targetPlayer.getLife();
        int lifeTotalFirstPlayer = firstPlayer.getLife();

        return lifeTotalTargetPlayer > lifeTotalFirstPlayer;
    }

    @Override
    public String toString() {
        return "player who has more life than he or she does and is his or her opponent";
    }
}

class OathOfMagesEffect extends OneShotEffect {

    public OathOfMagesEffect() {
        super(Outcome.Damage);
        staticText = "that player chooses target player who has more life than he or she does and is his or her opponent. The first player may have Oath of Mages deal 1 damage to the second player";
    }

    public OathOfMagesEffect(OathOfMagesEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        MageObject sourceObject = game.getObject(source.getSourceId());
        Player firstPlayer = game.getPlayer(game.getActivePlayerId());
        Player secondPlayer = game.getPlayer(source.getFirstTarget());
        if (sourceObject == null || firstPlayer == null) {
            return false;
        }
        if (firstPlayer.chooseUse(outcome, "Deal one damage to " + secondPlayer.getLogName() + "?", source, game)) {
            secondPlayer.damage(1, source.getId(), game, false, true);
        }
        return true;
    }

    @Override
    public OathOfMagesEffect copy() {
        return new OathOfMagesEffect(this);
    }
}
