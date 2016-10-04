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
package mage.sets.timespiral;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.BecomesBlockedTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.RemoveFromCombatSourceEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.effects.common.UntapSourceEffect;
import mage.abilities.keyword.FlankingAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public class GustcloakCavalier extends CardImpl {

    public GustcloakCavalier(UUID ownerId) {
        super(ownerId, 22, "Gustcloak Cavalier", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{3}{W}{W}");
        this.expansionSetCode = "TSP";
        this.subtype.add("Human");
        this.subtype.add("Knight");
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flanking
        this.addAbility(new FlankingAbility());
        
        // Whenever Gustcloak Cavalier attacks, you may tap target creature.
        Ability ability = new AttacksTriggeredAbility(new TapTargetEffect(), true);
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
        
        // Whenever Gustcloak Cavalier becomes blocked, you may untap Gustcloak Cavalier and remove it from combat.
        Ability ability2 = new BecomesBlockedTriggeredAbility(new UntapSourceEffect(), true);
        Effect effect = new RemoveFromCombatSourceEffect();
        effect.setText("and remove it from combat");
        ability2.addEffect(effect);
        this.addAbility(ability2);
    }

    public GustcloakCavalier(final GustcloakCavalier card) {
        super(card);
    }

    @Override
    public GustcloakCavalier copy() {
        return new GustcloakCavalier(this);
    }
}
