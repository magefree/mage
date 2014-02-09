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
package mage.sets.ravnika;

import java.util.LinkedList;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.combat.MustBeBlockedByTargetSourceEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.BlockedByIdPredicate;
import mage.filter.predicate.permanent.BlockingAttackerIdPredicate;
import mage.game.ExileZone;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author jeffwadsworth
 */
public class SistersOfStoneDeath extends CardImpl<SistersOfStoneDeath> {
    
    private UUID exileId = UUID.randomUUID();
    
    public SistersOfStoneDeath(UUID ownerId) {
        super(ownerId, 231, "Sisters of Stone Death", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{4}{B}{B}{G}{G}");
        this.expansionSetCode = "RAV";
        this.supertype.add("Legendary");
        this.subtype.add("Gorgon");
        
        this.color.setGreen(true);
        this.color.setBlack(true);
        this.power = new MageInt(7);
        this.toughness = new MageInt(5);

        // {G}: Target creature blocks Sisters of Stone Death this turn if able.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new MustBeBlockedByTargetSourceEffect(), new ManaCostsImpl("{G}"));
        ability.addTarget(new TargetCreaturePermanent(true));
        this.addAbility(ability);

        // {B}{G}: Exile target creature blocking or blocked by Sisters of Stone Death.
        Ability ability2 = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ExileTargetEffect(exileId, "Sisters Of Stone Death"), new ManaCostsImpl("{B}{G}"));
        FilterCreaturePermanent filter = new FilterCreaturePermanent("creature blocking or blocked by Sisters of Stone Death");
        filter.add(Predicates.or(new BlockedByIdPredicate(this.getId()),
                new BlockingAttackerIdPredicate(this.getId())));
        ability2.addTarget(new TargetCreaturePermanent(filter));
        this.addAbility(ability2);

        // {2}{B}: Put a creature card exiled with Sisters of Stone Death onto the battlefield under your control.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new SistersOfStoneDeathEffect(exileId), new ManaCostsImpl("{2}{B}")));
        
    }
    
    public SistersOfStoneDeath(final SistersOfStoneDeath card) {
        super(card);
    }
    
    @Override
    public SistersOfStoneDeath copy() {
        return new SistersOfStoneDeath(this);
    }
}

class SistersOfStoneDeathEffect extends OneShotEffect<SistersOfStoneDeathEffect> {
    
    private UUID exileId;
    
    public SistersOfStoneDeathEffect(UUID exileId) {
        super(Outcome.PutCreatureInPlay);
        this.exileId = exileId;
        staticText = "Put a creature card exiled with {this} onto the battlefield under your control";
    }
    
    public SistersOfStoneDeathEffect(final SistersOfStoneDeathEffect effect) {
        super(effect);
        this.exileId = effect.exileId;
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        CardsImpl cardsInExile = new CardsImpl();
        TargetCard target = new TargetCard(Zone.PICK, new FilterCard());
        target.setRequired(true);
        Player you = game.getPlayer(source.getControllerId());
        if (you != null) {
            ExileZone exile = game.getExile().getExileZone(exileId);
            if (exile != null) {
                LinkedList<UUID> cards = new LinkedList<UUID>(exile);
                for (UUID cardId : cards) {
                    Card card = game.getCard(cardId);
                    cardsInExile.add(card);
                }
                if (you.choose(Outcome.PutCreatureInPlay, cardsInExile, target, game)) {
                    Card chosenCard = game.getCard(target.getFirstTarget());
                    return you.putOntoBattlefieldWithInfo(chosenCard, game, Zone.EXILED, source.getSourceId());
                }
            }
        }
        return false;
    }
    
    @Override
    public SistersOfStoneDeathEffect copy() {
        return new SistersOfStoneDeathEffect(this);
    }
}
