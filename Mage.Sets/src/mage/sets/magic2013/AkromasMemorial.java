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
package mage.sets.magic2013;

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.ObjectColor;
import mage.abilities.CompoundAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continious.GainAbilityControlledEffect;
import mage.abilities.keyword.*;
import mage.cards.CardImpl;
import mage.filter.FilterCard;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.ColorPredicate;

import java.util.UUID;

/**
 * @author Loki, noxx
 */
public class AkromasMemorial extends CardImpl<AkromasMemorial> {
    private static final FilterCard filterBlack = new FilterCard("Black");
    private static final FilterCard filterRed = new FilterCard("Red");

    static {
        filterBlack.add(new ColorPredicate(ObjectColor.BLACK));
        filterRed.add(new ColorPredicate(ObjectColor.RED));
    }

    public AkromasMemorial(UUID ownerId) {
        super(ownerId, 200, "Akroma's Memorial", Rarity.MYTHIC, new CardType[]{CardType.ARTIFACT}, "{7}");
        this.expansionSetCode = "M13";
        this.supertype.add("Legendary");
    }

    @Override
    public void build() {
        // Creatures you control have flying, first strike, vigilance, trample, haste, and protection from black and from red.
        CompoundAbility abilities = new CompoundAbility(FlyingAbility.getInstance(), FirstStrikeAbility.getInstance(), VigilanceAbility.getInstance(), TrampleAbility.getInstance(), HasteAbility.getInstance(), new ProtectionAbility(filterBlack), new ProtectionAbility(filterRed));
        this.addAbility(new SimpleStaticAbility(Constants.Zone.BATTLEFIELD, new GainAbilityControlledEffect(abilities, Constants.Duration.WhileOnBattlefield, new FilterControlledCreaturePermanent("Creatures you control"))));
    }

    public AkromasMemorial(final AkromasMemorial card) {
        super(card);
    }

    @Override
    public AkromasMemorial copy() {
        return new AkromasMemorial(this);
    }
}
