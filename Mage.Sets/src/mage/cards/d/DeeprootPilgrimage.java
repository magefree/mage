package mage.cards.d;

import mage.abilities.common.BecomesTappedOneOrMoreTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.permanent.token.MerfolkHexproofToken;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class DeeprootPilgrimage extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.MERFOLK, "nontoken Merfolk you control");

    static {
        filter.add(TokenPredicate.FALSE);
    }

    public DeeprootPilgrimage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{U}");

        // Whenever one or more nontoken Merfolk you control become tapped, create a 1/1 blue Merfolk creature token with hexproof.
        this.addAbility(new BecomesTappedOneOrMoreTriggeredAbility(
                Zone.BATTLEFIELD, new CreateTokenEffect(new MerfolkHexproofToken()), false, filter
        ));
    }

    private DeeprootPilgrimage(final DeeprootPilgrimage card) {
        super(card);
    }

    @Override
    public DeeprootPilgrimage copy() {
        return new DeeprootPilgrimage(this);
    }
}

