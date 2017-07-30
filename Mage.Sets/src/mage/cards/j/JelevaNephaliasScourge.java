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
package mage.cards.j;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SuperType;
import mage.constants.WatcherScope;
import mage.constants.Zone;
import mage.filter.common.FilterInstantOrSorceryCard;
import mage.game.ExileZone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;
import mage.players.Player;
import mage.target.common.TargetCardInExile;
import mage.util.CardUtil;
import mage.watchers.Watcher;

/**
 *
 * @author LevelX2
 */
public class JelevaNephaliasScourge extends CardImpl {

    public JelevaNephaliasScourge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}{B}{R}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add("Vampire");
        this.subtype.add("Wizard");

        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // When Jeleva, Nephalia's Scourge enters the battlefield, each player exiles the top X cards of his or her library, where X is the amount of mana spent to cast Jeleva.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new JelevaNephaliasScourgeEffect(), false));
        // Whenever Jeleva attacks, you may cast an instant or sorcery card exiled with it without paying its mana cost.
        this.addAbility(new AttacksTriggeredAbility(new JelevaNephaliasCastEffect(), false), new JelevaNephaliasWatcher());

    }

    public JelevaNephaliasScourge(final JelevaNephaliasScourge card) {
        super(card);
    }

    @Override
    public JelevaNephaliasScourge copy() {
        return new JelevaNephaliasScourge(this);
    }
}

class JelevaNephaliasScourgeEffect extends OneShotEffect {

    public JelevaNephaliasScourgeEffect() {
        super(Outcome.Benefit);
        this.staticText = "each player exiles the top X cards of his or her library, where X is the amount of mana spent to cast {this}";
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
        MageObject sourceObject = source.getSourceObject(game);
        JelevaNephaliasWatcher watcher = (JelevaNephaliasWatcher) game.getState().getWatchers().get(JelevaNephaliasWatcher.class.getSimpleName());
        if (controller != null && sourceObject != null && watcher != null) {
            int xValue = watcher.getManaSpentToCastLastTime(sourceObject.getId(), sourceObject.getZoneChangeCounter(game) - 1);
            if (xValue > 0) {
                for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                    Player player = game.getPlayer(playerId);
                    if (player != null) {
                        player.moveCards(player.getLibrary().getTopCards(game, xValue), Zone.EXILED, source, game);
                    }
                }
            }
            return true;
        }
        return false;
    }
}

class JelevaNephaliasCastEffect extends OneShotEffect {

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
            ExileZone exileZone = game.getExile().getExileZone(CardUtil.getCardExileZoneId(game, source));
            if (exileZone != null && exileZone.count(new FilterInstantOrSorceryCard(), game) > 0) {
                if (controller.chooseUse(outcome, "Cast an instant or sorcery card from exile?", source, game)) {
                    TargetCardInExile target = new TargetCardInExile(new FilterInstantOrSorceryCard(), CardUtil.getCardExileZoneId(game, source));
                    if (controller.choose(Outcome.PlayForFree, exileZone, target, game)) {
                        Card card = game.getCard(target.getFirstTarget());
                        if (card != null) {
                            return controller.cast(card.getSpellAbility(), game, true);
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }
}

class JelevaNephaliasWatcher extends Watcher {

    private final Map<String, Integer> manaSpendToCast = new HashMap<>(); // cast

    public JelevaNephaliasWatcher() {
        super(JelevaNephaliasWatcher.class.getSimpleName(), WatcherScope.GAME);
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
        // Watcher saves all casts becaus of possible Clone cards that copy Jeleva
        if (event.getType() == GameEvent.EventType.SPELL_CAST) {
            if (!game.getStack().isEmpty()) {
                for (StackObject stackObject : game.getStack()) {
                    if (stackObject instanceof Spell) {
                        Spell spell = (Spell) stackObject;
                        manaSpendToCast.putIfAbsent(spell.getSourceId().toString() + spell.getCard().getZoneChangeCounter(game),
                                spell.getSpellAbility().getManaCostsToPay().convertedManaCost());
                    }
                }
            }
        }
    }

    public int getManaSpentToCastLastTime(UUID sourceId, int zoneChangeCounter) {
        return manaSpendToCast.getOrDefault(sourceId.toString() + zoneChangeCounter, 0);
    }

    @Override
    public void reset() {
        super.reset();
        manaSpendToCast.clear();
    }
}
