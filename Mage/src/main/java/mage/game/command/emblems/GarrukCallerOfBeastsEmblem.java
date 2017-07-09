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
 *  THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ..AS IS.. AND ANY EXPRESS OR IMPLIED
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
package mage.game.command.emblems;

import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreatureCard;
import mage.game.command.Emblem;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author spjspj
 */
public class GarrukCallerOfBeastsEmblem extends Emblem {

    /**
     * Emblem: "Whenever you cast a creature spell, you may search your library
     * for a creature card, put it onto the battlefield, then shuffle your
     * library."
     */
    public GarrukCallerOfBeastsEmblem() {
        this.setName("Emblem Garruk");
        Effect effect = new SearchLibraryPutInPlayEffect(new TargetCardInLibrary(new FilterCreatureCard("creature card")), false, true, Outcome.PutCreatureInPlay);
        Ability ability = new SpellCastControllerTriggeredAbility(Zone.COMMAND, effect, StaticFilters.FILTER_SPELL_A_CREATURE, true, false);
        this.getAbilities().add(ability);
    }
}
