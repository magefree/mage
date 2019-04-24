
package mage.cards.b;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.Game;
import mage.target.common.TargetArtifactPermanent;

/**
 *
 * @author spjspj
 */
public final class ByForce extends CardImpl {

    public ByForce(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{R}");

        // Destroy X target artifacts.
        this.getSpellAbility().addEffect(new DestroyTargetEffect("Destroy X target artifacts"));
        this.getSpellAbility().addTarget(new TargetArtifactPermanent());
    }

    @Override
    public void adjustTargets(Ability ability, Game game) {
        if (ability instanceof SpellAbility) {
            ability.getTargets().clear();
            int xValue = ability.getManaCostsToPay().getX();
            ability.addTarget(new TargetArtifactPermanent(xValue, xValue));
        }
    }

    public ByForce(final ByForce card) {
        super(card);
    }

    @Override
    public ByForce copy() {
        return new ByForce(this);
    }
}
