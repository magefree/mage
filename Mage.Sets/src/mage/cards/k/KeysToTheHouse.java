package mage.cards.k;

import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.LockOrUnlockRoomTargetEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KeysToTheHouse extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.ROOM);

    public KeysToTheHouse(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}");

        // {1}, {T}, Sacrifice Keys to the House: Search your library for a basic land card, reveal it, put it into your hand, then shuffle.
        Ability ability = new SimpleActivatedAbility(new SearchLibraryPutInHandEffect(
                new TargetCardInLibrary(StaticFilters.FILTER_CARD_BASIC_LAND), true
        ), new GenericManaCost(1));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);

        // {3}, {T}, Sacrifice Keys to the House: Lock or unlock a door of target Room you control. Activate only as a sorcery.
        ability = new ActivateAsSorceryActivatedAbility(new LockOrUnlockRoomTargetEffect(), new GenericManaCost(3));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private KeysToTheHouse(final KeysToTheHouse card) {
        super(card);
    }

    @Override
    public KeysToTheHouse copy() {
        return new KeysToTheHouse(this);
    }
}
