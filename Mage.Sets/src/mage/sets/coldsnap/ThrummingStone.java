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
package mage.sets.coldsnap;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.keyword.RippleAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.SubLayer;
import mage.constants.Zone;
import mage.filter.FilterSpell;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;
import mage.players.Player;

import java.util.UUID;

/**
 * @author klayhamn
 */
public class ThrummingStone extends CardImpl {

  //applies to all spells
  private static final FilterSpell anySpellFilter = new FilterSpell("Spells you cast");

  public ThrummingStone(UUID ownerId) {
    super(ownerId, 142, "Thrumming Stone", Rarity.RARE, new CardType[]{CardType.ARTIFACT}, "{5}");
    this.expansionSetCode = "CSP";
    this.supertype.add("Legendary");

    // spells you cast have Ripple 4
    this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ThrummingStoneGainAbilitySpellsEffect(new RippleAbility(4), anySpellFilter)));
  }

  public ThrummingStone(final ThrummingStone card) {
    super(card);
  }

  @Override
  public ThrummingStone copy() {
    return new ThrummingStone(this);
  }

}

class ThrummingStoneGainAbilitySpellsEffect extends ContinuousEffectImpl {

  private final Ability ability;
  private final FilterSpell filter;


  public ThrummingStoneGainAbilitySpellsEffect(Ability ability, FilterSpell filter) {
    super(Duration.WhileOnBattlefield, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
    this.ability = ability;
    this.filter = filter;
    staticText = filter.getMessage() + " have " + ability.getRule();
  }

  public ThrummingStoneGainAbilitySpellsEffect(final ThrummingStoneGainAbilitySpellsEffect effect) {
    super(effect);
    this.ability = effect.ability.copy();
    this.filter = effect.filter.copy();
  }

  @Override
  public ThrummingStoneGainAbilitySpellsEffect copy() {
    return new ThrummingStoneGainAbilitySpellsEffect(this);
  }

  @Override
  public boolean apply(Game game, Ability source) {
    Player player = game.getPlayer(source.getControllerId());
    Permanent permanent = game.getPermanent(source.getSourceId());
    if (player != null && permanent != null) {
      for (StackObject stackObject : game.getStack()) {
        // only spells cast, so no copies of spells
        if ((stackObject instanceof Spell) && !stackObject.isCopy() && stackObject.getControllerId().equals(source.getControllerId())) {
          Spell spell = (Spell) stackObject;
          if (filter.match(spell, game)) {
            if (!spell.getAbilities().contains(ability)) {
              game.getState().addOtherAbility(spell.getCard(), ability);
            }
          }
        }
      }
      return true;
    }
    return false;
  }
}


