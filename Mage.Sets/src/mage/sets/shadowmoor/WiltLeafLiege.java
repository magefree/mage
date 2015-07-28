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
import mage.ObjectColor;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.DiscardOntoBattlefieldEffect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.filter.predicate.permanent.ControllerPredicate;

/**
 *
 * @author Plopman
 */
public class WiltLeafLiege extends CardImpl {

    private static final FilterCreaturePermanent filterGreen = new FilterCreaturePermanent("green creatures you control");
    private static final FilterCreaturePermanent filterWhite = new FilterCreaturePermanent("white creatures you control");
    static {
        filterGreen.add(new ColorPredicate(ObjectColor.GREEN));
        filterGreen.add(new ControllerPredicate(TargetController.YOU));
        filterWhite.add(new ColorPredicate(ObjectColor.WHITE));
        filterWhite.add(new ControllerPredicate(TargetController.YOU));
    }

    public WiltLeafLiege(UUID ownerId) {
        super(ownerId, 245, "Wilt-Leaf Liege", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{1}{G/W}{G/W}{G/W}");
        this.expansionSetCode = "SHM";
        this.subtype.add("Elf");
        this.subtype.add("Knight");

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Other green creatures you control get +1/+1.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostAllEffect(1, 1, Duration.WhileOnBattlefield, filterGreen, true)));
        // Other white creatures you control get +1/+1.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostAllEffect(1, 1, Duration.WhileOnBattlefield, filterWhite, true)));
        // If a spell or ability an opponent controls causes you to discard Wilt-Leaf Liege, put it onto the battlefield instead of putting it into your graveyard.
        this.addAbility(new SimpleStaticAbility(Zone.HAND, new DiscardOntoBattlefieldEffect()));
    }

    public WiltLeafLiege(final WiltLeafLiege card) {
        super(card);
    }

    @Override
    public WiltLeafLiege copy() {
        return new WiltLeafLiege(this);
    }
}
