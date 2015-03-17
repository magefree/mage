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
package mage.sets.morningtide;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.repository.CardRepository;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCreatureOrPlayer;

/**
 *
 * @author michael.napoleon@gmail.com
 */
public class RoarOfTheCrowd extends CardImpl {

    public RoarOfTheCrowd(UUID ownerId) {
        super(ownerId, 100, "Roar of the Crowd", Rarity.COMMON, new CardType[]{CardType.SORCERY}, "{3}{R}");
        this.expansionSetCode = "MOR";

        // Choose a creature type. Roar of the Crowd deals damage to target creature or player equal to the number of permanents you control of the chosen type.
        TargetCreatureOrPlayer target = new TargetCreatureOrPlayer();
        this.getSpellAbility().addTarget(target);
        this.getSpellAbility().addEffect(new RoarOfTheCrowdEffect());
    }

    public RoarOfTheCrowd(final RoarOfTheCrowd card) {
        super(card);
    }

    @Override
    public RoarOfTheCrowd copy() {
        return new RoarOfTheCrowd(this);
    }
}

class RoarOfTheCrowdEffect extends OneShotEffect {
  
  RoarOfTheCrowdEffect() {
    super(Outcome.LoseLife);
    this.staticText = "Choose a creature type. {this} deals damage to target creature or player equal to the number of permanents you control of the chosen type.";
  }

  RoarOfTheCrowdEffect(final RoarOfTheCrowdEffect effect) {
    super(effect);
  }
  
  @Override
  public RoarOfTheCrowdEffect copy() {
    return new RoarOfTheCrowdEffect(this);
  }
  
  @Override public boolean apply(Game game, Ability source) {
    Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            Choice typeChoice = new ChoiceImpl(true);
            typeChoice.setMessage("Choose a creature type:");
            typeChoice.setChoices(CardRepository.instance.getCreatureTypes());
            while (!player.choose(Outcome.LoseLife, typeChoice, game)) {
                if (!player.isInGame()) {
                    return false;
                }
            }
            FilterControlledPermanent filter = new FilterControlledPermanent();
            filter.add(new SubtypePredicate(typeChoice.getChoice()));
            return new DamageTargetEffect(new PermanentsOnBattlefieldCount(filter)).apply(game, source);
        }
        return false;
  }
}