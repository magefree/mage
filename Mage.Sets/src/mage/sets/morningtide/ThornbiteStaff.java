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
package mage.sets.morningtide;

import java.util.UUID;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.abilities.Ability;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.UntapSourceEffect;
import mage.abilities.effects.common.continious.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.target.common.TargetCreatureOrPlayer;

/**
 *
 * @author LevelX2
 */
public class ThornbiteStaff extends CardImpl<ThornbiteStaff> {

    private static final FilterPermanent filter = new FilterCreaturePermanent("a Shaman creature");
    static {
        filter.add(new SubtypePredicate("Shaman"));
    }

    public ThornbiteStaff(UUID ownerId) {
        super(ownerId, 145, "Thornbite Staff", Rarity.UNCOMMON, new CardType[]{CardType.ARTIFACT}, "{2}");
        this.expansionSetCode = "MOR";
        this.supertype.add("Tribal");
        this.subtype.add("Shaman");
        this.subtype.add("Equipment");

        // Equipped creature has "{2}, {T}: This creature deals 1 damage to target creature or player" and "Whenever a creature dies, untap this creature."
        Ability gainedAbility = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DamageTargetEffect(1), new GenericManaCost(2));
        gainedAbility.addCost(new TapSourceCost());
        gainedAbility.addTarget(new TargetCreatureOrPlayer());
        Effect effect = new GainAbilityAttachedEffect(gainedAbility, AttachmentType.EQUIPMENT);
        effect.setText("Equipped creature has \"{2}, {T}: This creature deals 1 damage to target creature or player\"");
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, effect);
        effect = new GainAbilityAttachedEffect(new DiesCreatureTriggeredAbility(new UntapSourceEffect(),false), AttachmentType.EQUIPMENT);
        effect.setText("and \"Whenever a creature dies, untap this creature.\"");
        ability.addEffect(effect);
        this.addAbility(ability);
        // Whenever a Shaman creature enters the battlefield, you may attach Thornbite Staff to it.
        this.addAbility(new EntersBattlefieldAllTriggeredAbility(
                Zone.BATTLEFIELD, new AttachEffect(Outcome.Detriment, "attach {source} to it"),
                filter, true, true, null));
        // Equip {4}
        this.addAbility(new EquipAbility(Outcome.AddAbility, new GenericManaCost(4)));
    }

    public ThornbiteStaff(final ThornbiteStaff card) {
        super(card);
    }

    @Override
    public ThornbiteStaff copy() {
        return new ThornbiteStaff(this);
    }
}
