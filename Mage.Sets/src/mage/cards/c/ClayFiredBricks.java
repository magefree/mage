package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.abilities.keyword.CraftAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.common.FilterBasicLandCard;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ClayFiredBricks extends CardImpl {

    private static final FilterCard filter = new FilterBasicLandCard(SubType.PLAINS);

    public ClayFiredBricks(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}{W}");
        this.secondSideCardClazz = mage.cards.c.CosmiumKiln.class;

        // When Clay-Fired Bricks enters the battlefield, search your library for a basic Plains card, reveal it, put it into your hand, then shuffle. You gain 2 life.
        Ability ability = new EntersBattlefieldTriggeredAbility(
                new SearchLibraryPutInHandEffect(new TargetCardInLibrary(filter), true)
        );
        ability.addEffect(new GainLifeEffect(2));
        this.addAbility(ability);

        // Craft with artifact {5}{W}{W}
        this.addAbility(new CraftAbility("{5}{W}{W}"));
    }

    private ClayFiredBricks(final ClayFiredBricks card) {
        super(card);
    }

    @Override
    public ClayFiredBricks copy() {
        return new ClayFiredBricks(this);
    }
}
