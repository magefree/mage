package mage.cards.e;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.search.SearchLibraryGraveyardPutInHandEffect;
import mage.abilities.keyword.TrampleAbility;
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
public final class EtherealElk extends CardImpl {

    private static final FilterCard filter = new FilterCard("Vivien, Nature's Avenger");

    static {
        filter.add(new NamePredicate("Vivien, Nature's Avenger"));
    }

    public EtherealElk(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}{G}");

        this.subtype.add(SubType.ELK);
        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // When Ethereal Elk enters the battlefield, you may search your library and/or graveyard for a card named Vivien, Nature's Avenger, reveal it, and put it into your hand. If you search your library this way, shuffle it.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new SearchLibraryGraveyardPutInHandEffect(filter, false, true)
        ));
    }

    private EtherealElk(final EtherealElk card) {
        super(card);
    }

    @Override
    public EtherealElk copy() {
        return new EtherealElk(this);
    }
}
