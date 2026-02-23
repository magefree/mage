package mage.cards.c;

import mage.MageInt;
import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.BeholdAndExileCost;
import mage.abilities.effects.common.ReturnExiledCardToHandEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ChampionOfTheClachan extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent(SubType.KITHKIN, "Kithkin");

    public ChampionOfTheClachan(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");

        this.subtype.add(SubType.KITHKIN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // As an additional cost to cast this spell, behold a Kithkin and exile it.
        this.getSpellAbility().addCost(new BeholdAndExileCost(SubType.KITHKIN));

        // Other Kithkin you control get +1/+1.
        this.addAbility(new SimpleStaticAbility(new BoostControlledEffect(
                1, 1, Duration.WhileOnBattlefield, filter, true
        )));

        // When this creature leaves the battlefield, return the exiled card to its owner's hand.
        this.addAbility(new LeavesBattlefieldTriggeredAbility(new ReturnExiledCardToHandEffect()));
    }

    private ChampionOfTheClachan(final ChampionOfTheClachan card) {
        super(card);
    }

    @Override
    public ChampionOfTheClachan copy() {
        return new ChampionOfTheClachan(this);
    }
}
