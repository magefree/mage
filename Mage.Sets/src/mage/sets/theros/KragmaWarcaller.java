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
package mage.sets.theros;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AttacksCreatureYourControlTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continious.BoostTargetEffect;
import mage.abilities.effects.common.continious.GainAbilityAllEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;

/**
 *
 * @author LevelX2
 */
public class KragmaWarcaller extends CardImpl<KragmaWarcaller> {

    private static final FilterControlledCreaturePermanent filter1 = new FilterControlledCreaturePermanent("Minotaur creatures you control");
    private static final FilterControlledCreaturePermanent filter2 = new FilterControlledCreaturePermanent("Minotaur you control");
    static {
        filter1.add(new SubtypePredicate("Minotaur"));
    }

    public KragmaWarcaller(UUID ownerId) {
        super(ownerId, 195, "Kragma Warcaller", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{3}{B}{R}");
        this.expansionSetCode = "THS";
        this.subtype.add("Minotaur");
        this.subtype.add("Warrior");

        this.color.setRed(true);
        this.color.setBlack(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Minotaur creatures you control have haste.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityAllEffect(HasteAbility.getInstance(), Duration.WhileOnBattlefield, filter1, false)));

        // Whenever a Minotaur you control attacks, it gets +2/+0 until end of turn.
        Effect effect = new BoostTargetEffect(2,0, Duration.EndOfTurn);
        effect.setText("it gets +2/+0 until end of turn");
        this.addAbility(new AttacksCreatureYourControlTriggeredAbility(effect, false, filter1, true));
    }

    public KragmaWarcaller(final KragmaWarcaller card) {
        super(card);
    }

    @Override
    public KragmaWarcaller copy() {
        return new KragmaWarcaller(this);
    }
}
