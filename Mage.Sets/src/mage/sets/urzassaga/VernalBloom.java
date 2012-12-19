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
package mage.sets.urzassaga;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.ManaEffect;
import mage.abilities.mana.TriggeredManaAbility;
import mage.cards.CardImpl;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author Plopman
 */
public class VernalBloom extends CardImpl<VernalBloom> {

    public VernalBloom(UUID ownerId) {
        super(ownerId, 281, "Vernal Bloom", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{3}{G}");
        this.expansionSetCode = "USG";

        this.color.setGreen(true);

        // Whenever a Forest is tapped for mana, its controller adds {G} to his or her mana pool.
        this.addAbility(new VernalBloomTriggeredAbility());
    }

    public VernalBloom(final VernalBloom card) {
        super(card);
    }

    @Override
    public VernalBloom copy() {
        return new VernalBloom(this);
    }
}

class VernalBloomTriggeredAbility extends TriggeredManaAbility<VernalBloomTriggeredAbility> {

    private final static FilterLandPermanent filter = new FilterLandPermanent("Forest");
    static {
            filter.add(new SubtypePredicate("Forest"));
    }

    public VernalBloomTriggeredAbility() {
        super(Constants.Zone.BATTLEFIELD, new AddGreenToTargetEffect());
        this.usesStack = false;
    }

    public VernalBloomTriggeredAbility(VernalBloomTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent land = game.getPermanent(event.getTargetId());
        if (event.getType() == GameEvent.EventType.TAPPED_FOR_MANA && land != null && filter.match(land, game)) {
            for (Effect effect : this.getEffects()) {
                effect.setTargetPointer(new FixedTarget(land.getControllerId()));
            }
            return true;
        }
        return false;
    }

    @Override
    public VernalBloomTriggeredAbility copy() {
        return new VernalBloomTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "Whenever a Forest is tapped for mana, its controller adds {G} to his or her mana pool";
    }
}

class AddGreenToTargetEffect extends ManaEffect<AddGreenToTargetEffect> {
    

    public AddGreenToTargetEffect() {
        super();
        staticText = "its controller adds {G} to his or her mana pool";
    }


    public AddGreenToTargetEffect(final AddGreenToTargetEffect effect) {
        super(effect);
    }

    @Override
    public AddGreenToTargetEffect copy() {
        return new AddGreenToTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(this.getTargetPointer().getFirst(game, source));
        if(player != null)
        {
            player.getManaPool().addMana(Mana.GreenMana(1), game, source);

        }
        return true;
    }

}