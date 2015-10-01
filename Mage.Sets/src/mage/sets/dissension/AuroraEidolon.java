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
package mage.sets.dissension;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.PreventDamageToTargetEffect;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToHandEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.FilterSpell;
import mage.filter.predicate.mageobject.MulticoloredPredicate;
import mage.target.common.TargetCreatureOrPlayer;

/**
 *
 * @author LoneFox
 */
public class AuroraEidolon extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("a multicolored spell");

    static {
        filter.add(new MulticoloredPredicate());
    }

    public AuroraEidolon(UUID ownerId) {
        super(ownerId, 1, "Aurora Eidolon", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{3}{W}");
        this.expansionSetCode = "DIS";
        this.subtype.add("Spirit");
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {W}, Sacrifice Aurora Eidolon: Prevent the next 3 damage that would be dealt to target creature or player this turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new PreventDamageToTargetEffect(Duration.EndOfTurn, 3), new ManaCostsImpl("{W}"));
        ability.addCost(new SacrificeSourceCost());
        ability.addTarget(new TargetCreatureOrPlayer());
        this.addAbility(ability);
        // Whenever you cast a multicolored spell, you may return Aurora Eidolon from your graveyard to your hand.
        this.addAbility(new SpellCastControllerTriggeredAbility(Zone.GRAVEYARD, new ReturnSourceFromGraveyardToHandEffect(), filter, true, false));
    }

    public AuroraEidolon(final AuroraEidolon card) {
        super(card);
    }

    @Override
    public AuroraEidolon copy() {
        return new AuroraEidolon(this);
    }
}
