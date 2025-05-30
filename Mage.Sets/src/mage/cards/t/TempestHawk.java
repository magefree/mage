package mage.cards.t;

import mage.MageInt;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TempestHawk extends CardImpl {

    private static final FilterCard filter = new FilterCard("a card named Tempest Hawk");

    static {
        filter.add(new NamePredicate("Tempest Hawk"));
    }

    public TempestHawk(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.BIRD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever this creature deals combat damage to a player, you may search your library for a card named Tempest Hawk, reveal it, put it into your hand, then shuffle.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(
                new SearchLibraryPutInHandEffect(new TargetCardInLibrary(filter), true), true
        ));

        // A deck can have any number of cards named Tempest Hawk.
        this.addAbility(new SimpleStaticAbility(
                new InfoEffect("a deck can have any number of cards named Tempest Hawk")
        ));
    }

    private TempestHawk(final TempestHawk card) {
        super(card);
    }

    @Override
    public TempestHawk copy() {
        return new TempestHawk(this);
    }
}
