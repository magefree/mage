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
import mage.abilities.StaticAbility;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.EntersBattlefieldEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.TapSourceEffect;
import mage.abilities.mana.GreenManaAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.AsThoughEffectType;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.ExileZone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetCard;
import mage.util.CardUtil;

/**
 *
 * @author LevelX2
 */
public class MosswortBridge extends CardImpl<MosswortBridge> {

    public MosswortBridge(UUID ownerId) {
        super(ownerId, 307, "Mosswort Bridge", Rarity.RARE, new CardType[]{CardType.LAND}, "");
        this.expansionSetCode = "C13";

        // Hideaway (This land enters the battlefield tapped. When it does, look at the top four cards of your library, exile one face down, then put the rest on the bottom of your library.)
        this.addAbility(new HideawayAbility(this));

        // {tap}: Add {G} to your mana pool.
        this.addAbility(new GreenManaAbility());

        // {G}, {tap}: You may play the exiled card without paying its mana cost if creatures you control have total power 10 or greater.
        Ability ability = new ActivateIfConditionActivatedAbility(
                Zone.BATTLEFIELD, new HideawayPlayEffect(), new ManaCostsImpl("{G}"), MosswortBridgeTotalPowerCondition.getInstance());
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    public MosswortBridge(final MosswortBridge card) {
        super(card);
    }

    @Override
    public MosswortBridge copy() {
        return new MosswortBridge(this);
    }
}

class MosswortBridgeTotalPowerCondition implements Condition {

    private static MosswortBridgeTotalPowerCondition fInstance = new MosswortBridgeTotalPowerCondition();
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent();

    public static Condition getInstance() {
        return fInstance;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int totalPower = 0;
        for (Permanent permanent : game.getBattlefield().getAllActivePermanents(filter, source.getControllerId(), game)) {
            totalPower += permanent.getPower().getValue();
            if (totalPower >= 10) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "creatures you control have total power 10 or greater";
    }
}

/**
 * 702.74. Hideaway 702.74a Hideaway represents a static ability and a triggered
 * ability. "Hideaway" means "This permanent enters the battlefield tapped" and
 * "When this permanent enters the battlefield, look at the top four cards of
 * your library. Exile one of them face down and put the rest on the bottom of
 * your library in any order. The exiled card gains 'Any player who has
 * controlled the permanent that exiled this card may look at this card in the
 * exile zone.'"
 */
class HideawayAbility extends StaticAbility<HideawayAbility> {

    public HideawayAbility(Card card) {
        super(Zone.BATTLEFIELD, new EntersBattlefieldEffect(new TapSourceEffect(true)));
        Ability ability = new EntersBattlefieldTriggeredAbility(new HideawayExileEffect(), false);
        ability.setRuleVisible(false);
        card.addAbility(ability);
        // Allow controller to look at face down card
        ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new LookAtFaceDownCardEffect());
        ability.setRuleVisible(false);
        card.addAbility(ability);
    }

    public HideawayAbility(final HideawayAbility ability) {
        super(ability);
    }

    @Override
    public String getRule() {
        return "Hideaway <i>(This land enters the battlefield tapped. When it does, look at the top four cards of your library, exile one face down, then put the rest on the bottom of your library.)</i>";
    }

    @Override
    public HideawayAbility copy() {
        return new HideawayAbility(this);
    }
}

class HideawayExileEffect extends OneShotEffect<HideawayExileEffect> {

    private static FilterCard filter1 = new FilterCard("card to exile face down");
    private static FilterCard filter2 = new FilterCard("card to put on the bottom of your library");

    public HideawayExileEffect() {
        super(Outcome.Benefit);
        this.staticText = "Look at the top four cards of your library, exile one face down, then put the rest on the bottom of your library";
    }

    public HideawayExileEffect(final HideawayExileEffect effect) {
        super(effect);
    }

