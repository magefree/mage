
package mage.cards.d;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.ActivatedAbility;
import mage.abilities.costs.mana.ManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.mana.ActivatedManaAbilityImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityType;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.permanent.PermanentInListPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.ManaPoolItem;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.TargetPlayer;

/**
 *
 * @author L_J
 */
public final class DrainPower extends CardImpl {

    public DrainPower(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{U}{U}");

        // Target player activates a mana ability of each land they control. Then that player loses all unspent mana and you add the mana lost this way.
        this.getSpellAbility().addEffect(new DrainPowerEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());
    }

    private DrainPower(final DrainPower card) {
        super(card);
    }

    @Override
    public DrainPower copy() {
        return new DrainPower(this);
    }
}

class DrainPowerEffect extends OneShotEffect {

    private static final FilterLandPermanent filter = new FilterLandPermanent();

    public DrainPowerEffect() {
        super(Outcome.PutManaInPool);
        this.staticText = "Target player activates a mana ability of each land they control. Then that player loses all unspent mana and you add the mana lost this way";
    }

    public DrainPowerEffect(final DrainPowerEffect effect) {
        super(effect);
    }

    @Override
    public DrainPowerEffect copy() {
        return new DrainPowerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player targetPlayer = game.getPlayer(source.getFirstTarget());
        if (targetPlayer != null) {
            List<Permanent> ignorePermanents = new ArrayList<>();
            Map<Permanent, List<ActivatedManaAbilityImpl>> manaAbilitiesMap = new HashMap<>();
            TargetPermanent target = null;

            while (true) {
                targetPlayer.setPayManaMode(true);
                manaAbilitiesMap.clear();
                for (Permanent permanent : game.getBattlefield().getAllActivePermanents(filter, targetPlayer.getId(), game)) {
                    if (!ignorePermanents.contains(permanent)) {
                        List<ActivatedManaAbilityImpl> manaAbilities = new ArrayList<>();
                        abilitySearch:
                        for (Ability ability : permanent.getAbilities()) {
                            if (ability instanceof ActivatedAbility && ability.getAbilityType() == AbilityType.MANA) {
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
                    filter2.add(new PermanentInListPredicate(permList));
                    target = new TargetPermanent(1, 1, filter2, true);
                    while (!target.isChosen() && target.canChoose(targetPlayer.getId(), source, game) && targetPlayer.canRespond()) {
                        targetPlayer.chooseTarget(Outcome.Neutral, target, source, game);
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

            // 106.12. One card (Drain Power) causes one player to lose unspent mana and another to add “the mana lost this way.” (Note that these may be the same player.)
            // This empties the former player's mana pool and causes the mana emptied this way to be put into the latter player's mana pool. Which permanents, spells, and/or
            // abilities produced that mana are unchanged, as are any restrictions or additional effects associated with any of that mana.
            List<ManaPoolItem> manaItems = targetPlayer.getManaPool().getManaItems();
            targetPlayer.getManaPool().emptyPool(game);
            for (ManaPoolItem manaPoolItem : manaItems) {
                controller.getManaPool().addMana(
                        manaPoolItem.isConditional() ? manaPoolItem.getConditionalMana() : manaPoolItem.getMana(),
                        game, source, Duration.EndOfTurn.equals(manaPoolItem.getDuration()));
            }
            return true;
        }
        return false;
    }
}
