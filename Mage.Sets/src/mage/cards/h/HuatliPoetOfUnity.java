package mage.cards.h;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ExileAndReturnSourceEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.PutCards;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class HuatliPoetOfUnity extends CardImpl {

    public HuatliPoetOfUnity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.subtype.add(SubType.BARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);
        this.secondSideCardClazz = mage.cards.r.RoarOfTheFifthPeople.class;

        // When Huatli, Poet of Unity enters the battlefield, search your library for a basic land card, reveal it, put it into your hand, then shuffle.
        TargetCardInLibrary target = new TargetCardInLibrary(StaticFilters.FILTER_CARD_BASIC_LAND);
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SearchLibraryPutInHandEffect(target, true), false));

        // {3}{R/W}{R/W}: Exile Huatli, then return her to the battlefield transformed under her owner's control. Activate only as a sorcery.
        this.addAbility(new TransformAbility());
        Ability ability = new ActivateAsSorceryActivatedAbility(
                new ExileAndReturnSourceEffect(PutCards.BATTLEFIELD_TRANSFORMED),
                new ManaCostsImpl<>("{3}{R/W}{R/W}")
        );
        this.addAbility(ability);
    }

    private HuatliPoetOfUnity(final HuatliPoetOfUnity card) {
        super(card);
    }

    @Override
    public HuatliPoetOfUnity copy() {
        return new HuatliPoetOfUnity(this);
    }
}
