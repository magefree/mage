/*
 *  Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without modification, are
 *  permitted provided that the following conditions are met:
 *
 *     1. Redistributions of source code must retain the above copyright notice, this list of
 *        conditions and the following disclaimer.
 *
 *     2. Redistributions in binary form must reproduce the above copyright notice, this list
 *        of conditions and the following disclaimer in the documentation and/or other materials
 *        provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *  The views and conclusions contained in the software and documentation are those of the
 *  authors and should not be interpreted as representing official policies, either expressed
 *  or implied, of BetaSteward_at_googlemail.com.
 */
package mage.cards.d;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.ActivatedAbility;
import mage.abilities.costs.mana.ManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.mana.ActivatedManaAbilityImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityType;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.permanent.PermanentInListPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.TargetPlayer;

/**
 *
 * @author L_J
 */
public class DrainPower extends CardImpl {

    public DrainPower(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{U}{U}");

        // Target player activates a mana ability of each land they control. Then that player loses all unspent mana and you add the mana lost this way.
        this.getSpellAbility().addEffect(new DrainPowerEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());
    }

    public DrainPower(final DrainPower card) {
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
        super(Outcome.Tap);
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
            TargetPermanent target = null;
            
            do {
                Map<Permanent, List<ActivatedManaAbilityImpl>> manaAbilitiesMap = new HashMap<>();
                for (Permanent permanent : game.getBattlefield().getAllActivePermanents(filter, targetPlayer.getId(), game)) {
                    if (!ignorePermanents.contains(permanent)) {
                        List<ActivatedManaAbilityImpl> manaAbilities = new ArrayList<>();
                        abilitySearch:
                        for (Ability ability : permanent.getAbilities()) {
                            if (ability instanceof ActivatedAbility && ability.getAbilityType() == AbilityType.MANA) {
                                ActivatedManaAbilityImpl manaAbility = (ActivatedManaAbilityImpl) ability;
                                // TODO: make Rhystic Cave untappable due to its instant speed limitation (this is a Rhystic Cave canActivate bug)
                                if (manaAbility != null && manaAbility.canActivate(targetPlayer.getId(), game)) {
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
                
                List<Permanent> permList = new ArrayList<Permanent>(manaAbilitiesMap.keySet());
                Permanent permanent = null;
                if (permList.size() > 1 || target != null) {
                    FilterLandPermanent filter2 = new FilterLandPermanent("land you control to tap for mana (remaining: " + permList.size() + ')');
                    filter2.add(new PermanentInListPredicate(permList));
                    target = new TargetPermanent(1, 1, filter2, true);
                    while (!target.isChosen() && target.canChoose(targetPlayer.getId(), game) && targetPlayer.canRespond()) {
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
            } while (target != null && target.canChoose(targetPlayer.getId(), game));
            
            // 106.12. One card (Drain Power) causes one player to lose unspent mana and another to add “the mana lost this way.” (Note that these may be the same player.) 
            // This empties the former player’s mana pool and causes the mana emptied this way to be put into the latter player’s mana pool. Which permanents, spells, and/or 
            // abilities produced that mana are unchanged, as are any restrictions or additional effects associated with any of that mana.
            // TODO: retain riders associated with drained mana
            Mana mana = targetPlayer.getManaPool().getMana();
            targetPlayer.getManaPool().emptyPool(game);
            controller.getManaPool().addMana(mana, game, source);
            return true;
        }
        return false;
    }
}
