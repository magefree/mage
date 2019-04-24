
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;

/**
 *
 * 613.10. Some continuous effects affect game rules rather than objects. For
 * example, effects may modify a player's maximum hand size, or say that a
 * creature must attack this turn if able. These effects are applied after all
 * other continuous effects have been applied. Continuous effects that affect
 * the costs of spells or abilities are applied according to the order specified
 * in rule 601.2e. All other such effects are applied in timestamp order. See
 * also the rules for timestamp order and dependency (rules 613.6 and 613.7)
 *
 *
 * @author LevelX2
 */
public final class DoranTheSiegeTower extends CardImpl {

    public DoranTheSiegeTower(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}{B}{G}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.TREEFOLK);
        this.subtype.add(SubType.SHAMAN);

        this.power = new MageInt(0);
        this.toughness = new MageInt(5);

        // Each creature assigns combat damage equal to its toughness rather than its power.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new DoranTheSiegeTowerCombatDamageRuleEffect()));
    }

    public DoranTheSiegeTower(final DoranTheSiegeTower card) {
        super(card);
    }

    @Override
    public DoranTheSiegeTower copy() {
        return new DoranTheSiegeTower(this);
    }
}

class DoranTheSiegeTowerCombatDamageRuleEffect extends ContinuousEffectImpl {

    public DoranTheSiegeTowerCombatDamageRuleEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Detriment);
        staticText = "Each creature assigns combat damage equal to its toughness rather than its power";
    }

    public DoranTheSiegeTowerCombatDamageRuleEffect(final DoranTheSiegeTowerCombatDamageRuleEffect effect) {
        super(effect);
    }

    @Override
    public DoranTheSiegeTowerCombatDamageRuleEffect copy() {
        return new DoranTheSiegeTowerCombatDamageRuleEffect(this);
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        // Change the rule
        game.getCombat().setUseToughnessForDamage(true);
        game.getCombat().addUseToughnessForDamageFilter(StaticFilters.FILTER_PERMANENT_CREATURES);
        return true;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean hasLayer(Layer layer) {
        return layer == Layer.RulesEffects;
    }
}
