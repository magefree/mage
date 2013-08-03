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
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.counters.common.DevotionCounter;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public class PiousKitsune extends CardImpl<PiousKitsune> {

    public PiousKitsune(UUID ownerId) {
        super(ownerId, 38, "Pious Kitsune", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{2}{W}");
        this.expansionSetCode = "CHK";
        this.subtype.add("Fox");
        this.subtype.add("Cleric");

        this.color.setWhite(true);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // At the beginning of your upkeep, put a devotion counter on Pious Kitsune. Then if a creature named Eight-and-a-Half-Tails is on the battlefield, you gain 1 life for each devotion counter on Pious Kitsune.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new PiousKitsuneEffect(), TargetController.YOU, false));
        // {tap}, Remove a devotion counter from Pious Kitsune: You gain 1 life.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new GainLifeEffect(1), new TapSourceCost());
        ability.addCost(new RemoveCountersSourceCost(new DevotionCounter()));
        this.addAbility(ability);
        
    }

    public PiousKitsune(final PiousKitsune card) {
        super(card);
    }

    @Override
    public PiousKitsune copy() {
        return new PiousKitsune(this);
    }
}

class PiousKitsuneEffect extends OneShotEffect<PiousKitsuneEffect> {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature named Eight-and-a-Half-Tails");
    static {
        filter.add(new NamePredicate("Eight-and-a-Half-Tails"));
    }

    public PiousKitsuneEffect() {
        super(Outcome.Benefit);
        this.staticText = "put a devotion counter on Pious Kitsune. Then if a creature named Eight-and-a-Half-Tails is on the battlefield, you gain 1 life for each devotion counter on Pious Kitsune";
    }

    public PiousKitsuneEffect(final PiousKitsuneEffect effect) {
        super(effect);
    }

    @Override
    public PiousKitsuneEffect copy() {
        return new PiousKitsuneEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        boolean result;
        result = new AddCountersSourceEffect(new DevotionCounter()).apply(game, source);
        if (game.getBattlefield().count(filter, source.getSourceId(), source.getControllerId(), game) > 0) {
            Permanent permanent = game.getPermanent(source.getSourceId());
            if (permanent != null) {
                int life = permanent.getCounters().getCount(CounterType.DEVOTION);
                if (life > 0) {
                    Player controller = game.getPlayer(source.getControllerId());
                    if (controller != null) {
                        controller.gainLife(life, game);
                    }
                }
            }
        }
        return result;
    }
}
