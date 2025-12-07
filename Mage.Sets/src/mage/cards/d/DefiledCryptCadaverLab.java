package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.common.CardsLeaveGraveyardTriggeredAbility;
import mage.abilities.common.UnlockThisDoorTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.cards.CardSetInfo;
import mage.cards.RoomCard;
import mage.filter.StaticFilters;
import mage.game.permanent.token.HorrorEnchantmentCreatureToken;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DefiledCryptCadaverLab extends RoomCard {

    public DefiledCryptCadaverLab(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, "{3}{B}", "{B}");

        // Defiled Crypt
        // Whenever one or more cards leave your graveyard, create a 2/2 black Horror enchantment creature token. This ability triggers only once each turn.
        this.getLeftHalfCard().addAbility(new CardsLeaveGraveyardTriggeredAbility(
                new CreateTokenEffect(new HorrorEnchantmentCreatureToken())
        ).setTriggersLimitEachTurn(1));

        // Cadaver Lab
        // When you unlock this door, return target creature card from your graveyard to your hand.
        Ability ability = new UnlockThisDoorTriggeredAbility(new ReturnFromGraveyardToHandTargetEffect(), false, false);
        ability.addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD));
        this.getRightHalfCard().addAbility(ability);
    }

    private DefiledCryptCadaverLab(final DefiledCryptCadaverLab card) {
        super(card);
    }

    @Override
    public DefiledCryptCadaverLab copy() {
        return new DefiledCryptCadaverLab(this);
    }
}
