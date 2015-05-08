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
package mage.sets.lorwyn;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author LevelX2
 */
public class HordeOfNotions extends CardImpl {

    private final static FilterCard filter = new FilterCard("Elemental card from your graveyard");
    
    static {
        filter.add(new SubtypePredicate("Elemental"));
    }
    
    public HordeOfNotions(UUID ownerId) {
        super(ownerId, 249, "Horde of Notions", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{W}{U}{B}{R}{G}");
        this.expansionSetCode = "LRW";
        this.supertype.add("Legendary");
        this.subtype.add("Elemental");
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());
        // Trample
        this.addAbility(TrampleAbility.getInstance());
        // Haste
        this.addAbility(HasteAbility.getInstance());
        
        // {W}{U}{B}{R}{G}: You may play target Elemental card from your graveyard without paying its mana cost.        
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new HordeOfNotionsEffect(), new ManaCostsImpl<>("{W}{U}{B}{R}{G}"));
        ability.addTarget(new TargetCardInYourGraveyard(filter));
        this.addAbility(ability);
    }

    public HordeOfNotions(final HordeOfNotions card) {
        super(card);
    }

    @Override
    public HordeOfNotions copy() {
        return new HordeOfNotions(this);
    }
}

class HordeOfNotionsEffect extends OneShotEffect {
    
    public HordeOfNotionsEffect() {
        super(Outcome.PlayForFree);
        this.staticText = "You may play target Elemental card from your graveyard without paying its mana cost";
    }
    
    public HordeOfNotionsEffect(final HordeOfNotionsEffect effect) {
        super(effect);
    }
    
    @Override
    public HordeOfNotionsEffect copy() {
        return new HordeOfNotionsEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Card card = game.getCard(getTargetPointer().getFirst(game, source));
            if (card != null ) {
                // Probably there is no Elemental land, but who knows
                if (card.getCardType().contains(CardType.LAND) && controller.canPlayLand() && 
                            controller.chooseUse(outcome, "Play " + card.getName() + " from your graveyard for free?", game)) {
                    controller.playLand(card, game);
                } else {
                    if (card.getSpellAbility().canChooseTarget(game) && 
                            controller.chooseUse(outcome, "Play " + card.getName() + " from your graveyard for free?", game)) {
                        controller.cast(card.getSpellAbility(), game, true);    
                    }
                }                
            }
            return true;
        }
        return false;
    }
}
