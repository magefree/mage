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
package mage.sets.avacynrestored;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.stack.StackObject;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.filter.Filter;
import mage.filter.FilterSpell;
import mage.players.Player;
import mage.game.stack.Spell;
import mage.target.TargetObject;
import mage.target.Target;



/**
 *
 * @author jeffwadsworth
 */
public class Outwit extends CardImpl<Outwit> {
    
    private static FilterSpell filter = new FilterSpell("spell that targets a player");

    public Outwit(UUID ownerId) {
        super(ownerId, 70, "Outwit", Rarity.COMMON, new CardType[]{CardType.INSTANT}, "{U}");
        this.expansionSetCode = "AVR";

        this.color.setBlue(true);

        // Counter target spell that targets a player.
        this.getSpellAbility().addEffect(new CounterTargetEffect());
	this.getSpellAbility().addTarget(new CustomTargetSpell(filter));
    }

    public Outwit(final Outwit card) {
        super(card);
    }

    @Override
    public Outwit copy() {
        return new Outwit(this);
    }


    private class CustomTargetSpell extends TargetObject<CustomTargetSpell> {

        protected FilterSpell filter;

	public CustomTargetSpell() {
            this(1, 1, new FilterSpell());
	}

	public CustomTargetSpell(FilterSpell filter) {
            this(1, 1, filter);
	}

	public CustomTargetSpell(int numTargets, FilterSpell filter) {
            this(numTargets, numTargets, filter);
	}

	public CustomTargetSpell(int minNumTargets, int maxNumTargets, FilterSpell filter) {
            this.minNumberOfTargets = minNumTargets;
            this.maxNumberOfTargets = maxNumTargets;
            this.zone = Constants.Zone.STACK;
            this.filter = filter;
            this.targetName = filter.getMessage();
	}

	public CustomTargetSpell(final CustomTargetSpell target) {
            super(target);
            this.filter = target.filter.copy();
	}

	@Override
	public boolean canChoose(UUID sourceId, UUID sourceControllerId, Game game) {
            return canChoose(sourceControllerId, game);
	}

	@Override
	public Set<UUID> possibleTargets(UUID sourceId, UUID sourceControllerId, Game game) {
            return possibleTargets(sourceControllerId, game);
	}

	@Override
	public boolean canTarget(UUID id, Ability source, Game game) {
            if (super.canTarget(id, source, game)) {
                if (targetsPlayer(id, game)) {
                    return true;
		}
            }
            return false;
	}

	@Override
	public boolean canChoose(UUID sourceControllerId, Game game) {
            int count = 0;
            for (StackObject stackObject : game.getStack()) {
		if (stackObject instanceof Spell && filter.match((Spell) stackObject, game)) {
                    if (targetsPlayer(stackObject.getId(), game)) {
                        count++;
			if (count >= this.minNumberOfTargets)
                            return true;
                    }
		}
            }
            return false;
	}

	@Override
	public Set<UUID> possibleTargets(UUID sourceControllerId, Game game) {
            Set<UUID> possibleTargets = new HashSet<UUID>();
            for (StackObject stackObject : game.getStack()) {
                if (stackObject instanceof Spell && filter.match((Spell) stackObject, game)) {
                    if (targetsPlayer(stackObject.getId(), game)) {
                        possibleTargets.add(stackObject.getId());
                    }
		}
            }
            return possibleTargets;
	}

	@Override
	public Filter getFilter() {
            return filter;
	}

	private boolean targetsPlayer(UUID id, Game game) {
            StackObject spell = game.getStack().getStackObject(id);
            if (spell != null) {
		Ability ability = spell.getStackAbility();
                if (ability != null && !ability.getTargets().isEmpty()) {
                    for (Target target : ability.getTargets()) {
                        for (UUID playerId : target.getTargets()) {
                            Player player = game.getPlayer(playerId);
                            if (player != null) {
                                return true;
                            }
                        }
                    }
                }
            }
            return false;
	}

	@Override
	public CustomTargetSpell copy() {
            return new CustomTargetSpell(this);
	}
    }
}