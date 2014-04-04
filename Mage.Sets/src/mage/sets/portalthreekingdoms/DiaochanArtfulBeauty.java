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
package mage.sets.portalthreekingdoms;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.condition.common.MyTurnBeforeAttackersDeclaredCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.common.TargetOpponent;

/**
 *
 * @author LevelX2
 */
public class DiaochanArtfulBeauty extends CardImpl<DiaochanArtfulBeauty> {

    public DiaochanArtfulBeauty(UUID ownerId) {
        super(ownerId, 108, "Diaochan, Artful Beauty", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{3}{R}");
        this.expansionSetCode = "PTK";
        this.supertype.add("Legendary");
        this.subtype.add("Human");
        this.subtype.add("Advisor");

        this.color.setRed(true);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);
        
        // {tap}: Destroy target creature of your choice, then destroy target creature of an opponent's choice. Activate this ability only during your turn, before attackers are declared.
        Ability ability = new ActivateIfConditionActivatedAbility(Zone.BATTLEFIELD, new DiaochanArtfulBeautyDestroyEffect(), new TapSourceCost(), MyTurnBeforeAttackersDeclaredCondition.getInstance());
        ability.addTarget(new TargetCreaturePermanent(true));
        ability.addTarget(new TargetOpponentsChoiceCreaturePermanent());
        this.addAbility(ability);          
    }

    public DiaochanArtfulBeauty(final DiaochanArtfulBeauty card) {
        super(card);
    }

    @Override
    public DiaochanArtfulBeauty copy() {
        return new DiaochanArtfulBeauty(this);
    }
}

class DiaochanArtfulBeautyDestroyEffect extends OneShotEffect<DiaochanArtfulBeautyDestroyEffect> {
    
    public DiaochanArtfulBeautyDestroyEffect() {
        super(Outcome.DestroyPermanent);
        this.staticText = "Destroy target creature of your choice, then destroy target creature of an opponent's choice";
    }
    
    public DiaochanArtfulBeautyDestroyEffect(final DiaochanArtfulBeautyDestroyEffect effect) {
        super(effect);
    }
    
    @Override
    public DiaochanArtfulBeautyDestroyEffect copy() {
        return new DiaochanArtfulBeautyDestroyEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Permanent firstTarget = game.getPermanent(source.getFirstTarget());
            if (firstTarget != null) {
                firstTarget.destroy(source.getSourceId(), game, false);
                
            }
            Permanent secondTarget = game.getPermanent(source.getTargets().get(1).getFirstTarget());
            if (secondTarget != null) {
                secondTarget.destroy(source.getSourceId(), game, false);                
            }
            return true;
        }
        return false;
    }
}

class TargetOpponentsChoiceCreaturePermanent extends TargetPermanent<TargetOpponentsChoiceCreaturePermanent> {

    private UUID opponentId = null;

    public TargetOpponentsChoiceCreaturePermanent() {
        super(1, 1, new FilterCreaturePermanent(), false);
        this.setRequired(true);
        this.targetName = filter.getMessage();
    }

    public TargetOpponentsChoiceCreaturePermanent(final TargetOpponentsChoiceCreaturePermanent target) {
        super(target);
        this.opponentId = target.opponentId;
    }

    @Override
    public boolean canTarget(UUID controllerId, UUID id, UUID sourceId, Game game, boolean flag) {
        if (opponentId != null) {
            return super.canTarget(opponentId, id, sourceId, game, flag);
        }
        return false;
    }

    @Override
    public boolean canTarget(UUID controllerId, UUID id, Ability source, Game game) {
        if (opponentId != null) {
            return super.canTarget(opponentId, id, source, game);
        }
        return false;
    }

    @Override
    public boolean chooseTarget(Outcome outcome, UUID playerId, Ability source, Game game) {
        return super.chooseTarget(outcome, getOpponentId(playerId, source, game), source, game);
    }

    @Override
    public TargetOpponentsChoiceCreaturePermanent copy() {
        return new TargetOpponentsChoiceCreaturePermanent(this);
    }

    private UUID getOpponentId(UUID playerId, Ability source, Game game) {
        if (opponentId == null) {
            TargetOpponent target = new TargetOpponent(true);
            Player player = game.getPlayer(playerId);
            if (player != null) {
                if (player.chooseTarget(Outcome.Detriment, target, source, game)) {
                    opponentId = target.getFirstTarget();
                }
            }

        }
        return opponentId;
    }

}
