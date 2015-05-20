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
package mage.sets.visions;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetPermanent;
import mage.target.TargetPlayer;

/**
 *
 * @author LevelX2
 */
public class Equipoise extends CardImpl {

    public Equipoise(UUID ownerId) {
        super(ownerId, 103, "Equipoise", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}");
        this.expansionSetCode = "VIS";


        // At the beginning of your upkeep, for each land target player controls in excess of the number you control, choose a land he or she controls, then the chosen permanents phase out. Repeat this process for artifacts and creatures.
        Ability ability = new BeginningOfUpkeepTriggeredAbility(new EquipoiseEffect(), TargetController.YOU, false);
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    public Equipoise(final Equipoise card) {
        super(card);
    }

    @Override
    public Equipoise copy() {
        return new Equipoise(this);
    }
}

class EquipoiseEffect extends OneShotEffect {

    public EquipoiseEffect() {
        super(Outcome.Benefit);
        this.staticText = "for each land target player controls in excess of the number you control, choose a land he or she controls, then the chosen permanents phase out. Repeat this process for artifacts and creatures";
    }

    public EquipoiseEffect(final EquipoiseEffect effect) {
        super(effect);
    }

    @Override
    public EquipoiseEffect copy() {
        return new EquipoiseEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player targetPlayer = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (controller != null && targetPlayer != null) {
            if (targetPlayer != controller) {
                phaseOutCardType(controller, targetPlayer, CardType.LAND, source, game);
                phaseOutCardType(controller, targetPlayer, CardType.ARTIFACT, source, game);
                phaseOutCardType(controller, targetPlayer, CardType.CREATURE, source, game);
            }
            return true;
        }
        return false;
    }

    private void phaseOutCardType(Player controller, Player targetPlayer, CardType cardType, Ability source, Game game) {
        FilterPermanent filter = new FilterControlledPermanent();
        filter.add(new CardTypePredicate(cardType));
        int numberController = game.getBattlefield().count(filter, source.getSourceId(), controller.getId(), game);
        int numberTargetPlayer = game.getBattlefield().count(filter, source.getSourceId(), targetPlayer.getId(), game);
        int excess = numberTargetPlayer - numberController;
        if (excess > 0) {
            FilterPermanent filterChoose = new FilterPermanent(cardType.toString().toLowerCase() + (excess > 1 ? "s":"") +" of target player");
            filterChoose.add(new ControllerIdPredicate(targetPlayer.getId()));
            filterChoose.add(new CardTypePredicate(cardType));
            Target target = new TargetPermanent(excess, excess, filterChoose, true);
            controller.chooseTarget(outcome, target, source, game);
            for (UUID permanentId:target.getTargets()) {
                Permanent permanent = game.getPermanent(permanentId);
                if (permanent != null) {
                    permanent.phaseOut(game);
                }
            }
        }
    }
}
