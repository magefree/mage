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
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.UntapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.choices.ChoiceColor;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author jeffwadsworth
 *
 */
public class PaleWayfarer extends CardImpl<PaleWayfarer> {

    public PaleWayfarer(UUID ownerId) {
        super(ownerId, 17, "Pale Wayfarer", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{5}{W}{W}");
        this.expansionSetCode = "SHM";
        this.subtype.add("Spirit");
        this.subtype.add("Giant");

        this.color.setWhite(true);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // {2}{W}{W}, {untap}: Target creature gains protection from the color of its controller's choice until end of turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new PaleWayfarerEffect(), new ManaCostsImpl("{2}{W}{W}"));
        ability.addCost(new UntapSourceCost());
        ability.addTarget(new TargetCreaturePermanent(true));
        this.addAbility(ability);

    }

    public PaleWayfarer(final PaleWayfarer card) {
        super(card);
    }

    @Override
    public PaleWayfarer copy() {
        return new PaleWayfarer(this);
    }
}

class PaleWayfarerEffect extends OneShotEffect<PaleWayfarerEffect> {

    public PaleWayfarerEffect() {
        super(Outcome.BoostCreature);
        staticText = "Target creature gains protection from the color of its controller's choice until end of turn";
    }

    public PaleWayfarerEffect(final PaleWayfarerEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent targetCreature = game.getPermanent(source.getFirstTarget());
        if (targetCreature != null) {
            Player player = game.getPlayer(targetCreature.getControllerId());
            if (player != null) {
                ChoiceColor colorChoice = new ChoiceColor();
                if (player.choose(Outcome.Neutral, colorChoice, game)) {
                    game.informPlayers(targetCreature.getName() + ": " + player.getName() + " has chosen " + colorChoice.getChoice());
                    game.getState().setValue(targetCreature.getId() + "_color", colorChoice.getColor());
                }
            }
        }
        return false;
    }

    @Override
    public PaleWayfarerEffect copy() {
        return new PaleWayfarerEffect(this);
    }
}