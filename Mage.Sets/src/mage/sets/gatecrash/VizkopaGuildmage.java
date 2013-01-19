/*
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
package mage.sets.gatecrash;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Duration;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.continious.GainAbilitySourceEffect;
import mage.abilities.effects.common.continious.GainAbilityTargetEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;
import mage.target.common.TargetNonlandPermanent;

/**
 *
 * @author LevelX2
 */
public class VizkopaGuildmage extends CardImpl<VizkopaGuildmage> {

    public VizkopaGuildmage(UUID ownerId) {
        super(ownerId, 206, "Vizkopa Guildmage", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{W}{B}");
        this.expansionSetCode = "GTC";

        this.subtype.add("Human");
        this.subtype.add("Wizard");

        this.color.setWhite(true);
        this.color.setBlack(true);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // 1{W}{B}: Target creature gains lifelink until end of turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new GainAbilityTargetEffect(LifelinkAbility.getInstance(), Duration.EndOfTurn),new ManaCostsImpl("{1}{W}{B}"));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

        // 1{W}{B}: Whenever you gain life this turn, each opponent loses that much life.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new GainAbilitySourceEffect(new VizkopaGuildmageTriggeredAbility(), Duration.EndOfTurn), new ManaCostsImpl("{1}{W}{B}")));
    }

    public VizkopaGuildmage(final VizkopaGuildmage card) {
        super(card);
    }

    @Override
    public VizkopaGuildmage copy() {
        return new VizkopaGuildmage(this);
    }
}

class VizkopaGuildmageTriggeredAbility extends TriggeredAbilityImpl<VizkopaGuildmageTriggeredAbility> {


    public VizkopaGuildmageTriggeredAbility() {
        super(Zone.BATTLEFIELD, new OpponentsLoseLifeEffect());
    }

    public VizkopaGuildmageTriggeredAbility(final VizkopaGuildmageTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public VizkopaGuildmageTriggeredAbility copy() {
        return new VizkopaGuildmageTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.GAINED_LIFE && event.getTargetId().equals(controllerId)) {
            getEffects().get(0).setValue("amountLifeGained", new Integer(event.getAmount()));
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever you gain life this turn, " + super.getRule();
    }
}

class OpponentsLoseLifeEffect extends OneShotEffect<OpponentsLoseLifeEffect> {

    public OpponentsLoseLifeEffect() {
        super(Outcome.Damage);
        this.staticText = "each opponent loses that much life";
    }

    public OpponentsLoseLifeEffect(final OpponentsLoseLifeEffect effect) {
        super(effect);
    }

    @Override
    public OpponentsLoseLifeEffect copy() {
        return new OpponentsLoseLifeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Integer amountLifeGained = (Integer) this.getValue("amountLifeGained");
        if (amountLifeGained != null ) {
            for (UUID opponentId: game.getOpponents(source.getControllerId())) {
                Player opponent = game.getPlayer(opponentId);
                if (opponent != null) {
                    opponent.damage(amountLifeGained, source.getSourceId(), game, false, true);
                }
            }
        }
        return false;
    }
}
