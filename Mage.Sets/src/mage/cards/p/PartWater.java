
package mage.cards.p;

import mage.abilities.Ability;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.IslandwalkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.game.Game;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetadjustment.TargetAdjuster;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class PartWater extends CardImpl {

    public PartWater(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{X}{U}");

        // X target creatures gain islandwalk until end of turn.
        Effect effect = new GainAbilityTargetEffect(new IslandwalkAbility(false), Duration.EndOfTurn);
        effect.setText("X target creatures gain islandwalk until end of turn");
        this.getSpellAbility().getEffects().add(effect);
        this.getSpellAbility().getTargets().add(new TargetCreaturePermanent());
        this.getSpellAbility().setTargetAdjuster(PartWaterAdjuster.instance);
    }

    private PartWater(final PartWater card) {
        super(card);
    }

    @Override
    public PartWater copy() {
        return new PartWater(this);
    }
}

enum PartWaterAdjuster implements TargetAdjuster {
    instance;

    @Override
    public void adjustTargets(Ability ability, Game game) {
        ability.getTargets().clear();
        ability.getTargets().add(new TargetCreaturePermanent(ability.getManaCostsToPay().getX()));
    }
}