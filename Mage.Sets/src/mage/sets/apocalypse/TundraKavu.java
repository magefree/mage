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
package mage.sets.apocalypse;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.continuous.BecomesBasicLandTargetEffect;
import mage.cards.CardImpl;
import mage.choices.ChoiceImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetLandPermanent;

/**
 *
 * @author LoneFox
 */
public class TundraKavu extends CardImpl {

    public TundraKavu(UUID ownerId) {
        super(ownerId, 71, "Tundra Kavu", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{2}{R}");
        this.expansionSetCode = "APC";
        this.subtype.add("Kavu");
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {T}: Target land becomes a Plains or an Island until end of turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new TundraKavuEffect(), new TapSourceCost());
        ability.addTarget(new TargetLandPermanent());
        this.addAbility(ability);
    }

    public TundraKavu(final TundraKavu card) {
        super(card);
    }

    @Override
    public TundraKavu copy() {
        return new TundraKavu(this);
    }
}


class TundraKavuEffect extends BecomesBasicLandTargetEffect {

    public TundraKavuEffect() {
        super(Duration.EndOfTurn, false, true, "");
        staticText = "Target land becomes a Plains or an Island until end of turn.";
    }

    public TundraKavuEffect(final TundraKavuEffect effect) {
        super(effect);
    }

    public TundraKavuEffect copy() {
        return new TundraKavuEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        landTypes.clear();
        Player controller = game.getPlayer(source.getControllerId());
        if(controller != null) {
            Set<String> choiceSet = new LinkedHashSet<>();
            choiceSet.add("Island");
            choiceSet.add("Plains");
            ChoiceImpl choice = new ChoiceImpl(true);
            choice.setChoices(choiceSet);
            choice.setMessage("Choose a basic land type");
            controller.choose(outcome, choice, game);
            landTypes.add(choice.getChoice());
        } else {
            this.discard();
        }

        super.init(source, game);
    }
}
