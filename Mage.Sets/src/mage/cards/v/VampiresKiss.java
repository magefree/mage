package mage.cards.v;

import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.BloodToken;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class VampiresKiss extends CardImpl {

    public VampiresKiss(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{B}");

        // Target player loses 2 life and you gain 2 life. Create two Blood tokens.
        this.getSpellAbility().addEffect(new LoseLifeTargetEffect(2));
        this.getSpellAbility().addEffect(new GainLifeEffect(2).concatBy("and"));
        this.getSpellAbility().addEffect(new CreateTokenEffect(new BloodToken(), 2));
        this.getSpellAbility().addTarget(new TargetPlayer());
    }

    private VampiresKiss(final VampiresKiss card) {
        super(card);
    }

    @Override
    public VampiresKiss copy() {
        return new VampiresKiss(this);
    }
}
