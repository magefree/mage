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
package mage.sets.theros;

import java.util.LinkedList;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.constants.WatcherScope;
import mage.constants.Zone;
import mage.filter.common.FilterControlledLandPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.ExileZone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.watchers.WatcherImpl;

/**
 * If the land Chained to the Rocks is enchanting stops being a Mountain or another player
 * gains control of it, Chained to the Rocks will be put into its owner's graveyard when
 * state-based actions are performed.
 *
 * Chained to the Rocks's ability causes a zone change with a duration, a style of ability
 * introduced in Magic 2014 that's somewhat reminiscent of older cards like Oblivion Ring.
 * However, unlike Oblivion Ring, cards like Chained to the Rocks have a single ability
 * that creates two one-shot effects: one that exiles the creature when the ability resolves,
 * and another that returns the exiled card to the battlefield immediately after Chained to
 * the Rocks leaves the battlefield.
 *
 * If Chained to the Rocks leaves the battlefield before its triggered ability resolves,
 * the target creature won't be exiled.
 *
 * Auras attached to the exiled creature will be put into their owners' graveyards (unless
 * they have bestow). Equipment attached to the exiled creature will become unattached and
 * remain on the battlefield. Any counters on the exiled creature will cease to exist.
 *
 * If a creature token is exiled, it ceases to exist. It won't be returned to the battlefield.
 *
 * The exiled card returns to the battlefield immediately after Chained to the Rocks leaves
 * the battlefield. Nothing happens between the two events, including state-based actions.
 *
 * In a multiplayer game, if Chained to the Rocks's owner leaves the game, the exiled card
 * will return to the battlefield. Because the one-shot effect that returns the card isn't
 * an ability that goes on the stack, it won't cease to exist along with the leaving player's
 * spells and abilities on the stack.
 *
 * @author LevelX2
 */
public class ChainedToTheRocks extends CardImpl<ChainedToTheRocks> {

    private static final FilterControlledLandPermanent filter = new FilterControlledLandPermanent("Mountain you control");
    private static final FilterCreaturePermanent filterTarget = new FilterCreaturePermanent("creature an opponent controls");
    static {
        filter.add(new SubtypePredicate("Mountain"));
        filterTarget.add(new ControllerPredicate(TargetController.OPPONENT));
    }

    public ChainedToTheRocks(UUID ownerId) {
        super(ownerId, 4, "Chained to the Rocks", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{W}");
        this.expansionSetCode = "THS";
        this.subtype.add("Aura");

        this.color.setWhite(true);

        // Enchant Mountain you control
        TargetPermanent auraTarget = new TargetPermanent(filter);
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Exile));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);

        // When Chained to the Rocks enters the battlefield, exile target creature an opponent controls until Chained to the Rocks leaves the battlefield. (That creature returns under its owner's control.)
        ability = new EntersBattlefieldTriggeredAbility(new ChainedToTheRocksEffect());
        ability.addTarget(new TargetCreaturePermanent(filterTarget));
        this.addAbility(ability);
        this.addWatcher(new ChainedToTheRocksWatcher(this.getId()));

    }

    public ChainedToTheRocks(final ChainedToTheRocks card) {
        super(card);
    }

    @Override
    public ChainedToTheRocks copy() {
        return new ChainedToTheRocks(this);
    }
}

class ChainedToTheRocksEffect extends OneShotEffect<ChainedToTheRocksEffect> {

    public ChainedToTheRocksEffect() {
        super(Outcome.Benefit);
        this.staticText = "exile target creature an opponent controls until {this} leaves the battlefield. <i>(That creature returns under its owner's control.)</i>";
    }

    public ChainedToTheRocksEffect(final ChainedToTheRocksEffect effect) {
        super(effect);
    }

    @Override
    public ChainedToTheRocksEffect copy() {
        return new ChainedToTheRocksEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        // If Chained to the Rocks leaves the battlefield before its triggered ability resolves,
        // the target creature won't be exiled.
        if (permanent != null) {
            new ExileTargetEffect(source.getSourceId(), permanent.getName()).apply(game, source);
        }
        return false;
    }
}

class ChainedToTheRocksWatcher extends WatcherImpl<ChainedToTheRocksWatcher> {

    ChainedToTheRocksWatcher (UUID exileId) {
        super("BattlefieldLeft", WatcherScope.CARD);
    }

    ChainedToTheRocksWatcher(final ChainedToTheRocksWatcher watcher) {
        super(watcher);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.ZONE_CHANGE && event.getTargetId().equals(this.getSourceId())) {
            ZoneChangeEvent zEvent = (ZoneChangeEvent)event;
            if (zEvent.getFromZone() == Zone.BATTLEFIELD) {
                ExileZone exile = game.getExile().getExileZone(this.getSourceId());
                Card sourceCard = game.getCard(this.getSourceId());
                if (exile != null && sourceCard != null) {
                    LinkedList<UUID> cards = new LinkedList<>(exile);
                    for (UUID cardId: cards) {
                        Card card = game.getCard(cardId);
                        card.moveToZone(Zone.BATTLEFIELD, this.getSourceId(), game, false);
                        game.informPlayers(new StringBuilder(sourceCard.getName()).append(": ").append(card.getName()).append(" was returned to battlefield from exile").toString());
                    }
                    exile.clear();
                }
            }
        }
    }

    @Override
    public void reset() {
        //don't reset condition each turn - only when this leaves the battlefield
    }

    @Override
    public ChainedToTheRocksWatcher copy() {
        return new ChainedToTheRocksWatcher(this);
    }
}
