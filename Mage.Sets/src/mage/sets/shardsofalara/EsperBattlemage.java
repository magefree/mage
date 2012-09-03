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
package mage.sets.shardsofalara;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.ColoredManaSymbol;
import mage.Constants.Duration;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ColoredManaCost;
import mage.abilities.effects.PreventionEffectImpl;
import mage.abilities.effects.common.continious.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author North
 */
public class EsperBattlemage extends CardImpl<EsperBattlemage> {

    public EsperBattlemage(UUID ownerId) {
        super(ownerId, 40, "Esper Battlemage", Rarity.UNCOMMON, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{2}{U}");
        this.expansionSetCode = "ALA";
        this.subtype.add("Human");
        this.subtype.add("Wizard");

        this.color.setBlue(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
        // {W}, {tap}: Prevent the next 2 damage that would be dealt to you this turn.
        SimpleActivatedAbility ability = new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new EsperBattlemageEffect(),
                new ColoredManaCost(ColoredManaSymbol.W));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
        // {B}, {tap}: Target creature gets -1/-1 until end of turn.
        ability = new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new BoostTargetEffect(-1, -1, Duration.EndOfTurn),
                new ColoredManaCost(ColoredManaSymbol.B));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    public EsperBattlemage(final EsperBattlemage card) {
        super(card);
    }

    @Override
    public EsperBattlemage copy() {
        return new EsperBattlemage(this);
    }
}

class EsperBattlemageEffect extends PreventionEffectImpl<EsperBattlemageEffect> {

    private int amount = 2;

    public EsperBattlemageEffect() {
        super(Duration.EndOfTurn);
        this.staticText = "Prevent the next 2 damage that would be dealt to you this turn";
    }

    public EsperBattlemageEffect(final EsperBattlemageEffect effect) {
        super(effect);
        this.amount = effect.amount;
    }

    @Override
    public EsperBattlemageEffect copy() {
        return new EsperBattlemageEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        GameEvent preventEvent = new GameEvent(GameEvent.EventType.PREVENT_DAMAGE,
                source.getControllerId(), source.getId(), source.getControllerId(), event.getAmount(), false);
        if (!game.replaceEvent(preventEvent)) {
            int damage = event.getAmount();
            if (damage >= this.amount) {
                event.setAmount(damage - this.amount);
                damage = this.amount;
                this.used = true;
            } else {
                event.setAmount(0);
                this.amount -= damage;
            }
            game.fireEvent(GameEvent.getEvent(GameEvent.EventType.PREVENTED_DAMAGE,
                    source.getControllerId(), source.getId(), source.getControllerId(), damage));
        }
        return false;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (!this.used && super.applies(event, source, game) && event.getTargetId().equals(source.getControllerId())) {
            return true;
        }
        return false;
    }
}
