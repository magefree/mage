package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.SpellAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SphinxOfNewPrahv extends CardImpl {

    public SphinxOfNewPrahv(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}{W}{U}{U}");

        this.subtype.add(SubType.SPHINX);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Spells your opponents cast that target Sphinx of New Prahv cost {2} more to cast.
        this.addAbility(new SimpleStaticAbility(new SphinxOfNewPrahvCostIncreaseEffect()));
    }

    private SphinxOfNewPrahv(final SphinxOfNewPrahv card) {
        super(card);
    }

    @Override
    public SphinxOfNewPrahv copy() {
        return new SphinxOfNewPrahv(this);
    }
}

class SphinxOfNewPrahvCostIncreaseEffect extends CostModificationEffectImpl {

    SphinxOfNewPrahvCostIncreaseEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit, CostModificationType.INCREASE_COST);
        staticText = "Spells your opponents cast that target {this} cost {2} more to cast";
    }

    private SphinxOfNewPrahvCostIncreaseEffect(SphinxOfNewPrahvCostIncreaseEffect effect) {
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
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null
                || !(abilityToModify instanceof SpellAbility)
                || !controller.hasOpponent(abilityToModify.getControllerId(), game)) {
            return false;
        }
        for (UUID modeId : abilityToModify.getModes().getSelectedModes()) {
            Mode mode = abilityToModify.getModes().get(modeId);
            for (Target target : mode.getTargets()) {
                for (UUID targetUUID : target.getTargets()) {
                    if (targetUUID.equals(source.getSourceId())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public SphinxOfNewPrahvCostIncreaseEffect copy() {
        return new SphinxOfNewPrahvCostIncreaseEffect(this);
    }

}
