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
package mage.sets.planechase2012;

import java.util.UUID;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.filter.common.FilterControlledLandPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.SaprolingToken;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;
import mage.target.common.TargetOpponent;

/**
 *
 * @author LevelX2
 */
public class LastStand extends CardImpl<LastStand> {

    public LastStand(UUID ownerId) {
        super(ownerId, 100, "Last Stand", Rarity.RARE, new CardType[]{CardType.SORCERY}, "{W}{U}{B}{R}{G}");
        this.expansionSetCode = "PC2";

        this.color.setRed(true);
        this.color.setBlue(true);
        this.color.setGreen(true);
        this.color.setBlack(true);
        this.color.setWhite(true);

        // Target opponent loses 2 life for each Swamp you control. Last Stand deals damage equal to the number of Mountains you control to target creature. Put a 1/1 green Saproling creature token onto the battlefield for each Forest you control. You gain 2 life for each Plains you control. Draw a card for each Island you control, then discard that many cards.
        this.getSpellAbility().addEffect(new LastStandEffect());
        this.getSpellAbility().addTarget(new TargetOpponent(true));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(true));
    }

    public LastStand(final LastStand card) {
        super(card);
    }

    @Override
    public LastStand copy() {
        return new LastStand(this);
    }
}

class LastStandEffect extends OneShotEffect<LastStandEffect> {

    private static final FilterControlledLandPermanent filterSwamp = new FilterControlledLandPermanent();
    private static final FilterControlledLandPermanent filterMountain = new FilterControlledLandPermanent();
    private static final FilterControlledLandPermanent filterPlains = new FilterControlledLandPermanent();
    private static final FilterControlledLandPermanent filterForest = new FilterControlledLandPermanent();
    private static final FilterControlledLandPermanent filterIsland = new FilterControlledLandPermanent();
    static {
        filterSwamp.add(new SubtypePredicate("Swamp"));
        filterMountain.add(new SubtypePredicate("Mountain"));
        filterPlains.add(new SubtypePredicate("Plains"));
        filterForest.add(new SubtypePredicate("Forest"));
        filterIsland.add(new SubtypePredicate("Island"));
    }

    public LastStandEffect() {
        super(Outcome.Benefit);
        this.staticText = "Target opponent loses 2 life for each Swamp you control. Last Stand deals damage equal to the number of Mountains you control to target creature. Put a 1/1 green Saproling creature token onto the battlefield for each Forest you control. You gain 2 life for each Plains you control. Draw a card for each Island you control, then discard that many cards";
    }

    public LastStandEffect(final LastStandEffect effect) {
        super(effect);
    }

    @Override
    public LastStandEffect copy() {
        return new LastStandEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            // Target opponent loses 2 life for each Swamp you control
            Player opponent = game.getPlayer(getTargetPointer().getFirst(game, source));
            if (opponent != null) {
                int swamps = game.getBattlefield().count(filterSwamp, source.getSourceId(), source.getControllerId(), game);
                opponent.damage(swamps, source.getSourceId(), game, false, true);
            }
            // Last Stand deals damage equal to the number of Mountains you control to target creature.
            Permanent creature = game.getPermanent(source.getTargets().get(1).getFirstTarget());
            if (creature != null) {
                int mountains = game.getBattlefield().count(filterMountain, source.getSourceId(), source.getControllerId(), game);
                if (mountains > 0) {
                    creature.damage(mountains, source.getSourceId(), game, true, false);
                }
            }
            // Put a 1/1 green Saproling creature token onto the battlefield for each Forest you control.
            int forests = game.getBattlefield().count(filterForest, source.getSourceId(), source.getControllerId(), game);
            if (forests > 0) {
                new CreateTokenEffect(new SaprolingToken(), forests).apply(game, source);
            }
            // You gain 2 life for each Plains you control.
            int plains = game.getBattlefield().count(filterPlains, source.getSourceId(), source.getControllerId(), game);
            controller.gainLife(plains, game);
            // Draw a card for each Island you control, then discard that many cards
            int islands = game.getBattlefield().count(filterIsland, source.getSourceId(), source.getControllerId(), game);
            if (islands > 0) {
                controller.drawCards(islands, game);
                controller.discard(islands, source, game);
            }

        }
        return false;
    }
}
