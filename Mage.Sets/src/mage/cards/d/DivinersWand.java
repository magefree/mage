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
package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.common.DrawCardControllerTriggeredAbility;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public class DivinersWand extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("a Wizard creature");
    static {
        filter.add(new SubtypePredicate(SubType.WIZARD));
    }

    public DivinersWand(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.TRIBAL,CardType.ARTIFACT},"{3}");
        this.subtype.add("Wizard");
        this.subtype.add("Equipment");

        // Equipped creature has "Whenever you draw a card, this creature gets +1/+1 and gains flying until end of turn" and "{4}: Draw a card."
        Ability gainedAbility = new DrawCardControllerTriggeredAbility(new BoostSourceEffect(1,1, Duration.EndOfTurn), false);
        gainedAbility.addEffect(new GainAbilitySourceEffect(FlyingAbility.getInstance(), Duration.EndOfTurn));
        Effect effect = new GainAbilityAttachedEffect(gainedAbility, AttachmentType.EQUIPMENT);
        effect.setText("Equipped creature has \"Whenever you draw a card, this creature gets +1/+1 and gains flying until end of turn\"");
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, effect);
        effect = new GainAbilityAttachedEffect(
                new SimpleActivatedAbility(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(1), new GenericManaCost(4)), AttachmentType.EQUIPMENT);
        effect.setText("and \"{4}: Draw a card.\"");
        ability.addEffect(effect);
        this.addAbility(ability);

        // Whenever a Wizard creature enters the battlefield, you may attach Diviner's Wand to it.
        this.addAbility(new EntersBattlefieldAllTriggeredAbility(
                Zone.BATTLEFIELD, new AttachEffect(Outcome.Detriment, "attach {source} to it"),
                filter, true, SetTargetPointer.PERMANENT, null));
        // Equip {3}
        this.addAbility(new EquipAbility(Outcome.AddAbility, new GenericManaCost(3)));
    }

    public DivinersWand(final DivinersWand card) {
        super(card);
    }

    @Override
    public DivinersWand copy() {
        return new DivinersWand(this);
    }
}
