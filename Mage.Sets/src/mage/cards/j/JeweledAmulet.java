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
package mage.cards.j;

import java.util.UUID;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.condition.common.SourceHasCounterCondition;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalActivatedAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ManaEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.util.CardUtil;

/**
 *
 * @author jeffwadsworth
 */
public class JeweledAmulet extends CardImpl {
    
    private static final String rule = "Put a charge counter on {this}. Note the type of mana spent to pay this activation cost. Activate this ability only if there are no charge counters on {this}";

    public JeweledAmulet(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{0}");

        // {1}, {tap}: Put a charge counter on Jeweled Amulet. Note the type of mana spent to pay this activation cost. Activate this ability only if there are no charge counters on Jeweled Amulet.
        ConditionalActivatedAbility ability = new ConditionalActivatedAbility(Zone.BATTLEFIELD, new JeweledAmuletAddCounterEffect(), new ManaCostsImpl("{1}"), new SourceHasCounterCondition(CounterType.CHARGE, 0), rule);
        ability.addEffect(new AddCountersSourceEffect(CounterType.CHARGE.createInstance(), true));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);

        // {tap}, Remove a charge counter from Jeweled Amulet: Add one mana of Jeweled Amulet's last noted type to your mana pool.
        Ability ability2 = new SimpleManaAbility(Zone.BATTLEFIELD, new JeweledAmuletAddManaEffect(), new TapSourceCost());
        ability2.addCost(new RemoveCountersSourceCost(CounterType.CHARGE.createInstance()));
        this.addAbility(ability2);

    }

    public JeweledAmulet(final JeweledAmulet card) {
        super(card);
    }

    @Override
    public JeweledAmulet copy() {
        return new JeweledAmulet(this);
    }
}

class JeweledAmuletAddCounterEffect extends OneShotEffect {
    
    private static String manaUsedString;

    public JeweledAmuletAddCounterEffect() {
        super(Outcome.Benefit);
        this.staticText = "Note the type of mana spent to pay this activation cost. Activate this ability only if there are no charge counters on {this}";
    }

    public JeweledAmuletAddCounterEffect(final JeweledAmuletAddCounterEffect effect) {
        super(effect);
    }

    @Override
    public JeweledAmuletAddCounterEffect copy() {
        return new JeweledAmuletAddCounterEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent jeweledAmulet = game.getPermanent(source.getSourceId());
        if (controller != null
                && jeweledAmulet != null) {
            game.getState().setValue("JeweledAmulet" + source.getSourceId().toString(), source.getManaCostsToPay().getUsedManaToPay()); //store the mana used to pay
            manaUsedString = source.getManaCostsToPay().getUsedManaToPay().toString();
            jeweledAmulet.addInfo("MANA USED", CardUtil.addToolTipMarkTags("Mana used last: " + manaUsedString), game);
            return true;
        }
        return false;
    }
}

class JeweledAmuletAddManaEffect extends ManaEffect {
    
    private static Mana storedMana;

    JeweledAmuletAddManaEffect() {
        super();
        staticText = "Add one mana of {this}'s last noted type to your mana pool";
    }

    JeweledAmuletAddManaEffect(JeweledAmuletAddManaEffect effect) {
        super(effect);
    }

    @Override
    public JeweledAmuletAddManaEffect copy() {
        return new JeweledAmuletAddManaEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent jeweledAmulet = game.getPermanent(source.getSourceId());
        Player controller = game.getPlayer(source.getControllerId());
        if (jeweledAmulet != null
                && controller != null) {
            storedMana = (Mana) game.getState().getValue("JeweledAmulet" + source.getSourceId().toString());
            if (storedMana != null) {
                checkToFirePossibleEvents(storedMana, game, source);
                controller.getManaPool().addMana(storedMana, game, source);
                return true;
            }
        }
        return false;
    }

    @Override
    public Mana getMana(Game game, Ability source) {
        return null;
    }

}
