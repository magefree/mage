package mage.cards.c;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.PutCards;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CaseyJonesJuryRigJusticiar extends CardImpl {

    public CaseyJonesJuryRigJusticiar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.BERSERKER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // When Casey Jones enters, look at the top four cards of your library. You may reveal an artifact card from among them and put it into your hand. Put the rest on the bottom of your library in a random order.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new LookLibraryAndPickControllerEffect(
                4, 1, StaticFilters.FILTER_CARD_ARTIFACT_AN, PutCards.HAND, PutCards.BOTTOM_RANDOM
        )));
    }

    private CaseyJonesJuryRigJusticiar(final CaseyJonesJuryRigJusticiar card) {
        super(card);
    }

    @Override
    public CaseyJonesJuryRigJusticiar copy() {
        return new CaseyJonesJuryRigJusticiar(this);
    }
}
