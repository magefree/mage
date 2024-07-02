
package mage.cards.r;

import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.GremlinToken;
import mage.target.common.TargetArtifactPermanent;
import mage.target.targetadjustment.XTargetsCountAdjuster;

import java.util.UUID;

/**
 * @author Styxo
 */
public final class ReleaseTheGremlins extends CardImpl {

    public ReleaseTheGremlins(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{X}{R}");

        // Destroy X target artifacts. 
        this.getSpellAbility().addEffect(new DestroyTargetEffect("Destroy X target artifacts"));
        this.getSpellAbility().addTarget(new TargetArtifactPermanent());
        this.getSpellAbility().setTargetAdjuster(new XTargetsCountAdjuster());

        // Create X 2/2 red Gremlin creature tokens.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new GremlinToken(), ManacostVariableValue.REGULAR));

    }

    private ReleaseTheGremlins(final ReleaseTheGremlins card) {
        super(card);
    }

    @Override
    public ReleaseTheGremlins copy() {
        return new ReleaseTheGremlins(this);
    }
}
