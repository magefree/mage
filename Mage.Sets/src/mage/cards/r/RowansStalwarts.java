package mage.cards.r;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.search.SearchLibraryGraveyardPutInHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.NamePredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RowansStalwarts extends CardImpl {

    private static final FilterCard filter = new FilterCard("Rowan, Fearless Sparkmage");

    static {
        filter.add(new NamePredicate("Rowan, Fearless Sparkmage"));
    }

    public RowansStalwarts(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(5);
        this.toughness = new MageInt(2);

        // When Rowan's Stalwarts enters the battlefield, you may search your library and/or graveyard for a card named Rowan, Fearless Sparkmage, reveal it, and put it into your hand. If you search your library this way, shuffle it.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new SearchLibraryGraveyardPutInHandEffect(filter, false, true)
        ));
    }

    private RowansStalwarts(final RowansStalwarts card) {
        super(card);
    }

    @Override
    public RowansStalwarts copy() {
        return new RowansStalwarts(this);
    }
}
