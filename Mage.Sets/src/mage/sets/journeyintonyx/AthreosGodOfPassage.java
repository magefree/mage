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
package mage.sets.journeyintonyx;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.dynamicvalue.common.DevotionCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continious.LoseCreatureTypeSourceEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.ColoredManaSymbol;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.other.OwnerPredicate;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetOpponent;

/**
 *
 * @author LevelX2
 */
public class AthreosGodOfPassage extends CardImpl<AthreosGodOfPassage> {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("another creature you own");
    
    static {
        filter.add(new AnotherPredicate());
        filter.add(new OwnerPredicate(TargetController.YOU));
    }
    
    public AthreosGodOfPassage(UUID ownerId) {
        super(ownerId, 146, "Athreos, God of Passage", Rarity.MYTHIC, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "{1}{W}{B}");
        this.expansionSetCode = "JOU";
        this.supertype.add("Legendary");
        this.subtype.add("God");

        this.color.setBlack(true);
        this.color.setWhite(true);
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // Indestructible
        this.addAbility(IndestructibleAbility.getInstance());
        // As long as your devotion to white and black is less than seven, Athreos isn't a creature.
        Effect effect = new LoseCreatureTypeSourceEffect(new DevotionCount(ColoredManaSymbol.W, ColoredManaSymbol.B), 7);
        effect.setText("As long as your devotion to white and black is less than seven, Athreos isn't a creature");
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect));        
        // Whenever another creature you own dies, return it to your hand unless target opponent pays 3 life.
        Ability ability = new DiesCreatureTriggeredAbility(new AthreosGodOfPassageReturnEffect(), false, filter, true);
        ability.addTarget(new TargetOpponent(true));
        this.addAbility(ability);
        
    }

    public AthreosGodOfPassage(final AthreosGodOfPassage card) {
        super(card);
    }

    @Override
    public AthreosGodOfPassage copy() {
        return new AthreosGodOfPassage(this);
    }
}

class AthreosGodOfPassageReturnEffect extends OneShotEffect<AthreosGodOfPassageReturnEffect> {
    
    public AthreosGodOfPassageReturnEffect() {
        super(Outcome.Benefit);
        this.staticText = "return it to your hand unless target opponent pays 3 life";
    }
    
    public AthreosGodOfPassageReturnEffect(final AthreosGodOfPassageReturnEffect effect) {
        super(effect);
    }
    
    @Override
    public AthreosGodOfPassageReturnEffect copy() {
        return new AthreosGodOfPassageReturnEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());        
        if (controller != null) {
            Permanent creature = game.getPermanentOrLKIBattlefield(this.getTargetPointer().getFirst(game, source));
            if (creature != null) {
                Player opponent = game.getPlayer(source.getFirstTarget());
                boolean paid = false;
                if (opponent != null) {
                    if (opponent.chooseUse(outcome, new StringBuilder("Pay 3 live or ").append(creature.getName()).append(" returns to ").append(controller.getName()).append("'s hand?").toString(), game)) {
                        Cost cost = new PayLifeCost(3);
                        if (!cost.pay(source, game, source.getSourceId(), opponent.getId(), false)) {
                            paid = true;
                        }
                    }            
                }
                if (opponent == null || !paid) {
                    controller.moveCardToHandWithInfo(creature, source.getSourceId(), game, null);
                }                
            }
            return true;
        }
        return false;
    }
}
