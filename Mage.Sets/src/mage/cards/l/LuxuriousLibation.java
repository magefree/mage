package mage.cards.l;

import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.game.permanent.token.CitizenGreenWhiteToken;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LuxuriousLibation extends CardImpl {

    public LuxuriousLibation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{X}{G}");

        // Target creature gets +X/+X until end of turn. Create a 1/1 green and white Citizen creature token.
        this.getSpellAbility().addEffect(new BoostTargetEffect(
                ManacostVariableValue.REGULAR, ManacostVariableValue.REGULAR, Duration.EndOfTurn
        ));
        this.getSpellAbility().addEffect(new CreateTokenEffect(new CitizenGreenWhiteToken()));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private LuxuriousLibation(final LuxuriousLibation card) {
        super(card);
    }

    @Override
    public LuxuriousLibation copy() {
        return new LuxuriousLibation(this);
    }
}
