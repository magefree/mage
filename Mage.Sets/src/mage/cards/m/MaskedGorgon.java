
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.CardsInControllerGraveyardCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;

/**
 *
 * @author LoneFox
 */
public final class MaskedGorgon extends CardImpl {

    private static final FilterCreaturePermanent filter1 = new FilterCreaturePermanent("green creatures and white creatures");
    private static final FilterCard filter2 = new FilterCard("Gorgons");

    static {
        filter1.add(Predicates.or(new ColorPredicate(ObjectColor.GREEN), new ColorPredicate(ObjectColor.WHITE)));
        filter2.add(SubType.GORGON.getPredicate());
    }

    public MaskedGorgon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{B}");
        this.subtype.add(SubType.GORGON);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Green creatures and white creatures have protection from Gorgons.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityAllEffect(
            new ProtectionAbility(filter2), Duration.WhileOnBattlefield, filter1)));
        // Threshold - Masked Gorgon has protection from green and from white as long as seven or more cards are in your graveyard.
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new ConditionalContinuousEffect(
            new GainAbilitySourceEffect(ProtectionAbility.from(ObjectColor.GREEN, ObjectColor.WHITE), Duration.WhileOnBattlefield), new CardsInControllerGraveyardCondition(7),
            "{this} has protection from green and from white as long as seven or more cards are in your graveyard"));
        ability.setAbilityWord(AbilityWord.THRESHOLD);
        this.addAbility(ability);
    }

    public MaskedGorgon(final MaskedGorgon card) {
        super(card);
    }

    @Override
    public MaskedGorgon copy() {
        return new MaskedGorgon(this);
    }
}
