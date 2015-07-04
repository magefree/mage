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
package mage.sets.commander2014;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterBasicLandCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.mageobject.SupertypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author LevelX2
 */
public class WaveOfVitriol extends CardImpl {

    public WaveOfVitriol(UUID ownerId) {
        super(ownerId, 51, "Wave of Vitriol", Rarity.RARE, new CardType[]{CardType.SORCERY}, "{5}{G}{G}");
        this.expansionSetCode = "C14";

        // Each player sacrifices all artifacts, enchantments, and nonbasic lands he or she controls. For each land sacrificed this way, its controller may search his or her library for a basic land card and put it onto the battlefield tapped. Then each player who searched his or her library this way shuffles it.
        this.getSpellAbility().addEffect(new WaveOfVitriolEffect());

    }

    public WaveOfVitriol(final WaveOfVitriol card) {
        super(card);
    }

    @Override
    public WaveOfVitriol copy() {
        return new WaveOfVitriol(this);
    }
}

class WaveOfVitriolEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterPermanent();

    static {
        filter.add(Predicates.or(
                new CardTypePredicate(CardType.ARTIFACT),
                new CardTypePredicate(CardType.ENCHANTMENT),
                Predicates.and(
                        new CardTypePredicate(CardType.LAND),
                        Predicates.not(new SupertypePredicate("Basic"))
                )

        ));
    }
    public WaveOfVitriolEffect() {
        super(Outcome.Benefit);
        this.staticText = "Each player sacrifices all artifacts, enchantments, and nonbasic lands he or she controls. For each land sacrificed this way, its controller may search his or her library for a basic land card and put it onto the battlefield tapped. Then each player who searched his or her library this way shuffles it";
    }

    public WaveOfVitriolEffect(final WaveOfVitriolEffect effect) {
        super(effect);
    }

    @Override
    public WaveOfVitriolEffect copy() {
        return new WaveOfVitriolEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Map<Player, Integer> sacrificedLands = new HashMap<>();
            for(UUID playerId: controller.getInRange()) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    int count = 0;
                    for(Permanent permanent: game.getBattlefield().getAllActivePermanents(filter, playerId, game)) {
                        if (permanent.sacrifice(source.getSourceId(), game) && permanent.getCardType().contains(CardType.LAND)) {
                            count++;
                        }
                    }
                    if (count > 0) {
                        sacrificedLands.put(player, count);
                    }
                }
            }
            game.getState().handleSimultaneousEvent(game);
            for(Map.Entry<Player, Integer> entry: sacrificedLands.entrySet()) {
                if (entry.getKey().chooseUse(Outcome.PutLandInPlay, "Search your library for up to " + entry.getValue() + " basic lands?", source, game)) {
                    Target target = new TargetCardInLibrary(0, entry.getValue(), new FilterBasicLandCard());
                    entry.getKey().chooseTarget(outcome, target, source, game);
                    for(UUID targetId: target.getTargets()) {
                        Card card = game.getCard(targetId);
                        if (card != null) {
                            entry.getKey().putOntoBattlefieldWithInfo(card, game, Zone.LIBRARY, source.getSourceId(), true);
                        }
                    }
                    entry.getKey().shuffleLibrary(game);
                } else {
                    entry.setValue(0);
                }
            }
            for(Map.Entry<Player, Integer> entry: sacrificedLands.entrySet()) {
                if (entry.getValue() > 0) {
                    entry.getKey().shuffleLibrary(game);
                }
            }

            return true;
        }
        return false;
    }
}
