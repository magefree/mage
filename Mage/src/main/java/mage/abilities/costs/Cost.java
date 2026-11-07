package mage.abilities.costs;

import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.cards.Card;
import mage.game.Game;
import mage.target.Targets;
import mage.util.Copyable;

import java.io.Serializable;
import java.util.Set;
import java.util.UUID;

public interface Cost extends Serializable, Copyable<Cost> {

    UUID getId();

    String getText();

    Cost setText(String text);

    /**
     * Check is it possible to pay
     * For mana it checks only single color and amount available, not total mana cost
     * <p>
     * Warning, if you want to use canChoose, then don't forget about already selected targets (important for AI sims).
     */
    boolean canPay(Ability ability, Ability source, UUID controllerId, Game game);

    /**
     * Simple canPay logic implementation with targets - cost has possible targets or already selected it, e.g. by AI sims
     * <p>
     * Do not override
     */
    default boolean canChooseOrAlreadyChosen(Ability ability, Ability source, UUID controllerId, Game game) {
        // a spell is put on the stack before its costs are paid, so its own card can't be chosen for them (601.2a)
        Card spellCard = ability instanceof SpellAbility ? game.getCard(ability.getSourceId()) : null;
        UUID spellCardId = spellCard == null ? null : spellCard.getMainCard().getId();
        return this.getTargets().stream().allMatch(target -> {
            if (target.isChosen(game)) {
                return true;
            }
            if (!target.canChoose(controllerId, source, game)) {
                return false;
            }
            if (spellCardId == null) {
                return true;
            }
            Set<UUID> possibleTargets = target.possibleTargets(controllerId, source, game);
            return !possibleTargets.contains(spellCardId)
                    || target.getSize() + possibleTargets.size() - 1 >= target.getMinNumberOfTargets();
        });
    }

    boolean pay(Ability ability, Game game, Ability source, UUID controllerId, boolean noMana);

    boolean pay(Ability ability, Game game, Ability source, UUID controllerId, boolean noMana, Cost costToPay);

    boolean isPaid();

    void clearPaid();

    void setPaid();

    /**
     * Warning, can return copied list in composite costs, so it will be un-changeable
     * Use targets list modification only in CostAdjuster for single card/effect
     */
    Targets getTargets();

    Cost copy();

}
