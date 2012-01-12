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
package mage.sets.championsofkamigawa;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ColoredManaCost;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.StackAbility;
import mage.target.TargetPlayer;
import mage.watchers.common.PlayerDamagedBySourceWatcher;

/**
 *
 * @author LevelX
 */
public class WickedAkuba extends CardImpl<WickedAkuba> {
    
    public WickedAkuba(UUID ownerId) {
        super(ownerId, 150, "Wicked Akuba", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{B}{B}");
        this.expansionSetCode = "CHK";
        this.subtype.add("Spirit");

        this.color.setBlack(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {B}: Target player dealt damage by Wicked Akuba this turn loses 1 life.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new LoseLifeTargetEffect(1), new ColoredManaCost(Constants.ColoredManaSymbol.B));
        ability.addTarget(new WickedAkubaTarget());
        this.addAbility(ability);
    }

    public WickedAkuba(final WickedAkuba card) {
        super(card);
    }

    @Override
    public WickedAkuba copy() {
        return new WickedAkuba(this);
    }    
     
}

class WickedAkubaTarget extends TargetPlayer<WickedAkubaTarget> {

	public WickedAkubaTarget() {
		super();
		this.targetName = "player dealt damage by Wicked Akuba this turn";
	}

	public WickedAkubaTarget(final WickedAkubaTarget target) {
		super(target);
	}
	
	@Override
	public boolean canChoose(UUID sourceId, UUID sourceControllerId, Game game) {
		filter.getPlayerId().clear();
                UUID source = null;
                MageObject targetSource = game.getObject(sourceId);
                if (targetSource instanceof StackAbility) {
                    StackAbility stackAbility = (StackAbility) targetSource;
                    source = stackAbility.getSourceId();
                }
                if (targetSource instanceof Permanent) {
                    Permanent permanent = (Permanent) targetSource;
                    source = permanent.getId();
                }
                if (source != null) {
                        for (UUID playerId: game.getPlayer(sourceControllerId).getInRange()){
                                PlayerDamagedBySourceWatcher watcher = (PlayerDamagedBySourceWatcher) game.getState().getWatchers().get("PlayerDamagedBySource", playerId);
                                if (watcher != null && watcher.damageSources.contains(source))
                                            filter.getPlayerId().add(playerId);
                        }
                }
                if (filter.getPlayerId().isEmpty()) // neccessary because empty playerId filter allows all players
                    return false;
		return super.canChoose(sourceId, sourceControllerId, game);
	}
	
	@Override
	public boolean canTarget(UUID id, Ability source, Game game) {
		filter.getPlayerId().clear();
                for (UUID playerId: game.getPlayer(source.getControllerId()).getInRange()){
                    PlayerDamagedBySourceWatcher watcher = (PlayerDamagedBySourceWatcher) game.getState().getWatchers().get("PlayerDamagedBySource", playerId);

                    if (watcher != null && watcher.damageSources.contains(source.getSourceId()))
                                            filter.getPlayerId().add(playerId);
                }
                if (filter.getPlayerId().isEmpty()) // neccessary because empty playerId filter allows all players
                    return false;
		return super.canTarget(id, source, game);
	}

	@Override
	public WickedAkubaTarget copy() {
		return new WickedAkubaTarget(this);
	}

}