package mage.cards.b;

import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.GainProtectionFromColorAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.permanent.token.BorgToken;

import java.util.UUID;

/**
 * @author muz
 */
public final class BorgIncursion extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("artifact creatures you control");

    static {
        filter.add(CardType.ARTIFACT.getPredicate());
    }

    public BorgIncursion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{W}");

        // Create two 1/1 colorless Borg artifact creature tokens, then artifact creatures you control gain protection from the color of your choice until end of turn.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new BorgToken(), 2));
        this.getSpellAbility().addEffect(new GainProtectionFromColorAllEffect(Duration.EndOfTurn, filter)
            .setText("then artifact creatures you control gain protection from the color of your choice until end of turn"));
    }

    private BorgIncursion(final BorgIncursion card) {
        super(card);
    }

    @Override
    public BorgIncursion copy() {
        return new BorgIncursion(this);
    }
}
