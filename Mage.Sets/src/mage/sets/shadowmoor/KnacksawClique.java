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
package mage.sets.shadowmoor;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.UntapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.AsThoughEffectType;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetOpponent;
import mage.util.CardUtil;

/**
 *
 * @author jeffwadsworth

 */
public class KnacksawClique extends CardImpl<KnacksawClique> {

    public KnacksawClique(UUID ownerId) {
        super(ownerId, 42, "Knacksaw Clique", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{3}{U}");
        this.expansionSetCode = "SHM";
        this.subtype.add("Faerie");
        this.subtype.add("Rogue");

        this.color.setBlue(true);
        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        
        // {1}{U}, {untap}: Target opponent exiles the top card of his or her library. Until end of turn, you may play that card.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new KnacksawCliqueEffect(), new ManaCostsImpl("{1}{U}"));
        ability.addCost(new UntapSourceCost());
        ability.addTarget(new TargetOpponent(true));
        this.addAbility(ability);
        
    }

    public KnacksawClique(final KnacksawClique card) {
        super(card);
    }

    @Override
    public KnacksawClique copy() {
        return new KnacksawClique(this);
    }
}

class KnacksawCliqueEffect extends OneShotEffect<KnacksawCliqueEffect> {

    public KnacksawCliqueEffect() {
        super(Outcome.Benefit);
        this.staticText = "Target opponent exiles the top card of his or her library. Until end of turn, you may play that card";
    }

    public KnacksawCliqueEffect(final KnacksawCliqueEffect effect) {
        super(effect);
    }

    @Override
    public KnacksawCliqueEffect copy() {
        return new KnacksawCliqueEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player opponent = game.getPlayer(source.getFirstTarget());
        if (opponent != null) {
            Player you = game.getPlayer(source.getControllerId());
            UUID exileId = CardUtil.getCardExileZoneId(game, source);
            Card card = opponent.getLibrary().getFromTop(game);
            if (card != null 
                    && you != null) {
                card.moveToExile(exileId, "Knacksaw Clique", source.getSourceId(), game);
                if (card.getSpellAbility() != null) {
                    game.addEffect(new KnacksawCliqueCastFromExileEffect(card.getId(), exileId), source);
                }
            }
            return true;
        }
        return false;
    }
}

class KnacksawCliqueCastFromExileEffect extends AsThoughEffectImpl<KnacksawCliqueCastFromExileEffect> {

    private UUID cardId;
    private UUID exileId;

    public KnacksawCliqueCastFromExileEffect(UUID cardId, UUID exileId) {
        super(AsThoughEffectType.CAST, Duration.EndOfTurn, Outcome.Benefit);
        staticText = "Until end of turn, you may play that card";
        this.cardId = cardId;
        this.exileId = exileId;
    }

    public KnacksawCliqueCastFromExileEffect(final KnacksawCliqueCastFromExileEffect effect) {
        super(effect);
        this.cardId = effect.cardId;
        this.exileId = effect.exileId;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public KnacksawCliqueCastFromExileEffect copy() {
        return new KnacksawCliqueCastFromExileEffect(this);
    }

    @Override
    public boolean applies(UUID sourceId, Ability source, Game game) {
        if (sourceId.equals(this.cardId)) {
            Card card = game.getCard(this.cardId);
            if (card != null 
                    && game.getState().getExile().getExileZone(exileId).contains(cardId)) {
                if (card.getSpellAbility() != null 
                        && card.getSpellAbility().spellCanBeActivatedRegularlyNow(source.getControllerId(), game)) {
                    return true;
                }
            }
        }
        return false;
    }
}