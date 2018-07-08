
package mage.cards.r;

import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 *
 * @author emerald000
 */
public final class ReturnToDust extends CardImpl {

    public ReturnToDust(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{W}{W}");


        // Exile target artifact or enchantment. If you cast this spell during your main phase, you may exile up to one other target artifact or enchantment.
        Effect effect = new ExileTargetEffect();
        effect.setText("Exile target artifact or enchantment. If you cast this spell during your main phase, you may exile up to one other target artifact or enchantment");
        this.getSpellAbility().addEffect(effect);
    }

    public ReturnToDust(final ReturnToDust card) {
        super(card);
    }

    @Override
    public void adjustTargets(Ability ability, Game game) {
        if (ability instanceof SpellAbility) {
            if (game.isActivePlayer(ability.getControllerId()) && game.isMainPhase()) {
                ability.addTarget(new TargetPermanent(1, 2, StaticFilters.FILTER_PERMANENT_ARTIFACT_OR_ENCHANTMENT, false));
            }
            else {
                ability.addTarget(new TargetPermanent(StaticFilters.FILTER_PERMANENT_ARTIFACT_OR_ENCHANTMENT));
            }
        }
    }

    @Override
    public ReturnToDust copy() {
        return new ReturnToDust(this);
    }
}
