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
package mage.sets.iceage;

import java.util.List;
import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.CardImpl;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

/**
 *
 * @author Plopman
 */
public class WrathOfMaritLage extends CardImpl<WrathOfMaritLage> {


    
    public WrathOfMaritLage(UUID ownerId) {
        super(ownerId, 109, "Wrath of Marit Lage", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{3}{U}{U}");
        this.expansionSetCode = "ICE";

        this.color.setBlue(true);

        // When Wrath of Marit Lage enters the battlefield, tap all red creatures.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new TapAllEffect()));
        // Red creatures don't untap during their controllers' untap steps.
        this.addAbility(new SimpleStaticAbility(Constants.Zone.BATTLEFIELD, new DontUntapEffect()));
    }

    public WrathOfMaritLage(final WrathOfMaritLage card) {
        super(card);
    }

    @Override
    public WrathOfMaritLage copy() {
        return new WrathOfMaritLage(this);
    }
}

class TapAllEffect extends OneShotEffect<TapAllEffect> {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Red creature");
    static{
        filter.add(new ColorPredicate(ObjectColor.RED));
    }
    
    public TapAllEffect() {
        super(Outcome.Tap);
        staticText = "tap all red creatures";
    }

    public TapAllEffect(final TapAllEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {

        List<Permanent> creatures = game.getBattlefield().getActivePermanents(filter, source.getSourceId(), game);
        for (Permanent creature : creatures) {
            creature.tap(game);
        }
        return true;
    }

    @Override
    public TapAllEffect copy() {
        return new TapAllEffect(this);
    }
}


class DontUntapEffect extends ReplacementEffectImpl<DontUntapEffect> {


    public DontUntapEffect() {
        super(Constants.Duration.WhileOnBattlefield, Outcome.Detriment);
    }

    public DontUntapEffect(final DontUntapEffect effect) {
        super(effect);
    }

    @Override
    public DontUntapEffect copy() {
        return new DontUntapEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        used = false;
        return true;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Permanent creature = game.getPermanent(event.getTargetId());
        if (game.getTurn().getStepType() == Constants.PhaseStep.UNTAP &&  event.getType() == GameEvent.EventType.UNTAP
                && creature != null && creature.getCardType().contains(CardType.CREATURE) && creature.getColor().isRed() && creature.getControllerId() == event.getPlayerId()) {
            return true;
        }
        return false;
    }

    @Override
    public String getText(Mode mode) {
        return "Red creatures don't untap during their controllers' untap steps";
    }
    
}