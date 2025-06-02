package mage.cards.h;

import mage.abilities.dynamicvalue.common.ArtifactYouControlCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.hint.common.ArtifactYouControlHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.ThopterColorlessToken;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HauntTheNetwork extends CardImpl {

    public HauntTheNetwork(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{U}{B}");

        // Choose target opponent. Create two 1/1 colorless Thopter artifact creature tokens with flying. Then the chosen player loses X life and you gain X life, where X is the number of artifacts you control.
        this.getSpellAbility().addEffect(new InfoEffect("choose target opponent"));
        this.getSpellAbility().addEffect(new CreateTokenEffect(new ThopterColorlessToken(), 2));
        this.getSpellAbility().addEffect(new LoseLifeTargetEffect(ArtifactYouControlCount.instance)
                .setText("Then the chosen player loses X life"));
        this.getSpellAbility().addEffect(new GainLifeEffect(
                ArtifactYouControlCount.instance, "and you gain X life, " +
                "where X is the number of artifacts you control"
        ));
        this.getSpellAbility().addTarget(new TargetOpponent());
        this.getSpellAbility().addHint(ArtifactYouControlHint.instance);
    }

    private HauntTheNetwork(final HauntTheNetwork card) {
        super(card);
    }

    @Override
    public HauntTheNetwork copy() {
        return new HauntTheNetwork(this);
    }
}
