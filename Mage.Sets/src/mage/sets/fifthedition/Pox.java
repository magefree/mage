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
package mage.sets.fifthedition;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterControlledLandPermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author emerald000
 */
public class Pox extends CardImpl {

    public Pox(UUID ownerId) {
        super(ownerId, 51, "Pox", Rarity.RARE, new CardType[]{CardType.SORCERY}, "{B}{B}{B}");
        this.expansionSetCode = "5ED";


        // Each player loses a third of his or her life, then discards a third of the cards in his or her hand, then sacrifices a third of the creatures he or she controls, then sacrifices a third of the lands he or she controls. Round up each time.
        this.getSpellAbility().addEffect(new PoxEffect());
    }

    public Pox(final Pox card) {
        super(card);
    }

    @Override
    public Pox copy() {
        return new Pox(this);
    }
}

class PoxEffect extends OneShotEffect {
    
    PoxEffect() {
        super(Outcome.Detriment);
        this.staticText = "Each player loses a third of his or her life, then discards a third of the cards in his or her hand, then sacrifices a third of the creatures he or she controls, then sacrifices a third of the lands he or she controls. Round up each time.";
    }
    
    PoxEffect(final PoxEffect effect) {
        super(effect);
    }
    
    @Override
    public PoxEffect copy() {
        return new PoxEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            // Each player loses a third of his or her life,
            for (UUID playerId : controller.getInRange()) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    int lifeToLose = (int) Math.ceil(player.getLife() / 3.0);
                    player.loseLife(lifeToLose, game);
                }
            }
            // then discards a third of the cards in his or her hand,
            for (UUID playerId : controller.getInRange()) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    int cardsToDiscard = (int) Math.ceil(player.getHand().size() / 3.0);
                    if (cardsToDiscard > 0) {
                        player.discard(cardsToDiscard, false, source, game);
                    }
                }
            }
            // then sacrifices a third of the creatures he or she controls,
            for (UUID playerId : controller.getInRange()) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent();
                    int creaturesToSacrifice = (int) Math.ceil(game.getBattlefield().count(filter, source.getSourceId(), player.getId(), game) / 3.0);
                    if (creaturesToSacrifice > 0) {
                        Target target = new TargetControlledCreaturePermanent(creaturesToSacrifice, creaturesToSacrifice, filter, true);
                        target.chooseTarget(Outcome.Sacrifice, playerId, source, game);
                        for (UUID permanentId : target.getTargets()) {
                            Permanent permanent = game.getPermanent(permanentId);
                            if (permanent != null) {
                                permanent.sacrifice(source.getSourceId(), game);
                            }
                        }
                    }
                }
            }
            // then sacrifices a third of the lands he or she controls.
            for (UUID playerId : controller.getInRange()) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    FilterControlledLandPermanent filter = new FilterControlledLandPermanent();
                    int landsToSacrifice = (int) Math.ceil(game.getBattlefield().count(filter, source.getSourceId(), player.getId(), game) / 3.0);
                    if (landsToSacrifice > 0) {
                        Target target = new TargetControlledPermanent(landsToSacrifice, landsToSacrifice, filter, true);
                        target.chooseTarget(Outcome.Sacrifice, playerId, source, game);
                        for (UUID permanentId : target.getTargets()) {
                            Permanent permanent = game.getPermanent(permanentId);
                            if (permanent != null) {
                                permanent.sacrifice(source.getSourceId(), game);
                            }
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }
}
