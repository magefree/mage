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

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.WatcherScope;
import mage.filter.common.FilterInstantOrSorceryCard;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;
import mage.players.Player;
import mage.target.common.TargetCardInExile;
import mage.util.CardUtil;
import mage.watchers.WatcherImpl;

/**
 *
 * @author LevelX2
 */
public class JelevaNephaliasScourge extends CardImpl<JelevaNephaliasScourge> {

    public JelevaNephaliasScourge(UUID ownerId) {
        super(ownerId, 194, "Jeleva, Nephalia's Scourge", Rarity.MYTHIC, new CardType[]{CardType.CREATURE}, "{1}{U}{B}{R}");
        this.expansionSetCode = "C13";
        this.supertype.add("Legendary");
        this.subtype.add("Vampire");
        this.subtype.add("Wizard");

        this.color.setRed(true);
        this.color.setBlue(true);
        this.color.setBlack(true);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // When Jeleva, Nephalia's Scourge enters the battlefield, each player exiles the top X cards of his or her library, where X is the amount of mana spent to cast Jeleva.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new JelevaNephaliasScourgeEffect(), false));
        // Whenever Jeleva attacks, you may cast an instant or sorcery card exiled with it without paying its mana cost.
        this.addAbility(new AttacksTriggeredAbility(new JelevaNephaliasCastEffect(), false));
        this.addWatcher(new JelevaNephaliasWatcher());

    }

    public JelevaNephaliasScourge(final JelevaNephaliasScourge card) {
        super(card);
    }

    @Override
    public JelevaNephaliasScourge copy() {
        return new JelevaNephaliasScourge(this);
    }
}

class JelevaNephaliasScourgeEffect extends OneShotEffect<JelevaNephaliasScourgeEffect> {

    public JelevaNephaliasScourgeEffect() {
        super(Outcome.Benefit);
        this.staticText = "each player exiles the top X cards of his or her library, where X is the amount of mana spent to cast Jeleva";
    }

    public JelevaNephaliasScourgeEffect(final JelevaNephaliasScourgeEffect effect) {
        super(effect);
    }

    @Override
    public JelevaNephaliasScourgeEffect copy() {
        return new JelevaNephaliasScourgeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Card sourceCard = game.getCard(source.getSourceId());
        if (controller != null && sourceCard != null) {
            JelevaNephaliasWatcher watcher = (JelevaNephaliasWatcher) game.getState().getWatchers().get("ManaPaidToCastJelevaNephalias", source.getSourceId());
            if (watcher != null) {
                int xValue = watcher.getManaSpentToCastLastTime(sourceCard.getZoneChangeCounter() - 1);
                if (xValue > 0) {
                    for (UUID playerId : controller.getInRange()) {
                        Player player = game.getPlayer(playerId);
                        if (player != null) {
                            int cardsToExile = Math.min(player.getLibrary().size(), xValue);
                            for(int i = 0; i < cardsToExile; i++) {
                                Card card = player.getLibrary().removeFromTop(game);
                                if (card != null) {
                                    card.moveToExile(CardUtil.getCardExileZoneId(game, source), sourceCard.getName(), source.getSourceId(), game);
                                }
                            }
                        }
                    }
                }
                return true;
            }
        }
        return false;
    }
}

class JelevaNephaliasCastEffect extends OneShotEffect<JelevaNephaliasCastEffect> {

    public JelevaNephaliasCastEffect() {
        super(Outcome.PlayForFree);
        this.staticText = "you may cast an instant or sorcery card exiled with it without paying its mana cost";
    }

    public JelevaNephaliasCastEffect(final JelevaNephaliasCastEffect effect) {
        super(effect);
    }

    @Override
    public JelevaNephaliasCastEffect copy() {
        return new JelevaNephaliasCastEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            if (controller.chooseUse(outcome, "Cast an instant or sorcery from exile?", game)) {
                TargetCardInExile target = new TargetCardInExile(new FilterInstantOrSorceryCard(), CardUtil.getCardExileZoneId(game, source));
                if (controller.choose(Outcome.PlayForFree, game.getExile().getExileZone(CardUtil.getCardExileZoneId(game, source)), target, game)) {
                    Card card = game.getCard(target.getFirstTarget());
                    if (card != null) {
                        game.getExile().removeCard(card, game);
                        return controller.cast(card.getSpellAbility(), game, true);
                    }
                }
            }
            return true;
        }
        return false;
    }
}

class JelevaNephaliasWatcher extends WatcherImpl<JelevaNephaliasWatcher> {

    private Map<Integer, Integer> manaSpendToCast = new HashMap<Integer, Integer>(); // cast

    public JelevaNephaliasWatcher() {
        super("ManaPaidToCastJelevaNephalias", WatcherScope.CARD);
    }

    public JelevaNephaliasWatcher(final JelevaNephaliasWatcher watcher) {
        super(watcher);
    }

    @Override
    public JelevaNephaliasWatcher copy() {
        return new JelevaNephaliasWatcher(this);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.SPELL_CAST && event.getSourceId().equals(sourceId)) {
            if (!game.getStack().isEmpty()) {
                for (StackObject stackObject : game.getStack()) {
                    if (stackObject instanceof Spell && ((Spell)stackObject).getSourceId().equals(sourceId)) {
                        Card card = game.getCard(sourceId);
                        if (!manaSpendToCast.containsValue(card.getZoneChangeCounter())) {
                            manaSpendToCast.put(new Integer(card.getZoneChangeCounter()), new Integer(((Spell)stackObject).getSpellAbility().getManaCostsToPay().convertedManaCost()));
                        }
                    }
                }
            }
        }
    }

    public int getManaSpentToCastLastTime(int zoneChangeCounter) {
        if (manaSpendToCast.containsKey(zoneChangeCounter)) {
            return manaSpendToCast.get(zoneChangeCounter);
        }
        return 0;
    }

    @Override
    public void reset() {
        super.reset();
        manaSpendToCast.clear();
    }
}
