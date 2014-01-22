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
package mage.sets.bornofthegods;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.abilities.keyword.HeroicAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.SplitCard;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.SpellAbilityType;
import mage.constants.Zone;
import mage.filter.FilterSpell;
import mage.filter.predicate.Predicate;
import mage.game.ExileZone;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.TargetSpell;
import mage.util.CardUtil;

/**
 import mage.constants.Outcome;
*
 * @author LevelX2
 */
public class Mindreaver extends CardImpl<Mindreaver> {

    public Mindreaver(UUID ownerId) {
        super(ownerId, 44, "Mindreaver", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{U}{U}");
        this.expansionSetCode = "BNG";
        this.subtype.add("Human");
        this.subtype.add("Wizard");

        this.color.setBlue(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // <i>Heroic</i> - Whenever you cast a spell that targets Mindreaver, exile the top three cards of target player's library.
        Ability ability = new HeroicAbility(new MindreaverExileEffect(), false);
        ability.addTarget(new TargetPlayer(true));
        this.addAbility(ability);
        
        // {U}{U}, Sacrifice Mindreaver: Counter target spell with the same name as a card exiled with mindreaver.
        ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CounterTargetEffect(), new ManaCostsImpl("{U}{U}"));
        FilterSpell filter = new FilterSpell("spell with the same name as a card exiled with mindreaver");
        filter.add(new MindreaverNamePredicate(this.getId()));
        ability.addTarget(new TargetSpell(filter));
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    public Mindreaver(final Mindreaver card) {
        super(card);
    }

    @Override
    public Mindreaver copy() {
        return new Mindreaver(this);
    }
}

class MindreaverExileEffect extends OneShotEffect<MindreaverExileEffect> {

    public MindreaverExileEffect() {
        super(Outcome.Exile);
        this.staticText = "exile the top three cards of target opponent's library";
    }

    public MindreaverExileEffect(final MindreaverExileEffect effect) {
        super(effect);
    }

    @Override
    public MindreaverExileEffect copy() {
        return new MindreaverExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        UUID exileId = CardUtil.getCardExileZoneId(game, source);
        Player opponent = game.getPlayer(this.getTargetPointer().getFirst(game, source));
        if (opponent != null) {
            for (int i = 0; i < 3; i++) {
                Card card = opponent.getLibrary().getFromTop(game);
                if (card != null) {
                    card.moveToExile(exileId, "Mindreaver", source.getSourceId(), game);
                }
            }
        }
        return false;
    }
}

class MindreaverNamePredicate implements Predicate<MageObject> {
    
    private final UUID sourceId;
    
    public MindreaverNamePredicate(UUID sourceId) {
        this.sourceId = sourceId;
    }
    
    @Override
    public boolean apply(MageObject input, Game game) {
        Set<String> cardNames = new HashSet<String>();
        UUID exileId = CardUtil.getCardExileZoneId(game, sourceId);
        ExileZone exileZone = game.getExile().getExileZone(exileId);
        if (exileZone != null) {
            for(Card card : exileZone.getCards(game)) {
                cardNames.add(card.getName());
            }
        }
        // If a player names a card, the player may name either half of a split card, but not both. 
        // A split card has the chosen name if one of its two names matches the chosen name.
        if (input instanceof SplitCard) {
            return cardNames.contains(((SplitCard)input).getLeftHalfCard().getName()) || cardNames.contains(((SplitCard)input).getRightHalfCard().getName());
        } else if (input instanceof Spell && ((Spell)input).getSpellAbility().getSpellAbilityType().equals(SpellAbilityType.SPLIT_FUSED)){
            SplitCard card = (SplitCard) ((Spell)input).getCard();
            return cardNames.contains(card.getLeftHalfCard().getName()) || cardNames.contains(card.getRightHalfCard().getName());
        } else {
            return cardNames.contains(input.getName());
        }
    }

    @Override
    public String toString() {
        return "spell with the same name as a card exiled with {source}";
    }
}