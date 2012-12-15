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
package mage.sets.worldwake;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.Constants.TargetController;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.KickedCondition;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.dynamicvalue.common.MultikickerCount;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.abilities.effects.common.continious.BoostAllEffect;
import mage.abilities.keyword.MultikickerAbility;
import mage.cards.CardImpl;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author jeffwadsworth
 *
 */
public class MarshalsAnthem extends CardImpl<MarshalsAnthem> {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Creatures you control");
    private static final FilterCard filterCard = new FilterCard("creature card in your graveyard");

    static {
        filter.add(new ControllerPredicate(TargetController.YOU));
        filterCard.add(new CardTypePredicate(CardType.CREATURE));
    }

    public MarshalsAnthem(UUID ownerId) {
        super(ownerId, 15, "Marshal's Anthem", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}{W}");
        this.expansionSetCode = "WWK";

        this.color.setWhite(true);

        // Multikicker {1}{W}
        this.addAbility(new MultikickerAbility("{1}{W}"));

        // Creatures you control get +1/+1.
        this.addAbility(new SimpleStaticAbility(Constants.Zone.BATTLEFIELD, new BoostAllEffect(1, 1, Constants.Duration.WhileOnBattlefield, filter, false)));

        // When Marshal's Anthem enters the battlefield, return up to X target creature cards from your graveyard to the battlefield, where X is the number of times Marshal's Anthem was kicked.

        Ability ability = new ConditionalTriggeredAbility(
                new EntersBattlefieldTriggeredAbility(new ReturnFromGraveyardToHandTargetEffect(), false),
                KickedCondition.getInstance(),
                "When {this} enters the battlefield, return up to X target creature cards from your graveyard to the battlefield, where X is the number of times {this} was kicked.");
        this.addAbility(ability);

    }

    @Override
    public void adjustTargets(Ability ability, Game game) {
        if (ability instanceof ConditionalTriggeredAbility) {
            ability.getTargets().clear();
            int numbTargets = new MultikickerCount().calculate(game, ability);
            if (numbTargets > 0) {
                ability.addTarget(new TargetCardInYourGraveyard(0, numbTargets, filterCard));
            }
        }
    }

    public MarshalsAnthem(final MarshalsAnthem card) {
        super(card);
    }

    @Override
    public MarshalsAnthem copy() {
        return new MarshalsAnthem(this);
    }
}