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
package mage.sets.worldwake;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Duration;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.keyword.IslandwalkAbility;
import mage.abilities.keyword.SwampwalkAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.other.OwnerIdPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.events.ZoneChangeEvent;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCardInGraveyard;

/**
 *
 * @author jeffwadsworth
 */
public class WrexialTheRisenDeep extends CardImpl<WrexialTheRisenDeep> {

    public WrexialTheRisenDeep(UUID ownerId) {
        super(ownerId, 120, "Wrexial, the Risen Deep", Rarity.MYTHIC, new CardType[]{CardType.CREATURE}, "{3}{U}{U}{B}");
        this.expansionSetCode = "WWK";
        this.supertype.add("Legendary");
        this.subtype.add("Kraken");

        this.color.setBlue(true);
        this.color.setBlack(true);
        this.power = new MageInt(5);
        this.toughness = new MageInt(8);

        // Islandwalk
        this.addAbility(new IslandwalkAbility());
        // Swampwalk
        this.addAbility(new SwampwalkAbility());

        // Whenever Wrexial, the Risen Deep deals combat damage to a player, you may cast target instant or sorcery card from that player's graveyard without paying its mana cost. If that card would be put into a graveyard this turn, exile it instead.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new WrexialEffect(), true, true));
    }

    public WrexialTheRisenDeep(final WrexialTheRisenDeep card) {
        super(card);
    }

    @Override
    public WrexialTheRisenDeep copy() {
        return new WrexialTheRisenDeep(this);
    }
}

class WrexialEffect extends OneShotEffect<WrexialEffect> {

    static final private FilterCard filter = new FilterCard("target instant or sorcery card from damaged player's graveyard");

    public WrexialEffect() {
        super(Constants.Outcome.PlayForFree);
        staticText = "you may cast target instant or sorcery card from that player's graveyard without paying its mana cost. If that card would be put into a graveyard this turn, exile it instead";
    }

    public WrexialEffect(final WrexialEffect effect) {
        super(effect);
    }

    @Override
    public WrexialEffect copy() {
        return new WrexialEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player you = game.getPlayer(source.getControllerId());
        Player damagedPlayer = game.getPlayer(targetPointer.getFirst(game, source));

        filter.add(new OwnerIdPredicate(damagedPlayer.getId()));
        filter.add(Predicates.or(
                new CardTypePredicate(CardType.INSTANT),
                new CardTypePredicate(CardType.SORCERY)));

        Target target = new TargetCardInGraveyard(filter);

        if (you != null && target != null) {
            if (you.chooseTarget(Constants.Outcome.PlayForFree, target, source, game)) {
                Card card = game.getCard(target.getFirstTarget());
                if (card != null) {
                    you.cast(card.getSpellAbility(), game, true);
                    game.addEffect(new WrexialReplacementEffect(card.getId()), source);
                }
            }
        }
        return false;
    }
}

class WrexialReplacementEffect extends ReplacementEffectImpl<WrexialReplacementEffect> {

    private UUID cardid;

    public WrexialReplacementEffect(UUID cardid) {
        super(Duration.EndOfTurn, Outcome.Exile);
        this.cardid = cardid;
    }

    public WrexialReplacementEffect(final WrexialReplacementEffect effect) {
        super(effect);
        this.cardid = effect.cardid;
    }

    @Override
    public WrexialReplacementEffect copy() {
        return new WrexialReplacementEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        UUID eventObject = ((ZoneChangeEvent) event).getTargetId();
        StackObject card = game.getStack().getStackObject(eventObject);
        if (card instanceof Spell) {
            game.rememberLKI(card.getId(), Zone.STACK, (Spell) card);
        }
        if (card instanceof Card && card != null && eventObject == cardid) {
            ((Card) card).moveToExile(id, "Wrexial, The Risen Deep", id, game);
            return true;
        }
        return false;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getType() == EventType.ZONE_CHANGE) {
            ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
            if (zEvent.getToZone() == Zone.GRAVEYARD
                    && ((ZoneChangeEvent) event).getTargetId() == cardid) {
                return true;
            }
        }
        return false;
    }
}