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
package mage.sets.shadowmoor;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.delayed.AtEndOfTurnDelayedTriggeredAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnFromExileEffect;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author North
 */
public class MistmeadowWitch extends CardImpl<MistmeadowWitch> {

    public MistmeadowWitch(UUID ownerId) {
        super(ownerId, 144, "Mistmeadow Witch", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{1}{W/U}");
        this.expansionSetCode = "SHM";
        this.subtype.add("Kithkin");
        this.subtype.add("Wizard");

        this.color.setBlue(true);
        this.color.setWhite(true);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {2}{W}{U}: Exile target creature. Return that card to the battlefield under its owner's control at the beginning of the next end step.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new MistmeadowWitchEffect(), new ManaCostsImpl("{2}{W}{U}"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    public MistmeadowWitch(final MistmeadowWitch card) {
        super(card);
    }

    @Override
    public MistmeadowWitch copy() {
        return new MistmeadowWitch(this);
    }
}

class MistmeadowWitchEffect extends OneShotEffect<MistmeadowWitchEffect> {

    public MistmeadowWitchEffect() {
        super(Outcome.Detriment);
        staticText = "Exile target creature. Return that card to the battlefield under its owner's control at the beginning of the next end step";
    }

    public MistmeadowWitchEffect(final MistmeadowWitchEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent != null) {
            if (permanent.moveToExile(source.getSourceId(), "Mistmeadow Witch Exile", source.getId(), game)) {
                //create delayed triggered ability
                AtEndOfTurnDelayedTriggeredAbility delayedAbility = new AtEndOfTurnDelayedTriggeredAbility(new ReturnFromExileEffect(source.getSourceId(), Zone.BATTLEFIELD, true));
                delayedAbility.setSourceId(source.getSourceId());
                delayedAbility.setControllerId(source.getControllerId());
                game.addDelayedTriggeredAbility(delayedAbility);
                return true;
            }
        }
        return false;
    }

    @Override
    public MistmeadowWitchEffect copy() {
        return new MistmeadowWitchEffect(this);
    }
}
