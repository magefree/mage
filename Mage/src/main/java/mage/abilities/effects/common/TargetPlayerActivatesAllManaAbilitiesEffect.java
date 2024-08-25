package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.mana.ActivatedManaAbilityImpl;
import mage.constants.AbilityType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.permanent.PermanentReferenceInCollectionPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author L_J
 */
public class TargetPlayerActivatesAllManaAbilitiesEffect extends OneShotEffect {

    /**
     * For use in Drain Power and Pygmy Hippo
     */
    public TargetPlayerActivatesAllManaAbilitiesEffect() {
        super(Outcome.Detriment);
    }

    protected TargetPlayerActivatesAllManaAbilitiesEffect(final TargetPlayerActivatesAllManaAbilitiesEffect effect) {
        super(effect);
    }

    @Override
    public TargetPlayerActivatesAllManaAbilitiesEffect copy() {
        return new TargetPlayerActivatesAllManaAbilitiesEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player targetPlayer = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (targetPlayer == null) {
            return false;
        }
        List<Permanent> ignorePermanents = new ArrayList<>();
        Map<Permanent, List<ActivatedManaAbilityImpl>> manaAbilitiesMap = new HashMap<>();
        TargetPermanent target = null;

        while (targetPlayer.canRespond()) {
            targetPlayer.setPayManaMode(true);
            manaAbilitiesMap.clear();
            for (Permanent permanent : game.getBattlefield().getAllActivePermanents(StaticFilters.FILTER_LAND, targetPlayer.getId(), game)) {
                if (!ignorePermanents.contains(permanent)) {
                    List<ActivatedManaAbilityImpl> manaAbilities = new ArrayList<>();
                    abilitySearch:
                    for (Ability ability : permanent.getAbilities()) {
                        if (AbilityType.ACTIVATED_MANA.equals(ability.getAbilityType())) {
                            ActivatedManaAbilityImpl manaAbility = (ActivatedManaAbilityImpl) ability;
                            if (manaAbility.canActivate(targetPlayer.getId(), game).canActivate()) {
                                // canActivate can't check for mana abilities that require a mana cost, if the payment isn't possible (Cabal Coffers etc)
                                // so it's necessary to filter them out manually - might be buggy in some fringe cases
                                for (ManaCost manaCost : manaAbility.getManaCosts()) {
                                    if (!targetPlayer.getManaPool().getMana().includesMana(manaCost.getMana())) {
                                        continue abilitySearch;
                                    }
                                }
                                manaAbilities.add(manaAbility);
                            }
                        }
                    }
                    if (!manaAbilities.isEmpty()) {
                        manaAbilitiesMap.put(permanent, manaAbilities);
                    }
                }
            }
            if (manaAbilitiesMap.isEmpty()) {
                break;
            }

            List<Permanent> permList = new ArrayList<>(manaAbilitiesMap.keySet());
            Permanent permanent;
            if (permList.size() > 1 || target != null) {
                FilterLandPermanent filter2 = new FilterLandPermanent("land you control to tap for mana (remaining: " + permList.size() + ')');
                filter2.add(new PermanentReferenceInCollectionPredicate(permList, game));
                target = new TargetPermanent(1, 1, filter2, true);
                while (!target.isChosen(game) && target.canChoose(targetPlayer.getId(), source, game) && targetPlayer.canRespond()) {
                    targetPlayer.choose(Outcome.Neutral, target, source, game);
                }
                permanent = game.getPermanent(target.getFirstTarget());
            } else {
                permanent = permList.get(0);
            }
            if (permanent != null) {
                int i = 0;
                for (ActivatedManaAbilityImpl manaAbility : manaAbilitiesMap.get(permanent)) {
                    i++;
                    if (manaAbilitiesMap.get(permanent).size() <= i
                            || targetPlayer.chooseUse(Outcome.Neutral, "Activate mana ability \"" + manaAbility.getRule() + "\" of " + permanent.getLogName()
                            + "? (Choose \"no\" to activate next mana ability)", source, game)) {
                        boolean originalCanUndo = manaAbility.isUndoPossible();
                        manaAbility.setUndoPossible(false); // prevents being able to undo Drain Power
                        if (targetPlayer.activateAbility(manaAbility, game)) {
                            ignorePermanents.add(permanent);
                        }
                        manaAbility.setUndoPossible(originalCanUndo); // resets undoPossible to its original state
                        break;
                    }
                }
            }
        }
        targetPlayer.setPayManaMode(false);
        return true;
    }

}
