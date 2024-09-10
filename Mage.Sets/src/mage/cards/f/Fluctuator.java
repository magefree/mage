package mage.cards.f;

import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.abilities.keyword.CyclingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.ChoiceImpl;
import mage.constants.*;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author LevelX2
 */
public final class Fluctuator extends CardImpl {

    public Fluctuator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        // Cycling abilities you activate cost you up to {2} less to activate.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new FluctuatorEffect()));
    }

    private Fluctuator(final Fluctuator card) {
        super(card);
    }

    @Override
    public Fluctuator copy() {
        return new Fluctuator(this);
    }
}

class FluctuatorEffect extends CostModificationEffectImpl {

    private static final String effectText = "Cycling abilities you activate cost up to {2} less to activate";

    public FluctuatorEffect() {
        super(Duration.Custom, Outcome.Benefit, CostModificationType.REDUCE_COST);
        staticText = effectText;
    }

    private FluctuatorEffect(final FluctuatorEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        return abilityToModify.isControlledBy(source.getControllerId())
                && (abilityToModify instanceof CyclingAbility);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        Player controller = game.getPlayer(abilityToModify.getControllerId());
        if (controller != null) {
            Mana mana = abilityToModify.getManaCostsToPay().getMana();
            int reduceMax = mana.getGeneric();
            if (reduceMax > 2) {
                reduceMax = 2;
            }
            if (reduceMax > 0) {
                int reduce;
                if (game.inCheckPlayableState() || controller.isComputer()) {
                    reduce = reduceMax;
                } else {
                    ChoiceImpl choice = new ChoiceImpl(true);
                    Set<String> set = new LinkedHashSet<>();

                    for (int i = 0; i <= reduceMax; i++) {
                        set.add(String.valueOf(i));
                    }
                    choice.setChoices(set);
                    choice.setMessage("Reduce cycling cost");

                    if (controller.choose(Outcome.Benefit, choice, game)) {
                        reduce = Integer.parseInt(choice.getChoice());
                    } else {
                        return false;
                    }
                }
                CardUtil.reduceCost(abilityToModify, reduce);
            }
            return true;
        }
        return false;
    }

    @Override
    public FluctuatorEffect copy() {
        return new FluctuatorEffect(this);
    }
}
