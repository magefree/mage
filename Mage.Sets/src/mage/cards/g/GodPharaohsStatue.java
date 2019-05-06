package mage.cards.g;

import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GodPharaohsStatue extends CardImpl {

    public GodPharaohsStatue(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{6}");

        this.addSuperType(SuperType.LEGENDARY);

        // Spells your opponents cast cost {2} more to cast.
        this.addAbility(new SimpleStaticAbility(new GodPharaohsStatueEffect()));

        // At the beginning of your end step, each opponent loses 1 life.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                new LoseLifeOpponentsEffect(1), TargetController.YOU, false
        ));
    }

    private GodPharaohsStatue(final GodPharaohsStatue card) {
        super(card);
    }

    @Override
    public GodPharaohsStatue copy() {
        return new GodPharaohsStatue(this);
    }
}

class GodPharaohsStatueEffect extends CostModificationEffectImpl {

    private static final String effectText = "Spells your opponents cast cost {2} more to cast";

    GodPharaohsStatueEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit, CostModificationType.INCREASE_COST);
        staticText = effectText;
    }

    private GodPharaohsStatueEffect(GodPharaohsStatueEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        SpellAbility spellAbility = (SpellAbility) abilityToModify;
        CardUtil.adjustCost(spellAbility, -2);
        return true;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        if (abilityToModify instanceof SpellAbility) {
            if (game.getOpponents(source.getControllerId()).contains(abilityToModify.getControllerId())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public GodPharaohsStatueEffect copy() {
        return new GodPharaohsStatueEffect(this);
    }

}
