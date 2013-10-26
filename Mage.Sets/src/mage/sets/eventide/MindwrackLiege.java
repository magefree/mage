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
package mage.sets.eventide;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continious.BoostAllEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterCreatureCard;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInHand;

/**
 *
 * @author jeffwadsworth

 */
public class MindwrackLiege extends CardImpl<MindwrackLiege> {
    
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("blue creatures you control");
    private static final FilterCreaturePermanent filter2 = new FilterCreaturePermanent("red creatures you control");
    static {
        filter.add(new ColorPredicate(ObjectColor.BLUE));
        filter.add(new ControllerPredicate(TargetController.YOU));
        
        filter2.add(new ColorPredicate(ObjectColor.RED));
        filter2.add(new ControllerPredicate(TargetController.YOU));
    }

    public MindwrackLiege(UUID ownerId) {
        super(ownerId, 104, "Mindwrack Liege", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{3}{U/R}{U/R}{U/R}");
        this.expansionSetCode = "EVE";
        this.subtype.add("Horror");

        this.color.setRed(true);
        this.color.setBlue(true);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Other blue creatures you control get +1/+1.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostAllEffect(1, 1, Duration.WhileOnBattlefield, filter, true)));
        
        // Other red creatures you control get +1/+1.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostAllEffect(1, 1, Duration.WhileOnBattlefield, filter2, true)));
        
        // {UR}{UR}{UR}{UR}: You may put a blue or red creature card from your hand onto the battlefield.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new MindwrackLiegeEffect(), new ManaCostsImpl("{U/R}{U/R}{U/R}{U/R}")));
        
    }

    public MindwrackLiege(final MindwrackLiege card) {
        super(card);
    }

    @Override
    public MindwrackLiege copy() {
        return new MindwrackLiege(this);
    }
}

class MindwrackLiegeEffect extends OneShotEffect<MindwrackLiegeEffect> {

    private static final String choiceText = "Put a blue or red creature card from your hand onto the battlefield?";
    
    private static final FilterCreatureCard filter = new FilterCreatureCard("a blue or red creature card");
    
    static {
        filter.add(Predicates.or(
                new ColorPredicate(ObjectColor.BLUE),
                new ColorPredicate(ObjectColor.RED)));
    }

    public MindwrackLiegeEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "You may put a blue or red creature card from your hand onto the battlefield";
    }

    public MindwrackLiegeEffect(final MindwrackLiegeEffect effect) {
        super(effect);
    }

    @Override
    public MindwrackLiegeEffect copy() {
        return new MindwrackLiegeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null || !player.chooseUse(Outcome.PutCreatureInPlay, choiceText, game)) {
            return false;
        }

        TargetCardInHand target = new TargetCardInHand(filter);
        if (player.choose(Outcome.PutCreatureInPlay, target, source.getSourceId(), game)) {
            Card card = game.getCard(target.getFirstTarget());
            if (card != null) {
                card.putOntoBattlefield(game, Zone.HAND, source.getId(), source.getControllerId());
                return true;
            }
        }
        return false;
    }
}