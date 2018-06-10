
package mage.cards.p;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.IslandwalkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public final class PartWater extends CardImpl {

    public PartWater(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{X}{X}{U}");

        // X target creatures gain islandwalk until end of turn.
        Effect effect = new GainAbilityTargetEffect(new IslandwalkAbility(false), Duration.EndOfTurn);
        effect.setText("X target creatures gain islandwalk until end of turn");
        this.getSpellAbility().getEffects().add(effect);
        this.getSpellAbility().getTargets().add(new TargetCreaturePermanent(1,1));
    }

    @Override
    public void adjustTargets(Ability ability, Game game) {
        if (ability instanceof SpellAbility) {
            ability.getTargets().clear();
            int xValue = ability.getManaCostsToPay().getX();
            FilterCreaturePermanent filter = new FilterCreaturePermanent("creatures gain islandwalk until end of turn");
            ability.getTargets().add(new TargetCreaturePermanent(0, xValue, filter, false));
        }
    }

    public PartWater(final PartWater card) {
        super(card);
    }

    @Override
    public PartWater copy() {
        return new PartWater(this);
    }
}
