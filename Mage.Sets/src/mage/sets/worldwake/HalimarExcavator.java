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
package mage.sets.worldwake;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.Constants.TargetController;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AllyEntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.PutLibraryIntoGraveTargetEffect;
import mage.cards.CardImpl;
import mage.filter.common.FilterCreaturePermanent;
import mage.target.TargetPlayer;

/**
 *
 * @author North
 */
public class HalimarExcavator extends CardImpl<HalimarExcavator> {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Allies you control");

    static {
        filter.getSubtype().add("Ally");
        filter.setTargetController(TargetController.YOU);
    }

    public HalimarExcavator(UUID ownerId) {
        super(ownerId, 29, "Halimar Excavator", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{1}{U}");
        this.expansionSetCode = "WWK";
        this.subtype.add("Human");
        this.subtype.add("Wizard");
        this.subtype.add("Ally");

        this.color.setBlue(true);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);
        Ability ability = new AllyEntersBattlefieldTriggeredAbility(new PutLibraryIntoGraveTargetEffect(new PermanentsOnBattlefieldCount(filter)), false);
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    public HalimarExcavator(final HalimarExcavator card) {
        super(card);
    }

    @Override
    public HalimarExcavator copy() {
        return new HalimarExcavator(this);
    }
}
