package mage.cards.e;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.costs.common.SacrificeXTargetCost;
import mage.abilities.dynamicvalue.common.GetXValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetadjustment.TargetAdjuster;

/**
 *
 * @author LevelX2
 */
public final class EliminateTheCompetition extends CardImpl {

    public EliminateTheCompetition(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{B}");

        // As an additional cost to cast Eliminate the Competition, sacrifice X creatures.
        this.getSpellAbility().addCost(new SacrificeXTargetCost(new FilterControlledCreaturePermanent("creatures"), true));

        // Destroy X target creatures.
        Effect effect = new DestroyTargetEffect();
        effect.setText("Destroy X target creatures");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().setTargetAdjuster(EliminateTheCompetitionAdjuster.instance);
    }

    private EliminateTheCompetition(final EliminateTheCompetition card) {
        super(card);
    }

    @Override
    public EliminateTheCompetition copy() {
        return new EliminateTheCompetition(this);
    }
}

enum EliminateTheCompetitionAdjuster implements TargetAdjuster {
    instance;

    @Override
    public void adjustTargets(Ability ability, Game game) {
        ability.getTargets().clear();
        int sac = GetXValue.instance.calculate(game, ability, null);
        ability.addTarget(new TargetCreaturePermanent(sac, sac));
    }
}
