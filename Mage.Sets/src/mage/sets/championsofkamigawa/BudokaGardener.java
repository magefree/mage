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
package mage.sets.championsofkamigawa;

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.FlippedCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalContinousEffect;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CopyTokenEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continious.BoostSourceEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.filter.Filter;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterLandCard;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.Token;
import mage.target.common.TargetCardInHand;

import java.util.UUID;

/**
 * @author Loki
 */
public class BudokaGardener extends CardImpl<BudokaGardener> {
    public BudokaGardener(UUID ownerId) {
        super(ownerId, 202, "Budoka Gardener", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{1}{G}");
        this.expansionSetCode = "CHK";
        this.subtype.add("Human");
        this.subtype.add("Monk");
        this.color.setGreen(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);
        this.flipCard = true;
        Ability ability = new SimpleActivatedAbility(Constants.Zone.BATTLEFIELD, new BudokaGardenerEffect(), new TapSourceCost());
        ability.addTarget(new TargetCardInHand(new FilterLandCard()));
        this.addAbility(ability);
        this.addAbility(new SimpleStaticAbility(Constants.Zone.BATTLEFIELD, new ConditionalContinousEffect(new CopyTokenEffect(new DokaiWeaverofLife()), FlippedCondition.getInstance(), "")));
    }

    public BudokaGardener(final BudokaGardener card) {
        super(card);
    }

    @Override
    public BudokaGardener copy() {
        return new BudokaGardener(this);
    }

}

class BudokaGardenerEffect extends OneShotEffect<BudokaGardenerEffect> {
    BudokaGardenerEffect() {
        super(Constants.Outcome.PutLandInPlay);
        staticText = "You may put a land card from your hand onto the battlefield. If you control ten or more lands, flip {this}";
    }

    BudokaGardenerEffect(final BudokaGardenerEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card c = game.getCard(targetPointer.getFirst(game, source));
        if (c != null) {
            c.moveToZone(Constants.Zone.BATTLEFIELD, source.getSourceId(), game, false);
        }
        if (game.getBattlefield().count(DokaiWeaverofLifeToken.filterLands, source.getControllerId(), game) >= 10) {
            Permanent p = game.getPermanent(source.getSourceId());
            if (p != null) {
                p.flip(game);
            }
        }
        return true;
    }

    @Override
    public BudokaGardenerEffect copy() {
        return new BudokaGardenerEffect(this);
    }

 }

class DokaiWeaverofLife extends Token {
    DokaiWeaverofLife() {
        super("Dokai, Weaver of Life", "");
        supertype.add("Legendary");
        cardType.add(CardType.CREATURE);
        color.setGreen(true);
        subtype.add("Human");
        subtype.add("Monk");
        power = new MageInt(3);
        toughness = new MageInt(3);
        Ability ability = new SimpleActivatedAbility(Constants.Zone.BATTLEFIELD, new CreateTokenEffect(new DokaiWeaverofLifeToken()), new ManaCostsImpl("{4}{G}{G}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }
}

class DokaiWeaverofLifeToken extends Token {
    final static FilterControlledPermanent filterLands = new FilterControlledPermanent("lands");

    static {
        filterLands.getCardType().add(CardType.LAND);
        filterLands.setScopeCardType(Filter.ComparisonScope.Any);
    }

    DokaiWeaverofLifeToken() {
        super("Elemental", "a X/X green Elemental creature token onto the battlefield, where X is the number of lands you control");
        cardType.add(CardType.CREATURE);
        color.setGreen(true);
        subtype.add("Elemental");
        power = new MageInt(0);
        toughness = new MageInt(0);
        this.addAbility(new SimpleStaticAbility(Constants.Zone.BATTLEFIELD, new BoostSourceEffect(new PermanentsOnBattlefieldCount(filterLands), new PermanentsOnBattlefieldCount(filterLands), Constants.Duration.WhileOnBattlefield)));
    }
}

