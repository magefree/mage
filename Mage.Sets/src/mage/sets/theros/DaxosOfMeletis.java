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

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.combat.CantBeBlockedByCreaturesSourceEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.AsThoughEffectType;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.Filter;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

/**
 *
 * @author LevelX2
 */
public class DaxosOfMeletis extends CardImpl<DaxosOfMeletis> {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creatures with power 3 or greater");
    static {
        filter.add(new PowerPredicate(Filter.ComparisonType.GreaterThan, 2));
    }

    public DaxosOfMeletis(UUID ownerId) {
        super(ownerId, 191, "Daxos of Meletis", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{1}{W}{U}");
        this.expansionSetCode = "THS";
        this.supertype.add("Legendary");
        this.subtype.add("Human");
        this.subtype.add("Soldier");

        this.color.setBlue(true);
        this.color.setWhite(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Daxos of Meletis can't be blocked by creatures with power 3 or greater.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CantBeBlockedByCreaturesSourceEffect(filter, Duration.WhileOnBattlefield)));

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

class DaxosOfMeletisEffect extends OneShotEffect<DaxosOfMeletisEffect> {

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
        Player damagedPlayer = game.getPlayer(this.getTargetPointer().getFirst(game, source));
        if (damagedPlayer != null) {
            Player controller = game.getPlayer(source.getControllerId());
            UUID exileId = CardUtil.getCardExileZoneId(game, source);
            Card card = damagedPlayer.getLibrary().getFromTop(game);
            if (card != null && controller != null) {
                // move card to exile
                card.moveToExile(exileId, "Daxos of Meletis", source.getSourceId(), game);
                // player gains life
                int cmc = card.getManaCost().convertedManaCost();
                if (cmc > 0) {
                    controller.gainLife(cmc, game);
                }
                // Add effects only if the card has a spellAbility (e.g. not for lands).
                if (card.getSpellAbility() != null) {
                    // allow to cast the card
                    game.addEffect(new DaxosOfMeletisCastFromExileEffect(card.getId(), exileId), source);
                    // and you may spend mana as though it were mana of any color to cast it
                    game.addEffect(new DaxosOfMeletisSpendAnyManaEffect(card.getId()), source);
                }
            }
            return true;
        }
        return false;
    }
}

class DaxosOfMeletisCastFromExileEffect extends AsThoughEffectImpl<DaxosOfMeletisCastFromExileEffect> {

    private UUID cardId;
    private UUID exileId;

    public DaxosOfMeletisCastFromExileEffect(UUID cardId, UUID exileId) {
        super(AsThoughEffectType.CAST, Duration.EndOfTurn, Outcome.Benefit);
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
    public boolean applies(UUID sourceId, Ability source, Game game) {
        if (sourceId.equals(this.cardId)) {
            Card card = game.getCard(this.cardId);
            if (card != null && game.getState().getExile().getExileZone(exileId).contains(cardId)) {
                if (card.getSpellAbility() != null && card.getSpellAbility().spellCanBeActivatedRegularlyNow(source.getControllerId(), game)) {
                    return true;
                }
            }
        }
        return false;
    }
}

class DaxosOfMeletisSpendAnyManaEffect extends AsThoughEffectImpl<DaxosOfMeletisSpendAnyManaEffect> {

    private final UUID cardId;

    public DaxosOfMeletisSpendAnyManaEffect(UUID cardId) {
        super(AsThoughEffectType.SPEND_ANY_MANA, Duration.EndOfTurn, Outcome.Benefit);
        staticText = "you may spend mana as though it were mana of any color to cast it";
        this.cardId = cardId;
    }

    public DaxosOfMeletisSpendAnyManaEffect(final DaxosOfMeletisSpendAnyManaEffect effect) {
        super(effect);
        this.cardId = effect.cardId;
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
    public boolean applies(UUID sourceId, Ability source, Game game) {
        return sourceId.equals(this.cardId);
    }
}
