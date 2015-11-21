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
package mage.sets.dissension;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.Token;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author fireshoes
 */
public class RakdosGuildmage extends CardImpl {

    public RakdosGuildmage(UUID ownerId) {
        super(ownerId, 145, "Rakdos Guildmage", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{B/R}{B/R}");
        this.expansionSetCode = "DIS";
        this.subtype.add("Zombie");
        this.subtype.add("Shaman");
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // <i>({BR} can be paid with either {B} or {R}.)</i>
        // {3}{B}, Discard a card: Target creature gets -2/-2 until end of turn.
        SimpleActivatedAbility ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostTargetEffect(-2, -2, Duration.EndOfTurn), new ManaCostsImpl("{3}{B}"));
        ability.addTarget(new TargetCreaturePermanent());
        ability.addCost(new DiscardCardCost());
        this.addAbility(ability);

        // {3}{R}: Put a 2/1 red Goblin creature token with haste onto the battlefield. Exile it at the beginning of the next end step.
        SimpleActivatedAbility ability2 = new SimpleActivatedAbility(Zone.BATTLEFIELD, new RakdosGuildmageEffect(), new ManaCostsImpl("{3}{R}"));
        this.addAbility(ability2);
    }

    public RakdosGuildmage(final RakdosGuildmage card) {
        super(card);
    }

    @Override
    public RakdosGuildmage copy() {
        return new RakdosGuildmage(this);
    }
}

class RakdosGuildmageEffect extends OneShotEffect {

    public RakdosGuildmageEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "Put a 2/1 red Goblin creature token with haste onto the battlefield. Exile it at the beginning of the next end step";
    }

    public RakdosGuildmageEffect(final RakdosGuildmageEffect effect) {
        super(effect);
    }

    @Override
    public RakdosGuildmageEffect copy() {
        return new RakdosGuildmageEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Token token = new RakdosGuildmageGoblinToken();
        if (token.putOntoBattlefield(1, game, source.getSourceId(), source.getControllerId())) {
            for (UUID tokenId : token.getLastAddedTokenIds()) {
                Permanent tokenPermanent = game.getPermanent(tokenId);
                if (tokenPermanent != null) {
                    ExileTargetEffect exileEffect = new ExileTargetEffect();
                    exileEffect.setTargetPointer(new FixedTarget(tokenPermanent, game));
                    game.addDelayedTriggeredAbility(new AtTheBeginOfNextEndStepDelayedTriggeredAbility(exileEffect), source);
                }
            }
            return true;
        }
        return false;
    }
}

class RakdosGuildmageGoblinToken extends Token {

    RakdosGuildmageGoblinToken() {
        super("Goblin", "2/1 red Goblin creature token with haste");
        cardType.add(CardType.CREATURE);
        color.setRed(true);
        subtype.add("Goblin");
        power = new MageInt(2);
        toughness = new MageInt(1);
        this.addAbility(HasteAbility.getInstance());
    }
}
