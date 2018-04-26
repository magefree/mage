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
package mage.cards.r;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.CanBeEnchantedByPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;

/**
 *
 * @author L_J
 */
public class Retether extends CardImpl {

    public Retether(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{W}");

        // Return each Aura card from your graveyard to the battlefield. Only creatures can be enchanted this way.
        this.getSpellAbility().addEffect(new RetetherEffect());
    }

    public Retether(final Retether card) {
        super(card);
    }

    @Override
    public Retether copy() {
        return new Retether(this);
    }
}

class RetetherEffect extends OneShotEffect {

    private static final FilterCard filterAura = new FilterCard("Aura card from your graveyard");

    static {
        filterAura.add(new CardTypePredicate(CardType.ENCHANTMENT));
        filterAura.add(new SubtypePredicate(SubType.AURA));
    }

    public RetetherEffect() {
        super(Outcome.PutCardInPlay);
        this.staticText = "Return each Aura card from your graveyard to the battlefield. Only creatures can be enchanted this way";
    }

    public RetetherEffect(final RetetherEffect effect) {
        super(effect);
    }

    @Override
    public RetetherEffect copy() {
        return new RetetherEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Map<Card, Permanent> auraMap = new HashMap<>();
            auraCardsInGraveyard:
            for (Card aura : controller.getGraveyard().getCards(filterAura, source.getSourceId(), source.getControllerId(), game)) {
                if (aura != null) {
                    FilterCreaturePermanent filter = new FilterCreaturePermanent("creature to enchant (" + aura.getLogName() + ')');
                    filter.add(new CanBeEnchantedByPredicate(aura));
                    Target target = null;
                                
                    auraLegalitySearch:
                    for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
                        for (Permanent permanent : game.getBattlefield().getAllActivePermanents(filter, playerId, game)) {
                            if (permanent != null) {
                                for (Ability ability : aura.getAbilities()) {
                                    if (ability instanceof SpellAbility) {
                                        for (Target abilityTarget : ability.getTargets()) {
                                            if (abilityTarget.possibleTargets(controller.getId(), game).contains(permanent.getId())) {
                                                target = abilityTarget.copy();
                                                break auraLegalitySearch;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    if (target != null) {
                        target.getFilter().add(new CardTypePredicate(CardType.CREATURE));
                        target.setNotTarget(true);
                        if (target.canChoose(controller.getId(), game)) {
                            target.setTargetName("creature to enchant (" + aura.getLogName() + ')');
                            if (controller.choose(Outcome.PutCardInPlay, target, source.getSourceId(), game)) {
                                Permanent permanent = game.getPermanent(target.getFirstTarget());
                                if (permanent != null && !permanent.cantBeAttachedBy(aura, game)) {
                                    auraMap.put(aura, permanent);
                                    continue auraCardsInGraveyard;
                                }
                            }
                        }
                    }
                    game.informPlayers("No valid creature targets for " + aura.getLogName());
                }
            }
            for (Entry<Card, Permanent> entry : auraMap.entrySet()) {
                Card aura = entry.getKey();
                Permanent permanent = entry.getValue();
                if (aura != null) {
                    if (permanent != null) {
                        game.getState().setValue("attachTo:" + aura.getId(), permanent);
                    }
                    aura.putOntoBattlefield(game, Zone.GRAVEYARD, source.getSourceId(), controller.getId());
                    if (permanent != null) {
                        permanent.addAttachment(aura.getId(), game);
                    }
                }
            }
            return true;
        }
        return false;
    }
}
