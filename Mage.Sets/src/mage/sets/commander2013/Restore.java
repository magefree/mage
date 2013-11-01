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
package mage.sets.commander2013;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterLandCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCardInGraveyard;

/**
 *
 * @author LevelX2
 */
public class Restore extends CardImpl<Restore> {

    private static final FilterLandCard filter = new FilterLandCard();

    public Restore(UUID ownerId) {
        super(ownerId, 167, "Restore", Rarity.UNCOMMON, new CardType[]{CardType.SORCERY}, "{1}{G}");
        this.expansionSetCode = "C13";

        this.color.setGreen(true);

        // Put target land card from a graveyard onto the battlefield under your control.
        this.getSpellAbility().addEffect(new RestoreEffect());
        Target target = new TargetCardInGraveyard(filter);
        target.setRequired(true);
        this.getSpellAbility().addTarget(target);
    }

    public Restore(final Restore card) {
        super(card);
    }

    @Override
    public Restore copy() {
        return new Restore(this);
    }
}

class RestoreEffect extends OneShotEffect<RestoreEffect> {

    public RestoreEffect() {
        super(Outcome.PutLandInPlay);
        this.staticText = "Put target land card from a graveyard onto the battlefield under your control";
    }

    public RestoreEffect(final RestoreEffect effect) {
        super(effect);
    }

    @Override
    public RestoreEffect copy() {
        return new RestoreEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Card land = game.getCard(this.getTargetPointer().getFirst(game, source));
        if (controller != null && game.getState().getZone(land.getId()).equals(Zone.GRAVEYARD)) {
            return land.putOntoBattlefield(game, Zone.GRAVEYARD, source.getSourceId(), controller.getId());
        }
        return false;
    }
}
