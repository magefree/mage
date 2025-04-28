package mage.cards.r;

import mage.MageInt;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.common.EntersBattlefieldOrAttacksSourceTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.game.permanent.token.Wall13Token;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RampartArchitect extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledCreaturePermanent("a creature you control with defender");

    static {
        filter.add(new AbilityPredicate(DefenderAbility.class));
    }

    public RampartArchitect(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");

        this.subtype.add(SubType.ELEPHANT);
        this.subtype.add(SubType.ADVISOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Whenever this creature enters or attacks, create a 1/3 white Wall creature token with defender.
        this.addAbility(new EntersBattlefieldOrAttacksSourceTriggeredAbility(new CreateTokenEffect(new Wall13Token())));

        // Whenever a creature you control with defender dies, you may search your library for a basic land card, put that card onto the battlefield tapped, then shuffle.
        this.addAbility(new DiesCreatureTriggeredAbility(new SearchLibraryPutInPlayEffect(
                new TargetCardInLibrary(StaticFilters.FILTER_CARD_BASIC_LAND), true, true
        ), true, filter));
    }

    private RampartArchitect(final RampartArchitect card) {
        super(card);
    }

    @Override
    public RampartArchitect copy() {
        return new RampartArchitect(this);
    }
}
