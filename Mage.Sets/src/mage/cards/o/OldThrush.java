package mage.cards.o;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInLibrary;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.decorator.OptionalOneShotEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.search.SearchLibraryPutOnLibraryEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class OldThrush extends CardImpl {

    public OldThrush(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}");

        this.subtype.add(SubType.BIRD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When this creature enters, you gain 2 life. You may search your library for a basic land card, reveal it, then shuffle and put that card on top.
        Ability ability = new EntersBattlefieldTriggeredAbility(new GainLifeEffect(2));
        ability.addEffect(new OptionalOneShotEffect(new SearchLibraryPutOnLibraryEffect(
            new TargetCardInLibrary(StaticFilters.FILTER_CARD_BASIC_LAND_A), true
        )));
        this.addAbility(ability);
    }

    private OldThrush(final OldThrush card) {
        super(card);
    }

    @Override
    public OldThrush copy() {
        return new OldThrush(this);
    }
}
