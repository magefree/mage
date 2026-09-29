package mage.abilities.effects.common.continuous;

import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.keyword.OffspringAbility;
import mage.cards.Card;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.filter.FilterSpell;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;
import mage.util.CardUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author brahle
 */
public class EachSpellYouCastHasOffspringEffect extends ContinuousEffectImpl {

    private final FilterSpell filter;
    private final Cost offspringCost;
    private final Map<UUID, OffspringAbility> offspringAbilities = new HashMap<>();

    public EachSpellYouCastHasOffspringEffect(String manaString, FilterSpell filter) {
        this(new ManaCostsImpl<>(manaString), filter);
    }

    public EachSpellYouCastHasOffspringEffect(Cost offspringCost, FilterSpell filter) {
        this(offspringCost, filter, null);
    }

    public EachSpellYouCastHasOffspringEffect(Cost offspringCost, FilterSpell filter, String reminderText) {
        super(Duration.WhileOnBattlefield, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
        this.filter = filter;
        this.offspringCost = offspringCost;
        this.staticText = CardUtil.getTextWithFirstCharUpperCase(this.filter.getMessage())
                + (this.filter.getMessage().contains("cast") ? "" : " you cast")
                + " have offspring " + this.offspringCost.getText()
                + ((reminderText != null && !reminderText.isEmpty()) ? (". <i>(" + reminderText + ")</i>") : "");
    }

    private EachSpellYouCastHasOffspringEffect(final EachSpellYouCastHasOffspringEffect effect) {
        super(effect);
        this.filter = effect.filter;
        this.offspringCost = effect.offspringCost != null ? effect.offspringCost.copy() : null;
        for (Map.Entry<UUID, OffspringAbility> entry : effect.offspringAbilities.entrySet()) {
            this.offspringAbilities.put(entry.getKey(), entry.getValue().copy());
        }
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent == null
                || !permanent.isControlledBy(source.getControllerId())) {
            return false;
        }

        boolean applied = false;

        for (StackObject stackObject : game.getStack()) {
            if (!(stackObject instanceof Spell)
                    || !((Spell) stackObject).wasCast()
                    || !stackObject.isControlledBy(source.getControllerId())) {
                continue;
            }
            Spell spell = (Spell) stackObject;
            if (filter.match(stackObject, game)) {
                Card card = spell.getCard();
                if (card != null) {
                    OffspringAbility offspringAbility = offspringAbilities.computeIfAbsent(
                            spell.getId(), k -> new OffspringAbility(offspringCost.copy())
                    );
                    game.getState().addOtherAbility(card, offspringAbility, false);
                    applied = true;
                }
            }
        }
        if (game.getStack().isEmpty()) {
            offspringAbilities.clear();
        }

        return applied;
    }

    @Override
    public EachSpellYouCastHasOffspringEffect copy() {
        return new EachSpellYouCastHasOffspringEffect(this);
    }
}
