package mage.cards.h;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.CardsLeaveGraveyardTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.IllusionArtifactToken;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author muz
 */
public final class HologramStalker extends CardImpl {

    public HologramStalker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}{G}");

        this.subtype.add(SubType.HIROGEN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When this creature enters, return target permanent card from your graveyard to your hand.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ReturnFromGraveyardToHandTargetEffect());
        ability.addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_PERMANENT));
        this.addAbility(ability);

        // Whenever a card leaves your graveyard, create a 3/3 colorless Illusion artifact creature token. This ability triggers only once each turn.
        this.addAbility(new CardsLeaveGraveyardTriggeredAbility(new CreateTokenEffect(new IllusionArtifactToken()))
            .setTriggerPhrase("Whenever a card leaves your graveyard, ").setTriggersLimitEachTurn(1));
    }

    private HologramStalker(final HologramStalker card) {
        super(card);
    }

    @Override
    public HologramStalker copy() {
        return new HologramStalker(this);
    }
}
