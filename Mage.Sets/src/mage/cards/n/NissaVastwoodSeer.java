
package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Gender;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.ExileAndReturnTransformedSourceEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterCard;
import mage.filter.common.FilterLandPermanent;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author emerald000
 */
public final class NissaVastwoodSeer extends CardImpl {
    
    private static final FilterCard filter = new FilterCard("basic Forest card");
    static {
        filter.add(SuperType.BASIC.getPredicate());
        filter.add(SubType.FOREST.getPredicate());
    }

    public NissaVastwoodSeer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.SCOUT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        this.secondSideCardClazz = NissaSageAnimist.class;

        // When Nissa, Vastwood Seer enters the battlefield, you may search your library for a basic Forest card, reveal it, put it into your hand, then shuffle your library.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SearchLibraryPutInHandEffect(new TargetCardInLibrary(filter), true, true), true));

        // Whenever a land enters the battlefield under your control, if you control seven or more lands, exile Nissa, then return her to the battlefield transformed under her owner's control.
        this.addAbility(new TransformAbility());
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new EntersBattlefieldControlledTriggeredAbility(new ExileAndReturnTransformedSourceEffect(Gender.FEMALE), new FilterLandPermanent()),
                new PermanentsOnTheBattlefieldCondition(new FilterLandPermanent(), ComparisonType.MORE_THAN, 6, true),
                "Whenever a land enters the battlefield under your control, if you control seven or more lands, exile {this}, then return her to the battlefield transformed under her owner's control."));
    }

    private NissaVastwoodSeer(final NissaVastwoodSeer card) {
        super(card);
    }

    @Override
    public NissaVastwoodSeer copy() {
        return new NissaVastwoodSeer(this);
    }
}
