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
package mage.sets.eldritchmoon;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.CopySpellForEachItCouldTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.FilterInPlay;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.Target;
import mage.util.TargetAddress;

/**
 *
 * @author LevelX2
 */
public class MirrorwingDragon extends CardImpl {

    public MirrorwingDragon(UUID ownerId) {
        super(ownerId, 136, "Mirrorwing Dragon", Rarity.MYTHIC, new CardType[]{CardType.CREATURE}, "{3}{R}{R}");
        this.expansionSetCode = "EMN";
        this.subtype.add("Dragon");
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Whenever a player casts an instant or sorcery spell that targets only Mirrorwing Dragon,
        // that player copies that spell for each other creature he or she controls that the spell could target.
        // Each copy targets a different one of those creatures.
        this.addAbility(new MirrorwingDragonCopyTriggeredAbility());
    }

    public MirrorwingDragon(final MirrorwingDragon card) {
        super(card);
    }

    @Override
    public MirrorwingDragon copy() {
        return new MirrorwingDragon(this);
    }
}

class MirrorwingDragonCopyTriggeredAbility extends TriggeredAbilityImpl {

    MirrorwingDragonCopyTriggeredAbility() {
        super(Zone.BATTLEFIELD, new MirrorwingDragonCopySpellEffect(), false);
    }

    MirrorwingDragonCopyTriggeredAbility(final MirrorwingDragonCopyTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public MirrorwingDragonCopyTriggeredAbility copy() {
        return new MirrorwingDragonCopyTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Spell spell = game.getStack().getSpell(event.getTargetId());
        return checkSpell(spell, game);
    }

    private boolean checkSpell(Spell spell, Game game) {
        if (spell != null
                && (spell.getCardType().contains(CardType.INSTANT) || spell.getCardType().contains(CardType.SORCERY))) {
            for (TargetAddress addr : TargetAddress.walk(spell)) {
                Target targetInstance = addr.getTarget(spell);
                for (UUID target : targetInstance.getTargets()) {
                    Permanent permanent = game.getPermanent(target);
                    if (permanent == null || !permanent.getId().equals(getSourceId())) {
                        return false;
                    }
                }
            }
            getEffects().get(0).setValue("triggeringSpell", spell);
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a player casts an instant or sorcery spell that targets only {this}, "
                + "that player copies that spell for each creature he or she controls that the spell could target. "
                + "Each copy targets a different one of those creatures.";
    }
}

class MirrorwingDragonCopySpellEffect extends CopySpellForEachItCouldTargetEffect<Permanent> {

    public MirrorwingDragonCopySpellEffect() {
        this(new FilterControlledCreaturePermanent());
        this.staticText = "that player copies that spell for each creature he or she controls that the spell could target. Each copy targets a different one of those creatures.";
    }

    public MirrorwingDragonCopySpellEffect(MirrorwingDragonCopySpellEffect effect) {
        super(effect);
    }

    private MirrorwingDragonCopySpellEffect(FilterInPlay<Permanent> filter) {
        super(filter);
    }

    @Override
    protected Player getPlayer(Game game, Ability source) {
        Spell spell = getSpell(game, source);
        if (spell != null) {
            return game.getPlayer(spell.getControllerId());
        }
        return null;
    }

    @Override
    protected Spell getSpell(Game game, Ability source) {
        return (Spell) getValue("triggeringSpell");
    }

    @Override
    protected boolean changeTarget(Target target, Game game, Ability source) {
        return true;
    }

    @Override
    protected void modifyCopy(Spell copy, Game game, Ability source) {
    }

    @Override
    public MirrorwingDragonCopySpellEffect copy() {
        return new MirrorwingDragonCopySpellEffect(this);
    }
}
