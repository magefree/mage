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
package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.combat.CantBeBlockedByTargetSourceEffect;
import mage.abilities.effects.common.combat.MustBeBlockedByTargetSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author Galatolol
 */
public class AuriokSiegeSled extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("artifact creature");

    static {
        filter.add(new CardTypePredicate(CardType.ARTIFACT));
    }

    public AuriokSiegeSled(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{6}");
        this.subtype.add("Juggernaut");
        this.power = new MageInt(3);
        this.toughness = new MageInt(5);

        // {1}: Target artifact creature blocks Auriok Siege Sled this turn if able.
        MustBeBlockedByTargetSourceEffect effect = new MustBeBlockedByTargetSourceEffect(Duration.EndOfTurn);
        effect.setText("Target artifact creature blocks {this} this turn if able.");
        Ability ability1 = new SimpleActivatedAbility(Zone.BATTLEFIELD, effect, new ManaCostsImpl("{1}"));
        ability1.addTarget(new TargetCreaturePermanent(filter));
        this.addAbility(ability1);

        // {1}: Target artifact creature can't block Auriok Siege Sled this turn.
        Ability ability2 = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CantBeBlockedByTargetSourceEffect(Duration.EndOfTurn),
                new ManaCostsImpl("{1}"));
        ability2.addTarget(new TargetCreaturePermanent(filter));
        this.addAbility(ability2);
    }

    public AuriokSiegeSled(final AuriokSiegeSled card) {
        super(card);
    }

    @Override
    public AuriokSiegeSled copy() {
        return new AuriokSiegeSled(this);
    }
}
