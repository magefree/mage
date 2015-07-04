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
package mage.sets.ravnica;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.SacrificeControllerEffect;
import mage.abilities.effects.common.combat.CantBeBlockedByCreaturesSourceEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;

/**
 *
 * @author Wehk
 */
public class VindictiveMob extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("can't be blocked by Saporlings");

    static {
        filter.add(new SubtypePredicate("Saporling"));
    }
    
    public VindictiveMob(UUID ownerId) {
        super(ownerId, 112, "Vindictive Mob", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{4}{B}{B}");
        this.expansionSetCode = "RAV";
        this.subtype.add("Human");
        this.subtype.add("Berserker");
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // When Vindictive Mob enters the battlefield, sacrifice a creature.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SacrificeControllerEffect(new FilterCreaturePermanent(), 1, null)));
        
        // Vindictive Mob can't be blocked by Saprolings.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CantBeBlockedByCreaturesSourceEffect(filter, Duration.WhileOnBattlefield)));
    }

    public VindictiveMob(final VindictiveMob card) {
        super(card);
    }

    @Override
    public VindictiveMob copy() {
        return new VindictiveMob(this);
    }
}
