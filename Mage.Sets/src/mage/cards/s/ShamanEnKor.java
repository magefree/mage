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
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.RedirectionEffect;
import mage.abilities.effects.common.RedirectDamageFromSourceToTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.TargetSource;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author emerald000
 */
public class ShamanEnKor extends CardImpl {

    public ShamanEnKor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{W}");
        this.subtype.add(SubType.KOR);
        this.subtype.add(SubType.CLERIC);
        this.subtype.add(SubType.SHAMAN);

        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // {0}: The next 1 damage that would be dealt to Shaman en-Kor this turn is dealt to target creature you control instead.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new RedirectDamageFromSourceToTargetEffect(Duration.EndOfTurn, 1, true), new GenericManaCost(0));
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);

        // {1}{W}: The next time a source of your choice would deal damage to target creature this turn, that damage is dealt to Shaman en-Kor instead.
        ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ShamanEnKorRedirectFromTargetEffect(), new ManaCostsImpl("{1}{W}"));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    public ShamanEnKor(final ShamanEnKor card) {
        super(card);
    }

    @Override
    public ShamanEnKor copy() {
        return new ShamanEnKor(this);
    }
}

class ShamanEnKorRedirectFromTargetEffect extends RedirectionEffect {

    protected MageObjectReference sourceObject;

    ShamanEnKorRedirectFromTargetEffect() {
        super(Duration.EndOfTurn, Integer.MAX_VALUE, true);
        staticText = "The next time a source of your choice would deal damage to target creature this turn, that damage is dealt to {this} instead";
    }

    ShamanEnKorRedirectFromTargetEffect(final ShamanEnKorRedirectFromTargetEffect effect) {
        super(effect);
        sourceObject = effect.sourceObject;
    }

    @Override
    public void init(Ability source, Game game) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            TargetSource target = new TargetSource();
            target.choose(Outcome.PreventDamage, player.getId(), source.getSourceId(), game);
            this.sourceObject = new MageObjectReference(target.getFirstTarget(), game);
        } else {
            discard();
        }
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == EventType.DAMAGE_CREATURE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (sourceObject.equals(new MageObjectReference(event.getSourceId(), game))) {
            redirectTarget = new TargetPermanent();
            redirectTarget.add(source.getSourceId(), game);
            return event.getTargetId().equals(getTargetPointer().getFirst(game, source));
        }
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public ShamanEnKorRedirectFromTargetEffect copy() {
        return new ShamanEnKorRedirectFromTargetEffect(this);
    }
}
