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
package mage.cards.r;

import java.util.UUID;
import mage.MageItem;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.common.CopySpellForEachItCouldTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterInPlay;
import mage.filter.FilterSpell;
import mage.filter.common.FilterInstantOrSorcerySpell;
import mage.filter.common.FilterPermanentOrPlayer;
import mage.filter.predicate.ObjectPlayer;
import mage.filter.predicate.ObjectPlayerPredicate;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetSpell;
import mage.util.TargetAddress;

/**
 * @author duncant
 */
public class Radiate extends CardImpl {

    protected static final FilterSpell filter = new FilterInstantOrSorcerySpell();

    static {
        filter.add(new SpellWithOnlySingleTargetPredicate());
        filter.add(new SpellWithOnlyPermanentOrPlayerTargetsPredicate());
        filter.setMessage("instant or sorcery spell that targets only a single permanent or player");
    }

    public Radiate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{3}{R}{R}");

        // Choose target instant or sorcery spell that targets only a single permanent or player. Copy that spell for each other permanent or player the spell could target. Each copy targets a different one of those permanents and players.
        this.getSpellAbility().addEffect(new RadiateEffect());
        this.getSpellAbility().addTarget(new TargetSpell(filter));
    }

    public Radiate(final Radiate card) {
        super(card);
    }

    @Override
    public Radiate copy() {
        return new Radiate(this);
    }
}

class SpellWithOnlySingleTargetPredicate implements ObjectPlayerPredicate<ObjectPlayer<Spell>> {

    @Override
    public boolean apply(ObjectPlayer<Spell> input, Game game) {
        Spell spell = input.getObject();
        if (spell == null) {
            return false;
        }
        UUID singleTarget = null;
        for (TargetAddress addr : TargetAddress.walk(spell)) {
            Target targetInstance = addr.getTarget(spell);
            for (UUID targetId : targetInstance.getTargets()) {
                if (singleTarget == null) {
                    singleTarget = targetId;
                } else if (!singleTarget.equals(targetId)) {
                    return false;
                }
            }
        }
        return singleTarget != null;
    }
}

class SpellWithOnlyPermanentOrPlayerTargetsPredicate implements ObjectPlayerPredicate<ObjectPlayer<Spell>> {

    @Override
    public boolean apply(ObjectPlayer<Spell> input, Game game) {
        Spell spell = input.getObject();
        if (spell == null) {
            return false;
        }
        for (TargetAddress addr : TargetAddress.walk(spell)) {
            Target targetInstance = addr.getTarget(spell);
            for (UUID targetId : targetInstance.getTargets()) {
                if (game.getPermanent(targetId) == null
                        && game.getPlayer(targetId) == null) {
                    return false;
                }
            }
        }
        return true;
    }
}

class RadiateEffect extends CopySpellForEachItCouldTargetEffect<MageItem> {

    public RadiateEffect() {
        this(new FilterPermanentOrPlayer());
    }

    public RadiateEffect(RadiateEffect effect) {
        super(effect);
    }

    private RadiateEffect(FilterInPlay<MageItem> filter) {
        super(filter);
    }

    @Override
    public String getText(Mode mode) {
        return "Choose target instant or sorcery spell that targets only a single permanent or player. Copy that spell for each other permanent or player the spell could target. Each copy targets a different one of those permanents and players.";
    }

    @Override
    protected Player getPlayer(Game game, Ability source) {
        return game.getPlayer(source.getControllerId());
    }

    @Override
    protected Spell getSpell(Game game, Ability source) {
        StackObject ret = game.getStack().getStackObject(targetPointer.getFirst(game, source));
        if (ret instanceof Spell) {
            return (Spell) ret;
        }
        return null;
    }

    @Override
    protected boolean changeTarget(Target target, Game game, Ability source) {
        return true;
    }

    @Override
    protected void modifyCopy(Spell copy, Game game, Ability source) {
        copy.setControllerId(source.getControllerId());
    }

    @Override
    public RadiateEffect copy() {
        return new RadiateEffect(this);
    }
}
