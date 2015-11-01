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
package mage.sets.urzaslegacy;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterArtifactCard;
import mage.filter.common.FilterArtifactPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetArtifactPermanent;
import mage.target.common.TargetCardInGraveyard;

/**
 *
 * @author Plopman
 */
public class GoblinWelder extends CardImpl {

    public GoblinWelder(UUID ownerId) {
        super(ownerId, 80, "Goblin Welder", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{R}");
        this.expansionSetCode = "ULG";
        this.subtype.add("Goblin");
        this.subtype.add("Artificer");

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {tap}: Choose target artifact a player controls and target artifact card in that player's graveyard. If both targets are still legal as this ability resolves, that player simultaneously sacrifices the artifact and returns the artifact card to the battlefield.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new GoblinWelderEffect(), new TapSourceCost());
        ability.addTarget(new TargetArtifactPermanent(new FilterArtifactPermanent("artifact a player controls")));
        ability.addTarget(new GoblinWelderTarget());
        this.addAbility(ability);
    }

    public GoblinWelder(final GoblinWelder card) {
        super(card);
    }

    @Override
    public GoblinWelder copy() {
        return new GoblinWelder(this);
    }

    public class GoblinWelderEffect extends OneShotEffect {

        public GoblinWelderEffect() {
            super(Outcome.PutCardInPlay);
            staticText = "Choose target artifact a player controls and target artifact card in that player's graveyard. If both targets are still legal as this ability resolves, that player simultaneously sacrifices the artifact and returns the artifact card to the battlefield";
        }

        public GoblinWelderEffect(final GoblinWelderEffect effect) {
            super(effect);
        }

        @Override
        public boolean apply(Game game, Ability source) {
            Permanent artifact = game.getPermanent(getTargetPointer().getFirst(game, source));
            Card card = game.getCard(source.getTargets().get(1).getFirstTarget());
            Player controller = game.getPlayer(source.getControllerId());
            if (artifact != null && card != null && controller != null) {
                Zone currentZone = game.getState().getZone(card.getId());
                Player owner = game.getPlayer(card.getOwnerId());
                if (owner != null
                        && artifact.getCardType().contains(CardType.ARTIFACT)
                        && card.getCardType().contains(CardType.ARTIFACT)
                        && currentZone == Zone.GRAVEYARD
                        && card.getOwnerId().equals(artifact.getControllerId())) {
                    boolean sacrifice = artifact.sacrifice(source.getSourceId(), game);
                    boolean putOnBF = owner.moveCards(card, Zone.BATTLEFIELD, source, game);
                    if (sacrifice || putOnBF) {
                        return true;
                    }
                }
            }
            return false;
        }

        @Override
        public GoblinWelderEffect copy() {
            return new GoblinWelderEffect(this);
        }

    }

    class GoblinWelderTarget extends TargetCardInGraveyard {

        public GoblinWelderTarget() {
            super(1, 1, new FilterArtifactCard());
            targetName = "target artifact card in that player's graveyard";
        }

        public GoblinWelderTarget(final GoblinWelderTarget target) {
            super(target);
        }

        @Override
        public boolean canTarget(UUID id, Ability source, Game game) {
            Permanent artifact = game.getPermanent(source.getFirstTarget());
            if (artifact == null) {
                return false;
            }
            Player player = game.getPlayer(artifact.getControllerId());
            Card card = game.getCard(id);
            if (card != null && player != null && player.getGraveyard().contains(id)) {
                return filter.match(card, game);
            }
            return false;
        }

        @Override
        public GoblinWelderTarget copy() {
            return new GoblinWelderTarget(this);
        }
    }

}
