package mage.cards.d;

import mage.abilities.Mode;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.HexproofAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.PilotSaddleCrewToken;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DefendTheRider extends CardImpl {

    public DefendTheRider(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{G}");

        // Choose one --
        // * Target permanent you control gains hexproof and indestructible until end of turn.
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(HexproofAbility.getInstance())
                .setText("target permanent you control gains hexproof"));
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(IndestructibleAbility.getInstance())
                .setText("and indestructible until end of turn"));
        this.getSpellAbility().addTarget(new TargetPermanent());

        // * Create a 1/1 colorless Pilot creature token with "This token saddles Mounts and crews Vehicles as though its power were 2 greater."
        this.getSpellAbility().addMode(new Mode(new CreateTokenEffect(new PilotSaddleCrewToken())));
    }

    private DefendTheRider(final DefendTheRider card) {
        super(card);
    }

    @Override
    public DefendTheRider copy() {
        return new DefendTheRider(this);
    }
}
