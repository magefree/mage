package mage.cards.g;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.search.SearchLibraryGraveyardPutInHandEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.VigilanceAbility;
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
public final class GoldmaneGriffin extends CardImpl {

    private static final FilterCard filter = new FilterCard("Ajani, Inspiring Leader");

    static {
        filter.add(new NamePredicate("Ajani, Inspiring Leader"));
    }

    public GoldmaneGriffin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}{W}");

        this.subtype.add(SubType.GRIFFIN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // When Goldmane Griffin enters the battlefield, you may search your library and/or graveyard for a card named Ajani, Inspiring Leader, reveal it, and put it into your hand. If you search your library this way, shuffle it.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new SearchLibraryGraveyardPutInHandEffect(filter, false, true)
        ));
    }

    private GoldmaneGriffin(final GoldmaneGriffin card) {
        super(card);
    }

    @Override
    public GoldmaneGriffin copy() {
        return new GoldmaneGriffin(this);
    }
}
