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
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.RedirectionEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author LevelX2
 */
public class Glarecaster extends CardImpl {

    public Glarecaster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{W}{W}");

        this.subtype.add(SubType.BIRD);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // {5}{W}: The next time damage would be dealt to Glarecaster and/or you this turn, that damage is dealt to any target instead.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new GlarecasterEffect(), new ManaCostsImpl("{5}{W}"));
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    public Glarecaster(final Glarecaster card) {
        super(card);
    }

    @Override
    public Glarecaster copy() {
        return new Glarecaster(this);
    }
}

/**
 * 10/4/2004 If both you and this card would be dealt damage at the same time,
 * or if either you or this card would be dealt damage from multiple sources at
 * the same time, the redirection will apply to both chunks of damage.
 *
 * @author LevelX2
 */
class GlarecasterEffect extends RedirectionEffect {

    protected MageObjectReference redirectToObject;

    public GlarecasterEffect() {
        super(Duration.EndOfTurn, Integer.MAX_VALUE, UsageType.ONE_USAGE_AT_THE_SAME_TIME);
        staticText = "The next time damage would be dealt to {this} and/or you this turn, that damage is dealt to any target instead";
    }

    public GlarecasterEffect(final GlarecasterEffect effect) {
        super(effect);
        this.redirectToObject = effect.redirectToObject;
    }

    @Override
    public GlarecasterEffect copy() {
        return new GlarecasterEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        redirectToObject = new MageObjectReference(source.getTargets().get(0).getFirstTarget(), game);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == EventType.DAMAGE_CREATURE || event.getType() == EventType.DAMAGE_PLAYER;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getTargetId().equals(source.getSourceId())
                || event.getTargetId().equals(source.getControllerId())) {
            if (redirectToObject.equals(new MageObjectReference(source.getTargets().get(0).getFirstTarget(), game))) {
                redirectTarget = source.getTargets().get(0);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source
    ) {
        return true;
    }

}
