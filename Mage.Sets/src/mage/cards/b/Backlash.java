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
package mage.sets.invasion;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author michael.napoleon@gmail.com
 */
public class Backlash extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("untapped creature");
    static {
        filter.add(Predicates.not(new TappedPredicate()));
    }
    
    public Backlash(UUID ownerId) {
        super(ownerId, 234, "Backlash", Rarity.UNCOMMON, new CardType[]{CardType.INSTANT}, "{1}{B}{R}");
        this.expansionSetCode = "INV";

        // Tap target untapped creature. That creature deals damage equal to its power to its controller.
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(filter));
        this.getSpellAbility().addEffect(new BacklashEffect());
    }

    public Backlash(final Backlash card) {
        super(card);
    }

    @Override
    public Backlash copy() {
        return new Backlash(this);
    }
}

class BacklashEffect extends OneShotEffect {
  
  public BacklashEffect() {
    super(Outcome.Detriment);
    this.staticText = "Tap target untapped creature. That creature deals damage equal to its power to its controller.";
  }
  
  public BacklashEffect(final BacklashEffect effect) {
    super(effect);
  }
  
  @Override
  public BacklashEffect copy () {
    return new BacklashEffect(this);
  }
  
  @Override
  public boolean apply(Game game, Ability source) {
    boolean applied = false;
    Permanent targetCreature = game.getPermanent(targetPointer.getFirst(game, source));
    if (targetCreature != null) {
      applied = targetCreature.tap(game);
      Player controller = game.getPlayer(targetCreature.getControllerId());
      if (controller != null) {
        controller.damage(targetCreature.getPower().getValue(), source.getSourceId(), game, false, true);
        applied = true;
      }
    }
    return applied;
  }
}
