
package mage.cards.r;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.Game;
import mage.game.permanent.token.GremlinToken;
import mage.target.common.TargetArtifactPermanent;

/**
 *
 * @author Styxo
 */
public final class ReleaseTheGremlins extends CardImpl {

    public ReleaseTheGremlins(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{X}{R}");

        // Destroy X target artifacts. 
        this.getSpellAbility().addEffect(new DestroyTargetEffect("Destroy X target artifacts"));
        this.getSpellAbility().addTarget(new TargetArtifactPermanent());

        // Create X 2/2 red Gremlin creature tokens.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new GremlinToken(), new ManacostVariableValue()));

    }

    @Override
    public void adjustTargets(Ability ability, Game game) {
        if (ability instanceof SpellAbility) {
            ability.getTargets().clear();
            int xValue = ability.getManaCostsToPay().getX();
            ability.addTarget(new TargetArtifactPermanent(xValue, xValue));
        }
    }

    public ReleaseTheGremlins(final ReleaseTheGremlins card) {
        super(card);
    }

    @Override
    public ReleaseTheGremlins copy() {
        return new ReleaseTheGremlins(this);
    }
}
