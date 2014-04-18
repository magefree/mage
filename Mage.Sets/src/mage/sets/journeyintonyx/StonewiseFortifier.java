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
package mage.sets.journeyintonyx;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.PreventionEffectImpl;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.constants.Zone;
import static mage.filter.predicate.permanent.ControllerControlsIslandPredicate.filter;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public class StonewiseFortifier extends CardImpl<StonewiseFortifier> {

    public StonewiseFortifier(UUID ownerId) {
        super(ownerId, 27, "Stonewise Fortifier", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{1}{W}");
        this.expansionSetCode = "JOU";
        this.subtype.add("Human");
        this.subtype.add("Wizard");

        this.color.setWhite(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {4}{W}: Prevent all damage that would be dealt to Stonewise Fortifier by target creature this turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new StonewiseFortifierPreventAllDamageToEffect(), new ManaCostsImpl("{4}{W}"));
        ability.addTarget(new TargetCreaturePermanent(true));
        this.addAbility(ability);
    }

    public StonewiseFortifier(final StonewiseFortifier card) {
        super(card);
    }

    @Override
    public StonewiseFortifier copy() {
        return new StonewiseFortifier(this);
    }
}

class StonewiseFortifierPreventAllDamageToEffect extends PreventionEffectImpl<StonewiseFortifierPreventAllDamageToEffect> {

    public StonewiseFortifierPreventAllDamageToEffect() {
        super(Duration.EndOfTurn);
        staticText = "Prevent all damage that would be dealt to {this} by target creature this turn";
    }

    public StonewiseFortifierPreventAllDamageToEffect(final StonewiseFortifierPreventAllDamageToEffect effect) {
        super(effect);
    }

    @Override
    public StonewiseFortifierPreventAllDamageToEffect copy() {
        return new StonewiseFortifierPreventAllDamageToEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        GameEvent preventEvent = new GameEvent(GameEvent.EventType.PREVENT_DAMAGE, source.getFirstTarget(), source.getSourceId(), source.getControllerId(), event.getAmount(), false);
        if (!game.replaceEvent(preventEvent)) {
            int damage = event.getAmount();
            event.setAmount(0);
            game.fireEvent(GameEvent.getEvent(GameEvent.EventType.PREVENTED_DAMAGE, source.getFirstTarget(), source.getSourceId(), source.getControllerId(), damage));
        }
        return false;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (super.applies(event, source, game) && event.getTargetId().equals(source.getSourceId())) {
            if (event.getSourceId().equals(targetPointer.getFirst(game, source))) {
                return true;
            }
        }
        return false;
    }

}
