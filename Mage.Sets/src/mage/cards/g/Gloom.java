package mage.cards.g;

import mage.MageObject;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.abilities.effects.common.cost.SpellsCostIncreasingAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author escplan9 (Derek Monturo - dmontur1 at gmail dot com)
 */
public final class Gloom extends CardImpl {

    private static final FilterCard filter = new FilterCard("White spells");

    static {
        filter.add(new ColorPredicate(ObjectColor.WHITE));
    }

    public Gloom(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{B}");

        // White spells cost {3} more to cast.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new SpellsCostIncreasingAllEffect(3, filter, TargetController.ANY)));

        // Activated abilities of white enchantments cost {3} more to activate.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GloomCostIncreaseEffect()));
    }

    private Gloom(final Gloom card) {
        super(card);
    }

    @Override
    public Gloom copy() {
        return new Gloom(this);
    }
}

class GloomCostIncreaseEffect extends CostModificationEffectImpl {

    GloomCostIncreaseEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit, CostModificationType.INCREASE_COST);
        staticText = "Activated abilities of white enchantments cost {3} more to activate.";
    }

    private GloomCostIncreaseEffect(final GloomCostIncreaseEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        CardUtil.increaseCost(abilityToModify, 3);
        return true;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        boolean isWhiteEnchantment = false;
        boolean isActivated = abilityToModify.getAbilityType() == AbilityType.ACTIVATED;
        if (isActivated) {
            MageObject permanent = game.getPermanent(abilityToModify.getSourceId());
            if (permanent != null) {
                isWhiteEnchantment = permanent.isEnchantment(game) && permanent.getColor(game).isWhite();
            }
        }
        return isActivated && isWhiteEnchantment;
    }

    @Override
    public GloomCostIncreaseEffect copy() {
        return new GloomCostIncreaseEffect(this);
    }
}