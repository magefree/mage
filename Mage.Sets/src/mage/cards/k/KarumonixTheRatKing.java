package mage.cards.k;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.constants.*;
import mage.abilities.keyword.ToxicAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;

/**
 * @author TheElk801
 */
public final class KarumonixTheRatKing extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent(SubType.RAT, "Rats");
    private static final FilterCard filter2 = new FilterCard("Rat cards");

    static {
        filter.add(SubType.RAT.getPredicate());
        filter2.add(SubType.RAT.getPredicate());
    }

    public KarumonixTheRatKing(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}{B}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.RAT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Toxic 1
        this.addAbility(new ToxicAbility(1));

        // Other Rats you control have toxic 1.
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
                new ToxicAbility(1), Duration.WhileOnBattlefield, filter, true
        )));

        // When Karumonix enters the battlefield, look at the top five cards of your library. You may reveal any number of Rat cards from among them and put the revealed cards into your hand. Put the rest on the bottom of your library in a random order.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new LookLibraryAndPickControllerEffect(
                5, Integer.MAX_VALUE, filter2, PutCards.HAND, PutCards.BOTTOM_RANDOM
        )));
    }

    private KarumonixTheRatKing(final KarumonixTheRatKing card) {
        super(card);
    }

    @Override
    public KarumonixTheRatKing copy() {
        return new KarumonixTheRatKing(this);
    }
}
