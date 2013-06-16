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
package mage.sets.commander;

import java.util.UUID;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.EvokeAbility;
import mage.abilities.keyword.FearAbility;
import mage.cards.CardImpl;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.target.Target;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public class Shriekmaw extends CardImpl<Shriekmaw> {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("nonartifact, nonblack creature");

    static {
        filter.add(Predicates.not(new CardTypePredicate(CardType.ARTIFACT)));
        filter.add(Predicates.not(new ColorPredicate(ObjectColor.BLACK)));
    }

    public Shriekmaw(UUID ownerId) {
        super(ownerId, 100, "Shriekmaw", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{4}{B}");
        this.expansionSetCode = "CMD";
        this.subtype.add("Elemental");

        this.color.setBlack(true);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Fear
        this.addAbility(FearAbility.getInstance());

        // When Shriekmaw enters the battlefield, destroy target nonartifact, nonblack creature.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DestroyTargetEffect(),false);
        Target target = new TargetCreaturePermanent(filter);
        target.setRequired(true);
        ability.addTarget(target);
        this.addAbility(ability);
        
        // Evoke {1}{B} (You may cast this spell for its evoke cost. If you do, it's sacrificed when it enters the battlefield.)
        this.addAbility(new EvokeAbility(this, "{1}{B}"));
    }

    public Shriekmaw(final Shriekmaw card) {
        super(card);
    }

    @Override
    public Shriekmaw copy() {
        return new Shriekmaw(this);
    }
}
