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
package mage.cards.d;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.constants.ComparisonType;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.common.SimpleEvasionAbility;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.AsThoughManaEffect;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.combat.CantBeBlockedByCreaturesSourceEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.game.ExileZone;
import mage.game.Game;
import mage.players.ManaPoolItem;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

import java.util.Objects;
import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public class DaxosOfMeletis extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creatures with power 3 or greater");

    static {
        filter.add(new PowerPredicate(ComparisonType.MORE_THAN, 2));
    }

    public DaxosOfMeletis(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{W}{U}");
        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add("Human");
        this.subtype.add("Soldier");

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Daxos of Meletis can't be blocked by creatures with power 3 or greater.
        this.addAbility(new SimpleEvasionAbility(new CantBeBlockedByCreaturesSourceEffect(filter, Duration.WhileOnBattlefield)));

        // Whenever Daxos of Meletis deals combat damage to a player, exile the top card of that player's library. You gain life equal to that card's converted mana cost. Until end of turn, you may cast that card and you may spend mana as though it were mana of any color to cast it.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new DaxosOfMeletisEffect(), false, true));
    }

    public DaxosOfMeletis(final DaxosOfMeletis card) {
        super(card);
    }

    @Override
    public DaxosOfMeletis copy() {
        return new DaxosOfMeletis(this);
    }
}

class DaxosOfMeletisEffect extends OneShotEffect {

    public DaxosOfMeletisEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "exile the top card of that player's library. You gain life equal to that card's converted mana cost. Until end of turn, you may cast that card and you may spend mana as though it were mana of any color to cast it";
    }

    public DaxosOfMeletisEffect(final DaxosOfMeletisEffect effect) {
        super(effect);
    }

    @Override
    public DaxosOfMeletisEffect copy() {
        return new DaxosOfMeletisEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Player damagedPlayer = game.getPlayer(this.getTargetPointer().getFirst(game, source));
            if (damagedPlayer != null) {
                MageObject sourceObject = game.getObject(source.getSourceId());
                UUID exileId = CardUtil.getCardExileZoneId(game, source);
                Card card = damagedPlayer.getLibrary().getFromTop(game);
                if (card != null) {
                    // move card to exile
                    controller.moveCardToExileWithInfo(card, exileId, sourceObject.getIdName(), source.getSourceId(), game, Zone.LIBRARY, true);
                    // player gains life
                    int cmc = card.getConvertedManaCost();
                    if (cmc > 0) {
                        controller.gainLife(cmc, game);
                    }
                    // Add effects only if the card has a spellAbility (e.g. not for lands).
                    if (card.getSpellAbility() != null) {
                        // allow to cast the card
                        game.addEffect(new DaxosOfMeletisCastFromExileEffect(card.getId(), exileId), source);
                        // and you may spend mana as though it were mana of any color to cast it
                        ContinuousEffect effect = new DaxosOfMeletisSpendAnyManaEffect();
                        effect.setTargetPointer(new FixedTarget(card.getId()));
                        game.addEffect(effect, source);
                    }
                }
                return true;
            }
        }
        return false;
    }
}

class DaxosOfMeletisCastFromExileEffect extends AsThoughEffectImpl {

    private UUID cardId;
    private UUID exileId;

    public DaxosOfMeletisCastFromExileEffect(UUID cardId, UUID exileId) {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.EndOfTurn, Outcome.Benefit);
        staticText = "Until end of turn, you may cast that card and you may spend mana as though it were mana of any color to cast it";
        this.cardId = cardId;
        this.exileId = exileId;
    }

    public DaxosOfMeletisCastFromExileEffect(final DaxosOfMeletisCastFromExileEffect effect) {
        super(effect);
        this.cardId = effect.cardId;
        this.exileId = effect.exileId;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public DaxosOfMeletisCastFromExileEffect copy() {
        return new DaxosOfMeletisCastFromExileEffect(this);
    }

    @Override
    public boolean applies(UUID sourceId, Ability source, UUID affectedControllerId, Game game) {
        if (sourceId.equals(cardId) && source.getControllerId().equals(affectedControllerId)) {
            ExileZone exileZone = game.getState().getExile().getExileZone(exileId);
            return exileZone != null && exileZone.contains(cardId);
        }
        return false;
    }
}

class DaxosOfMeletisSpendAnyManaEffect extends AsThoughEffectImpl implements AsThoughManaEffect {

    public DaxosOfMeletisSpendAnyManaEffect() {
        super(AsThoughEffectType.SPEND_OTHER_MANA, Duration.EndOfTurn, Outcome.Benefit);
        staticText = "you may spend mana as though it were mana of any color to cast it";
    }

    public DaxosOfMeletisSpendAnyManaEffect(final DaxosOfMeletisSpendAnyManaEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public DaxosOfMeletisSpendAnyManaEffect copy() {
        return new DaxosOfMeletisSpendAnyManaEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        return source.getControllerId().equals(affectedControllerId)
                && Objects.equals(objectId, ((FixedTarget) getTargetPointer()).getTarget())
                && ((FixedTarget) getTargetPointer()).getZoneChangeCounter() + 1 == game.getState().getZoneChangeCounter(objectId)
                && (((FixedTarget) getTargetPointer()).getZoneChangeCounter() + 1 == game.getState().getZoneChangeCounter(objectId))
                && game.getState().getZone(objectId) == Zone.STACK;
    }

    @Override
    public ManaType getAsThoughManaType(ManaType manaType, ManaPoolItem mana, UUID affectedControllerId, Ability source, Game game) {
        return mana.getFirstAvailable();
    }

}
