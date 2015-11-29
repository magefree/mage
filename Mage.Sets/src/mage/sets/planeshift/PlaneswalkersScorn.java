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
package mage.sets.planeshift;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCreaturePermanent;
import mage.target.common.TargetOpponent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author fireshoes
 */
public class PlaneswalkersScorn extends CardImpl {

    public PlaneswalkersScorn(UUID ownerId) {
        super(ownerId, 52, "Planeswalker's Scorn", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{2}{B}");
        this.expansionSetCode = "PLS";

        // {3}{B}: Target opponent reveals a card at random from his or her hand. Target creature gets -X/-X until end of turn, where X is the revealed card's converted mana cost. Activate this ability only any time you could cast a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(Zone.BATTLEFIELD, new PlaneswalkersScornEffect(), new ManaCostsImpl("{3}{B}"));
        Target target = new TargetOpponent();
        ability.addTarget(target);
        target = new TargetCreaturePermanent();
        ability.addTarget(target);
        this.addAbility(ability);
    }

    public PlaneswalkersScorn(final PlaneswalkersScorn card) {
        super(card);
    }

    @Override
    public PlaneswalkersScorn copy() {
        return new PlaneswalkersScorn(this);
    }
}

class PlaneswalkersScornEffect extends OneShotEffect {

    public PlaneswalkersScornEffect() {
        super(Outcome.Damage);
        staticText = "Target opponent reveals a card at random from his or her hand. Target creature gets -X/-X until end of turn, where X is the revealed card's converted mana cost";
    }

    public PlaneswalkersScornEffect(final PlaneswalkersScornEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player opponent = game.getPlayer(source.getTargets().get(0).getFirstTarget());
        if (opponent != null && opponent.getHand().size() > 0) {
            Cards revealed = new CardsImpl();
            Card card = opponent.getHand().getRandom(game);
            if (card != null) {
                revealed.add(card);
                int boostValue = -1 * card.getManaCost().convertedManaCost();
                opponent.revealCards("Planeswalker's Scorn", revealed, game);
                ContinuousEffect effect = new BoostTargetEffect(boostValue, boostValue, Duration.EndOfTurn);
                effect.setTargetPointer(new FixedTarget(source.getTargets().get(1).getFirstTarget()));
                game.addEffect(effect, source);
            }
            return true;
        }
        return false;
    }

    @Override
    public PlaneswalkersScornEffect copy() {
        return new PlaneswalkersScornEffect(this);
    }

}