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
package mage.sets.fatereforged;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksAllTriggeredAbility;
import mage.abilities.effects.common.DontUntapInControllersNextUntapStepTargetEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.SetTargetPointer;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.common.FilterNonlandPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.target.TargetPermanent;

/**
 *
 * @author LevelX2
 */
public class OjutaiSoulOfWinter extends CardImpl {

    private static final FilterCreaturePermanent filterDragon = new FilterCreaturePermanent("Dragon you control");
    private static final FilterPermanent filterNonlandPermanent = new FilterNonlandPermanent("nonland permanent an opponent controls");

    static {
        filterDragon.add(new SubtypePredicate("Dragon"));
        filterDragon.add(new ControllerPredicate(TargetController.YOU));
        filterNonlandPermanent.add(new ControllerPredicate(TargetController.OPPONENT));
    }

    public OjutaiSoulOfWinter(UUID ownerId) {
        super(ownerId, 156, "Ojutai, Soul of Winter", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{5}{W}{U}");
        this.expansionSetCode = "FRF";
        this.supertype.add("Legendary");
        this.subtype.add("Dragon");
        this.power = new MageInt(5);
        this.toughness = new MageInt(6);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());
        // Whenever a Dragon you control attacks, tap target nonland permanent an opponent controls. That permanent doesn't untap during its controller's next untap step.
        Ability ability = new AttacksAllTriggeredAbility(
                new TapTargetEffect(),
                false, filterDragon, SetTargetPointer.NONE, false);
        ability.addEffect(new DontUntapInControllersNextUntapStepTargetEffect("That permanent"));
        ability.addTarget(new TargetPermanent(filterNonlandPermanent));
        this.addAbility(ability);
    }

    public OjutaiSoulOfWinter(final OjutaiSoulOfWinter card) {
        super(card);
    }

    @Override
    public OjutaiSoulOfWinter copy() {
        return new OjutaiSoulOfWinter(this);
    }
}
