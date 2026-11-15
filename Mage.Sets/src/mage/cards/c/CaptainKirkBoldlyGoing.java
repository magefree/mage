package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterCard;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.abilities.common.EntersBattlefieldOrAttacksSourceTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.search.SearchLibraryGraveyardPutInHandEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author muz
 */
public final class CaptainKirkBoldlyGoing extends CardImpl {

    private static final FilterCard filter = new FilterCard("a basic land card or a card named Starship Enterprise");
    private static final FilterControlledCreaturePermanent filter2 = new FilterControlledCreaturePermanent(SubType.SPACECRAFT, "Spacecraft creatures you control");

    static {
        filter.add(Predicates.or(
            Predicates.and(
                CardType.LAND.getPredicate(),
                SuperType.BASIC.getPredicate()
            ),
            new NamePredicate("Starship Enterprise")
        ));
    }

    public CaptainKirkBoldlyGoing(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}{W}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.OFFICER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Spacecraft creatures you control get +3/+3.
        this.addAbility(new SimpleStaticAbility(new BoostControlledEffect(3, 3, Duration.WhileOnBattlefield, filter2)));

        // Whenever Captain Kirk enters or attacks, search your library and/or graveyard for a basic land card or a card named Starship Enterprise, reveal it, and put it into your hand. If you search your library this way, shuffle.
        this.addAbility(new EntersBattlefieldOrAttacksSourceTriggeredAbility(new SearchLibraryGraveyardPutInHandEffect(filter)));
    }

    private CaptainKirkBoldlyGoing(final CaptainKirkBoldlyGoing card) {
        super(card);
    }

    @Override
    public CaptainKirkBoldlyGoing copy() {
        return new CaptainKirkBoldlyGoing(this);
    }
}
