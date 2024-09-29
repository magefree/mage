package mage.cards.p;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.MillThenPutInHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.TreasureToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PatientNaturalist extends CardImpl {

    public PatientNaturalist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SCOUT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // When Patient Naturalist enters the battlefield, mill three cards. Put a land card from among the milled cards into your hand. If you can't, create a Treasure token.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new MillThenPutInHandEffect(
                3, StaticFilters.FILTER_CARD_LAND_A,
                new CreateTokenEffect(new TreasureToken()), false
        )));
    }

    private PatientNaturalist(final PatientNaturalist card) {
        super(card);
    }

    @Override
    public PatientNaturalist copy() {
        return new PatientNaturalist(this);
    }
}
