package mage.cards.n;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.LandfallAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.effects.common.ExileAndReturnSourceEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.common.FilterBasicCard;
import mage.filter.common.FilterLandPermanent;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author emerald000
 */
public final class NissaVastwoodSeer extends CardImpl {

    private static final FilterCard filter = new FilterBasicCard(SubType.FOREST);

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(
            new FilterLandPermanent("you control seven or more lands"),
            ComparisonType.MORE_THAN, 6, true
    );

    public NissaVastwoodSeer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.SCOUT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        this.secondSideCardClazz = mage.cards.n.NissaSageAnimist.class;

        // When Nissa, Vastwood Seer enters the battlefield, you may search your library for a basic Forest card, reveal it, put it into your hand, then shuffle your library.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new SearchLibraryPutInHandEffect(new TargetCardInLibrary(filter), true), true
        ));

        // Whenever a land you control enters, if you control seven or more lands, exile Nissa, then return her to the battlefield transformed under her owner's control.
        this.addAbility(new TransformAbility());
        this.addAbility(new LandfallAbility(new ExileAndReturnSourceEffect(
                PutCards.BATTLEFIELD_TRANSFORMED, Pronoun.SHE
        )).withInterveningIf(condition).setAbilityWord(null));
    }

    private NissaVastwoodSeer(final NissaVastwoodSeer card) {
        super(card);
    }

    @Override
    public NissaVastwoodSeer copy() {
        return new NissaVastwoodSeer(this);
    }
}
