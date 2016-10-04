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
package mage.sets.masterseditionii;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.costs.common.ExileFromGraveCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.SacrificeTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.Token;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author jeffwadsworth
 */
public class BalduvianDead extends CardImpl {

    public BalduvianDead(UUID ownerId) {
        super(ownerId, 79, "Balduvian Dead", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{3}{B}");
        this.expansionSetCode = "ME2";
        this.subtype.add("Zombie");
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // {2}{R}, Exile a creature card from your graveyard: Put a 3/1 black and red Graveborn creature token with haste onto the battlefield. Sacrifice it at the beginning of the next end step.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new BalduvianDeadEffect(), new ManaCostsImpl("{2}{R}"));
        TargetCardInYourGraveyard target = new TargetCardInYourGraveyard(new FilterCreatureCard());
        ability.addCost(new ExileFromGraveCost(target));
        this.addAbility(ability);

    }

    public BalduvianDead(final BalduvianDead card) {
        super(card);
    }

    @Override
    public BalduvianDead copy() {
        return new BalduvianDead(this);
    }
}

class BalduvianDeadEffect extends OneShotEffect {

    public BalduvianDeadEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "Put a 3/1 black and red Graveborn creature token with haste onto the battlefield. Sacrifice it at the beginning of the next end step";
    }

    public BalduvianDeadEffect(final BalduvianDeadEffect effect) {
        super(effect);
    }

    @Override
    public BalduvianDeadEffect copy() {
        return new BalduvianDeadEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        CreateTokenEffect effect = new CreateTokenEffect(new BalduvianToken());
        effect.apply(game, source);
        for (UUID tokenId : effect.getLastAddedTokenIds()) {
            Permanent tokenPermanent = game.getPermanent(tokenId);
            if (tokenPermanent != null) {
                SacrificeTargetEffect sacrificeEffect = new SacrificeTargetEffect("Sacrifice the token at the beginning of the next end step", source.getControllerId());
                sacrificeEffect.setTargetPointer(new FixedTarget(tokenPermanent, game));
                DelayedTriggeredAbility delayedAbility = new AtTheBeginOfNextEndStepDelayedTriggeredAbility(sacrificeEffect);
                game.addDelayedTriggeredAbility(delayedAbility, source);
            }
        }
        return true;
    }
}

class BalduvianToken extends Token {

    public BalduvianToken() {
        super("Graveborn", "3/1 black and red Graveborn creature token with haste");
        cardType.add(CardType.CREATURE);
        color.setBlack(true);
        color.setRed(true);
        power = new MageInt(3);
        toughness = new MageInt(1);
        subtype.add("Graveborn");
        addAbility(HasteAbility.getInstance());
    }
}
