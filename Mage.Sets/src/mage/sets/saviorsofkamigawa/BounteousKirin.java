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
package mage.sets.saviorsofkamigawa;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterSpiritOrArcaneCard;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public class BounteousKirin extends CardImpl<BounteousKirin> {

    public BounteousKirin(UUID ownerId) {
        super(ownerId, 123, "Bounteous Kirin", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{5}{G}{G}");
        this.expansionSetCode = "SOK";
        this.supertype.add("Legendary");
        this.subtype.add("Kirin");
        this.subtype.add("Spirit");

        this.color.setGreen(true);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Whenever you cast a Spirit or Arcane spell, you may gain life equal to that spell's converted mana cost.
        this.addAbility(new SpellCastControllerTriggeredAbility(Zone.BATTLEFIELD, new BounteousKirinEffect(), new FilterSpiritOrArcaneCard(), true, true));
    }

    public BounteousKirin(final BounteousKirin card) {
        super(card);
    }

    @Override
    public BounteousKirin copy() {
        return new BounteousKirin(this);
    }
}

class BounteousKirinEffect extends OneShotEffect<BounteousKirinEffect> {

    public BounteousKirinEffect() {
        super(Outcome.GainLife);
        this.staticText = "you may gain life equal to that spell's converted mana cost";
    }

    public BounteousKirinEffect(final BounteousKirinEffect effect) {
        super(effect);
    }

    @Override
    public BounteousKirinEffect copy() {
        return new BounteousKirinEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Spell spell = game.getState().getStack().getSpell(getTargetPointer().getFirst(game, source));
        if (spell != null) {
            Player controller = game.getPlayer(source.getControllerId());
            if (controller != null) {
                int life = spell.getManaCost().convertedManaCost();
                controller.gainLife(life, game);
                return true;
            }
        }
        return false;
    }
}