    @Override
    public HideawayExileEffect copy() {
        return new HideawayExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Cards cards = new CardsImpl(Zone.PICK);
        int count = Math.min(player.getLibrary().size(), 4);
        for (int i = 0; i < count; i++) {
            Card card = player.getLibrary().removeFromTop(game);
            cards.add(card);
            game.setZone(card.getId(), Zone.PICK);
        }

        Permanent hideawaySource = game.getPermanent(source.getSourceId());
        if (cards.size() == 0 || hideawaySource == null) {
            return false;
        }

        TargetCard target1 = new TargetCard(Zone.PICK, filter1);
        if (player.choose(Outcome.Detriment, cards, target1, game)) {
            Card card = cards.get(target1.getFirstTarget(), game);
            if (card != null) {
                cards.remove(card);
                card.setFaceDown(true);
                card.moveToExile(CardUtil.getCardExileZoneId(game, source),
                        new StringBuilder("Hideaway (").append(hideawaySource.getName()).append(")").toString(),
                        source.getSourceId(), game);
            }
            target1.clearChosen();
        }

        if (cards.size() > 0) {
            TargetCard target2 = new TargetCard(Zone.PICK, filter2);
            target2.setRequired(true);
            while (cards.size() > 1) {
                player.choose(Outcome.Benefit, cards, target2, game);
                Card card = cards.get(target2.getFirstTarget(), game);
                if (card != null) {
                    cards.remove(card);
                    card.moveToZone(Zone.LIBRARY, source.getSourceId(), game, false);
                }
                target2.clearChosen();
            }
            Card card = cards.get(cards.iterator().next(), game);
            card.moveToZone(Zone.LIBRARY, source.getSourceId(), game, true);
        }

        return true;
    }
}

class LookAtFaceDownCardEffect extends AsThoughEffectImpl<LookAtFaceDownCardEffect> {

    public LookAtFaceDownCardEffect() {
        super(AsThoughEffectType.REVEAL_FACE_DOWN, Duration.EndOfGame, Outcome.Benefit);
        staticText = "You may look at cards exiled with {this}";
    }

    public LookAtFaceDownCardEffect(final LookAtFaceDownCardEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public LookAtFaceDownCardEffect copy() {
        return new LookAtFaceDownCardEffect(this);
    }

    @Override
    public boolean applies(UUID sourceId, Ability source, Game game) {
        Card card = game.getCard(sourceId);
        if (card != null && game.getState().getZone(sourceId) == Zone.EXILED) {
            Card sourceCard = game.getCard(source.getSourceId());
            if (sourceCard == null) {
                return false;
            }

            ExileZone exile = game.getExile().getExileZone(CardUtil.getCardExileZoneId(game, source));
            if (exile != null && exile.contains(sourceId)) {
                Cards cards = new CardsImpl(card);
                Player controller = game.getPlayer(source.getControllerId());
                if (controller != null) {
                    controller.lookAtCards("Exiled with " + sourceCard.getName(), cards, game);
                }
            }
        }
        return true;
    }
}

class HideawayPlayEffect extends OneShotEffect<HideawayPlayEffect> {

    public HideawayPlayEffect() {
        super(Outcome.Benefit);
        staticText = "You may play the exiled card without paying its mana cost";
    }

    public HideawayPlayEffect(final HideawayPlayEffect effect) {
        super(effect);
    }

    @Override
    public HideawayPlayEffect copy() {
        return new HideawayPlayEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        ExileZone zone = game.getExile().getExileZone(CardUtil.getCardExileZoneId(game, source));
        if (zone.isEmpty()) {
            return false;
        }
        Card card = zone.getCards(game).iterator().next();
        Player controller = game.getPlayer(source.getControllerId());
        if (card.getCardType().contains(CardType.LAND)) {
            // If the revealed card is a land, you can play it only if it's your turn and you haven't yet played a land this turn.
            if (game.getActivePlayerId().equals(source.getControllerId()) && controller.canPlayLand()) {
                if (controller.chooseUse(Outcome.Benefit, "Play the land?", game)) {
                    controller.playLand(card, game);
                }
            }
        } else {
            if (card.getSpellAbility().spellCanBeActivatedRegularlyNow(source.getControllerId(), game)) {
                if (controller.chooseUse(Outcome.Benefit, "Cast the card without paying mana cost?", game)) {
                    controller.cast(card.getSpellAbility(), game, true);
                }
            }
        }
        return false;
    }
}
