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
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.PreventionEffectImpl;
import mage.abilities.effects.common.PreventDamageToTargetEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.TargetSource;
import mage.target.common.TargetCardInOpponentsGraveyard;
import mage.target.common.TargetCreatureOrPlayer;
import mage.target.targetpointer.SecondTargetPointer;

/**
 *
 * @author L_J
 */
public class ShieldmageAdvocate extends CardImpl {

    public ShieldmageAdvocate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // {tap}: Return target card from an opponent's graveyard to their hand. Prevent all damage that would be dealt to target creature or player this turn by a source of your choice.
        Effect effect = new ReturnFromGraveyardToHandTargetEffect();
        effect.setText("Return target card from an opponent's graveyard to their hand");
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, effect, new TapSourceCost());

        effect = new ShieldmageAdvocateEffect();
        effect.setTargetPointer(new SecondTargetPointer());
        ability.addEffect(effect);
        ability.addTarget(new TargetCardInOpponentsGraveyard(1, 1, new FilterCard(), true));
        ability.addTarget(new TargetCreatureOrPlayer());
        this.addAbility(ability);
    }

    public ShieldmageAdvocate(final ShieldmageAdvocate card) {
        super(card);
    }

    @Override
    public ShieldmageAdvocate copy() {
        return new ShieldmageAdvocate(this);
    }
}

class ShieldmageAdvocateEffect extends PreventionEffectImpl {

    protected final TargetSource targetSource;

    public ShieldmageAdvocateEffect() {
        super(Duration.EndOfTurn, Integer.MAX_VALUE, false);
        staticText = "Prevent all damage that would be dealt to target creature or player this turn by a source of your choice";
        this.targetSource = new TargetSource();
    }
    
    public ShieldmageAdvocateEffect(final ShieldmageAdvocateEffect effect) {
        super(effect);
        this.targetSource = effect.targetSource.copy();
    }
    
    @Override
    public ShieldmageAdvocateEffect copy() {
        return new ShieldmageAdvocateEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        this.targetSource.choose(Outcome.PreventDamage, source.getControllerId(), source.getSourceId(), game);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        preventDamageAction(event, source, game);
        return false;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (super.applies(event, source, game)) {
            if (event.getTargetId().equals(targetPointer.getFirst(game, source)) && event.getSourceId().equals(targetSource.getFirstTarget())) {
                return true;
            }
        }
        return false;
    }

}
