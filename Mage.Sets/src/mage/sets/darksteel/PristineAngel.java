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
package mage.sets.darksteel;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.condition.InvertCondition;
import mage.abilities.condition.common.SourceTappedCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.UntapSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.mageobject.ColorPredicate;


/**
 *
 * @author LevelX2
 */
public class PristineAngel extends CardImpl {

    private static final FilterCard filter = new FilterCard("artifacts and all colors");

    static {
        filter.add(Predicates.or(
                new CardTypePredicate(CardType.ARTIFACT),
                new ColorPredicate(ObjectColor.BLACK),
                new ColorPredicate(ObjectColor.BLUE),
                new ColorPredicate(ObjectColor.GREEN),
                new ColorPredicate(ObjectColor.RED),
                new ColorPredicate(ObjectColor.WHITE)));
    }

    public PristineAngel(UUID ownerId) {
        super(ownerId, 9, "Pristine Angel", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{4}{W}{W}");
        this.expansionSetCode = "DST";
        this.subtype.add("Angel");

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // As long as Pristine Angel is untapped, it has protection from artifacts and from all colors.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD,
                new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(new ProtectionAbility(filter), Duration.WhileOnBattlefield),
                new InvertCondition(new SourceTappedCondition()),
                "As long as {this} is untapped, it has protection from artifacts and from all colors")));
        // Whenever you cast a spell, you may untap Pristine Angel.
        this.addAbility(new SpellCastControllerTriggeredAbility(new UntapSourceEffect(), true));
    }

    public PristineAngel(final PristineAngel card) {
        super(card);
    }

    @Override
    public PristineAngel copy() {
        return new PristineAngel(this);
    }
}
