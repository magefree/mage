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
package mage.sets.thedark;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.PutPermanentOnBattlefieldEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterPermanentCard;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.target.Target;
import mage.target.TargetPermanent;

/**
 *
 * @author BursegSardaukar
 */
public class GoblinWizard extends CardImpl {

    private static final FilterPermanentCard filter = new FilterPermanentCard("Goblin");
    private static final FilterCard protectionFilter = new FilterCard("white");
    private static final FilterPermanent goblinPermanent = new FilterPermanent("Goblin");

    static {
        filter.add(new SubtypePredicate("Goblin"));
        goblinPermanent.add(new SubtypePredicate("Goblin"));
        protectionFilter.add(new ColorPredicate(ObjectColor.WHITE));
    }

    public GoblinWizard(UUID ownerId) {
        super(ownerId, 68, "Goblin Wizard", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{2}{R}{R}");
        this.expansionSetCode = "DRK";
        this.rarity = Rarity.RARE;

        this.subtype.add("Goblin");
        this.subtype.add("Wizard");

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {tap}: You may put a Goblin permanent card from your hand onto the battlefield.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new PutPermanentOnBattlefieldEffect(filter),
                new TapSourceCost()));

        // {R}: Target Goblin gains protection from white until end of turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new GainAbilityTargetEffect(new ProtectionAbility(protectionFilter), Duration.EndOfTurn), new ManaCostsImpl("{R}"));
        Target target = new TargetPermanent(goblinPermanent);
        ability.addTarget(target);
        this.addAbility(ability);

    }

    public GoblinWizard(final GoblinWizard card) {
        super(card);
    }

    @Override
    public GoblinWizard copy() {
        return new GoblinWizard(this);
    }
}
