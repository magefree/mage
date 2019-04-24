
package mage.cards.b;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;

/**
 *
 * @author TheElk801
 */
public final class BelligerentBrontodon extends CardImpl {

    public BelligerentBrontodon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{G}{W}");

        this.subtype.add(SubType.DINOSAUR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(6);

        // Each creature you control assigns combat damage equal to its toughness rather than its power.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BelligerentBrontodonCombatDamageRuleEffect()));
    }

    public BelligerentBrontodon(final BelligerentBrontodon card) {
        super(card);
    }

    @Override
    public BelligerentBrontodon copy() {
        return new BelligerentBrontodon(this);
    }
}

class BelligerentBrontodonCombatDamageRuleEffect extends ContinuousEffectImpl {

    public BelligerentBrontodonCombatDamageRuleEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Detriment);
        staticText = "Each creature you control assigns combat damage equal to its toughness rather than its power";
    }

    public BelligerentBrontodonCombatDamageRuleEffect(final BelligerentBrontodonCombatDamageRuleEffect effect) {
        super(effect);
    }

    @Override
    public BelligerentBrontodonCombatDamageRuleEffect copy() {
        return new BelligerentBrontodonCombatDamageRuleEffect(this);
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        // Change the rule
        FilterCreaturePermanent filter = new FilterCreaturePermanent();
        filter.add(new ControllerIdPredicate(source.getControllerId()));
        game.getCombat().setUseToughnessForDamage(true);
        game.getCombat().addUseToughnessForDamageFilter(filter);
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
