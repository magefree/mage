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
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.DevotionCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continious.LoseCreatureTypeSourceEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.ColoredManaSymbol;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.game.permanent.token.Token;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCardInGraveyard;

/**
 *
 * @author LevelX2
 */
public class PharikaGodOfAffliction extends CardImpl<PharikaGodOfAffliction> {

    public PharikaGodOfAffliction(UUID ownerId) {
        super(ownerId, 154, "Pharika, God of Affliction", Rarity.MYTHIC, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "{1}{B}{G}");
        this.expansionSetCode = "JOU";
        this.supertype.add("Legendary");
        this.subtype.add("God");

        this.color.setGreen(true);
        this.color.setBlack(true);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Indestructible
        this.addAbility(IndestructibleAbility.getInstance());
        // As long as your devotion to black and green is less than seven, Pharika isn't a creature.
        Effect effect = new LoseCreatureTypeSourceEffect(new DevotionCount(ColoredManaSymbol.B, ColoredManaSymbol.G), 7);
        effect.setText("As long as your devotion to black and green is less than seven, Pharika isn't a creature");
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect));             
        // BG: Exile target creature card from a graveyard. It's owner puts a 1/1 black and green Snake enchantment creature token with deathtouch onto the battlefield.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new PharikaExileEffect(), new ManaCostsImpl("{B}{G}"));
        Target target = new TargetCardInGraveyard(new FilterCreatureCard("a creature card from a graveyard"));
        target.setRequired(true);
        ability.addTarget(target);
        this.addAbility(ability);
                
        
    }

    public PharikaGodOfAffliction(final PharikaGodOfAffliction card) {
        super(card);
    }

    @Override
    public PharikaGodOfAffliction copy() {
        return new PharikaGodOfAffliction(this);
    }
}

class PharikaExileEffect extends OneShotEffect<PharikaExileEffect> {

    public PharikaExileEffect() {
        super(Outcome.PutCreatureInPlay);
        staticText = "Exile target creature card from a graveyard. It's owner puts a 1/1 black and green Snake enchantment creature token with deathtouch onto the battlefield";
     }

    public PharikaExileEffect(final PharikaExileEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card targetCard = game.getCard(source.getFirstTarget());
        if (targetCard != null) {
            Player tokenController = game.getPlayer(targetCard.getOwnerId());
            if (tokenController != null) {
                return new PharikaSnakeToken().putOntoBattlefield(1, game, source.getSourceId(), tokenController.getId());
            }            
        }
        return false;
    }

    @Override
    public PharikaExileEffect copy() {
        return new PharikaExileEffect(this);
    }

}

class PharikaSnakeToken extends Token {

    public PharikaSnakeToken() {
        super("Snake", "1/1 black and green Snake enchantment creature token with deathtouch");
        this.setOriginalExpansionSetCode("JOU");
        cardType.add(CardType.ENCHANTMENT);
        cardType.add(CardType.CREATURE);
        subtype.add("Snake");
        color.setBlack(true);
        color.setGreen(true);
        power.setValue(1);
        toughness.setValue(1);
        this.addAbility(DeathtouchAbility.getInstance());
    }
}
