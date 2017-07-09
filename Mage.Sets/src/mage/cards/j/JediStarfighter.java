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
package mage.cards.j;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.keyword.SpaceflightAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.MeditateAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Styxo
 */
public class JediStarfighter extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Jedi creatures you control");

    static {
        filter.add(new SubtypePredicate(SubType.JEDI));
               filter.add(new ControllerPredicate(TargetController.YOU));
    }

    public JediStarfighter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{2}{W}{W}");
        this.subtype.add("Jedi");
        this.subtype.add("Starship");
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Spaceflight
        this.addAbility(SpaceflightAbility.getInstance());

        // When Jedi Starfighter enters the battlefield, up to two Jedi creatures you control gain spaceflight until end of turn.
        EntersBattlefieldTriggeredAbility ability = new EntersBattlefieldTriggeredAbility(new GainAbilityTargetEffect(SpaceflightAbility.getInstance(), Duration.EndOfTurn));
        ability.addTarget(new TargetCreaturePermanent(0, 2, filter, true));
        this.addAbility(ability);

        // Meditate {1}{W}
        this.addAbility(new MeditateAbility(new ManaCostsImpl("{1}{W}")));
    }

    public JediStarfighter(final JediStarfighter card) {
        super(card);
    }

    @Override
    public JediStarfighter copy() {
        return new JediStarfighter(this);
    }
}
