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
package mage.cards.v;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.PartnerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SuperType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.util.RandomUtil;
import mage.watchers.common.SpellsCastWatcher;

/**
 *
 * @author LevelX2
 */
public class VialSmasherTheFierce extends CardImpl {

    public VialSmasherTheFierce(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}{R}");

        addSuperType(SuperType.LEGENDARY);
        this.subtype.add("Goblin");
        this.subtype.add("Berserker");
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Whenever you cast your first spell each turn, Vial Smasher the Fierce deals damage equal to that spell's converted mana cost to an opponent chosen at random.
        this.addAbility(new VialSmasherTheFierceTriggeredAbility(), new SpellsCastWatcher());

        // Partner
        this.addAbility(PartnerAbility.getInstance());
    }

    public VialSmasherTheFierce(final VialSmasherTheFierce card) {
        super(card);
    }

    @Override
    public VialSmasherTheFierce copy() {
        return new VialSmasherTheFierce(this);
    }
}

class VialSmasherTheFierceTriggeredAbility extends SpellCastControllerTriggeredAbility {

    VialSmasherTheFierceTriggeredAbility() {
        super(new VialSmasherTheFierceEffect(), false);
    }

    VialSmasherTheFierceTriggeredAbility(VialSmasherTheFierceTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public VialSmasherTheFierceTriggeredAbility copy() {
        return new VialSmasherTheFierceTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (super.checkTrigger(event, game)) {
            SpellsCastWatcher watcher = (SpellsCastWatcher) game.getState().getWatchers().get(SpellsCastWatcher.class.getSimpleName());
            if (watcher != null) {
                List<Spell> spells = watcher.getSpellsCastThisTurn(event.getPlayerId());
                if (spells != null && spells.size() == 1) {
                    Spell spell = game.getStack().getSpell(event.getTargetId());
                    if (spell != null) {
                        for (Effect effect : getEffects()) {
                            effect.setValue("VialSmasherTheFierceCMC", spell.getConvertedManaCost());
                        }
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever you cast your first spell each turn, {this} deals damage equal to that spell's converted mana cost to an opponent chosen at random.";
    }
}

class VialSmasherTheFierceEffect extends OneShotEffect {

    public VialSmasherTheFierceEffect() {
        super(Outcome.Damage);
        this.staticText = "{this} deals damage equal to that spell's converted mana cost to an opponent chosen at random";
    }

    public VialSmasherTheFierceEffect(final VialSmasherTheFierceEffect effect) {
        super(effect);
    }

    @Override
    public VialSmasherTheFierceEffect copy() {
        return new VialSmasherTheFierceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            int damage = (Integer) getValue("VialSmasherTheFierceCMC");
            if (damage > 0) {
                Set<UUID> opponents = game.getOpponents(source.getControllerId());
                int random = RandomUtil.nextInt(opponents.size());
                Iterator<UUID> iterator = opponents.iterator();
                for (int i = 0; i < random; i++) {
                    iterator.next();
                }
                UUID opponentId = iterator.next();
                Player opponent = game.getPlayer(opponentId);
                if (opponent != null) {
                    game.informPlayers(opponent.getLogName() + " was chosen at random.");
                    opponent.damage(damage, source.getSourceId(), game, false, true);
                }
            }
            return true;
        }
        return false;
    }
}
