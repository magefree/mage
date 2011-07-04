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
import mage.Constants.Duration;
import mage.Constants.Rarity;
import mage.Constants.TargetController;
import mage.MageInt;
import mage.abilities.common.AllyEntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.continious.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.filter.common.FilterCreaturePermanent;

/**
 *
 * @author North
 */
public class AkoumBattlesinger extends CardImpl<AkoumBattlesinger> {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Ally creatures you control");

    static {
        filter.getSubtype().add("Ally");
        filter.setTargetController(TargetController.YOU);
    }

    public AkoumBattlesinger(UUID ownerId) {
        super(ownerId, 71, "Akoum Battlesinger", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{1}{R}");
        this.expansionSetCode = "WWK";
        this.subtype.add("Human");
        this.subtype.add("Berserker");
        this.subtype.add("Ally");

        this.color.setRed(true);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        this.addAbility(new AllyEntersBattlefieldTriggeredAbility(new BoostControlledEffect(1, 0, Duration.EndOfTurn, filter, false), true));
    }

    public AkoumBattlesinger(final AkoumBattlesinger card) {
        super(card);
    }

    @Override
    public AkoumBattlesinger copy() {
        return new AkoumBattlesinger(this);
    }
}
