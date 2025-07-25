package mage.cards.g;

import mage.MageInt;
import mage.abilities.common.AttacksWhileSaddledTriggeredAbility;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.keyword.SaddleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GloryheathLynx extends CardImpl {

    public GloryheathLynx(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.CAT);
        this.subtype.add(SubType.MOUNT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // Whenever this creature attacks while saddled, search your library for a basic Plains card, reveal it, put it into your hand, then shuffle.
        this.addAbility(new AttacksWhileSaddledTriggeredAbility(
                new SearchLibraryPutInHandEffect(new TargetCardInLibrary(StaticFilters.FILTER_CARD_BASIC_PLAINS), true)
        ));

        // Saddle 2
        this.addAbility(new SaddleAbility(2));
    }

    private GloryheathLynx(final GloryheathLynx card) {
        super(card);
    }

    @Override
    public GloryheathLynx copy() {
        return new GloryheathLynx(this);
    }
}
