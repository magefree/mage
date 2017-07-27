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
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public class GalvanicBombardment extends CardImpl {

    private static final FilterCard filter = new FilterCard("2 plus the number of cards named Galvanic Bombardment");

    static {
        filter.add(new NamePredicate("Galvanic Bombardment"));
    }

    public GalvanicBombardment(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{R}");

        // Galvanic Bombardment deals X damage to target creature, where X is 2 plus the number of cards named Galvanic Bombardment in your graveyard.
        Effect effect = new DamageTargetEffect(new GalvanicBombardmentCardsInControllerGraveyardCount(filter));
        effect.setText("{this} deals X damage to target creature, where X is 2 plus the number of cards named {source} in your graveyard");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    public GalvanicBombardment(final GalvanicBombardment card) {
        super(card);
    }

    @Override
    public GalvanicBombardment copy() {
        return new GalvanicBombardment(this);
    }
}

class GalvanicBombardmentCardsInControllerGraveyardCount implements DynamicValue {

    private final FilterCard filter;

    public GalvanicBombardmentCardsInControllerGraveyardCount(FilterCard filter) {
        this.filter = filter;
    }

    private GalvanicBombardmentCardsInControllerGraveyardCount(GalvanicBombardmentCardsInControllerGraveyardCount dynamicValue) {
        this.filter = dynamicValue.filter;
    }

    @Override
    public int calculate(Game game, Ability source, Effect effect) {
        int amount = 0;
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
                amount += controller.getGraveyard().count(filter, source.getSourceId(), source.getControllerId(), game);
            }
        return amount + 2;
    }

    @Override
    public GalvanicBombardmentCardsInControllerGraveyardCount copy() {
        return new GalvanicBombardmentCardsInControllerGraveyardCount(this);
    }

    @Override
    public String toString() {
        return "1";
    }

    @Override
    public String getMessage() {
        return filter.getMessage() + " in your graveyard";
    }
}
