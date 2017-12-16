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
package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BecomesSubtypeAllEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.keyword.SquirrellinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.util.SubTypeList;

/**
 *
 * @author spjspj
 */
public class EarlOfSquirrel extends CardImpl {

    private final static FilterCreaturePermanent filter = new FilterCreaturePermanent("Creature tokens you control");
    private static final FilterCreaturePermanent filter2 = new FilterCreaturePermanent("Other squirrels you control");


    static {
        filter.add(new TokenPredicate());
        filter.add(new ControllerPredicate(TargetController.YOU));
        filter2.add(new SubtypePredicate(SubType.SQUIRREL));
    }

    public EarlOfSquirrel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}{G}");

        this.subtype.add(SubType.SQUIRREL);
        this.subtype.add(SubType.ADVISOR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Squirrellink (Damage dealt by this creature also causes you to create that many 1/1 green Squirrel creature tokens.)
        this.addAbility(SquirrellinkAbility.getInstance());

        // Creature tokens you control are Squirrels in addition to their other creature types.
        SubTypeList subTypes = new SubTypeList();
        subTypes.add(SubType.SQUIRREL);
        Effect effect = new BecomesSubtypeAllEffect(Duration.WhileOnBattlefield, subTypes, filter, false);
        effect.setText("Creature tokens you control are Squirrels in addition to their other creature types");
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect));

        // Other Squirrels you control get +1/+1.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostControlledEffect(1, 1, Duration.WhileOnBattlefield, filter2, true)));
    }

    public EarlOfSquirrel(final EarlOfSquirrel card) {
        super(card);
    }

    @Override
    public EarlOfSquirrel copy() {
        return new EarlOfSquirrel(this);
    }
}
