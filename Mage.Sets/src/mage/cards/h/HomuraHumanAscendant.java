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
package mage.cards.h;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.CantBlockAbility;
import mage.abilities.common.DiesTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.FlippedCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CopyTokenEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.Token;
import mage.players.Player;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public class HomuraHumanAscendant extends CardImpl {

    public HomuraHumanAscendant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{R}{R}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add("Human");
        this.subtype.add("Monk");

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);
        this.flipCard = true;
        this.flipCardName = "Homura's Essence";

        // Homura, Human Ascendant can't block.
        this.addAbility(new CantBlockAbility());
        // When Homura dies, return it to the battlefield flipped.
        this.addAbility(new DiesTriggeredAbility(new HomuraReturnFlippedSourceEffect(new HomurasEssence2())));
    }

    public HomuraHumanAscendant(final HomuraHumanAscendant card) {
        super(card);
    }

    @Override
    public HomuraHumanAscendant copy() {
        return new HomuraHumanAscendant(this);
    }
}

class HomuraReturnFlippedSourceEffect extends OneShotEffect {

    private final Token flipToken;

    public HomuraReturnFlippedSourceEffect(Token flipToken) {
        super(Outcome.BecomeCreature);
        this.flipToken = flipToken;
        staticText = "return it to the battlefield flipped";
    }

    public HomuraReturnFlippedSourceEffect(final HomuraReturnFlippedSourceEffect effect) {
        super(effect);
        this.flipToken = effect.flipToken;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card sourceCard = game.getCard(source.getSourceId());
        Player controller = game.getPlayer(source.getControllerId());
        if (sourceCard != null && controller != null && game.getState().getZone(source.getSourceId()) == Zone.GRAVEYARD) {
            ContinuousEffect effect = new ConditionalContinuousEffect(new CopyTokenEffect(flipToken), FlippedCondition.getInstance(), "");
            game.addEffect(effect, source);
            controller.moveCards(sourceCard, Zone.BATTLEFIELD, source, game);
            Permanent permanent = game.getPermanent(source.getSourceId());
            if (permanent != null) {
                permanent.flip(game);  // not complete correct because it should enter the battlefield flipped
            }
            return true;
        }
        return false;
    }

    @Override
    public HomuraReturnFlippedSourceEffect copy() {
        return new HomuraReturnFlippedSourceEffect(this);
    }

}

class HomurasEssence2 extends Token {

    HomurasEssence2() {
        super("Homura's Essence", "");
        addSuperType(SuperType.LEGENDARY);
        cardType.add(CardType.ENCHANTMENT);
        color.setRed(true);
        // Creatures you control get +2/+2 and have flying and "{R}: This creature gets +1/+0 until end of turn."
        FilterCreaturePermanent filter = new FilterCreaturePermanent();
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostControlledEffect(2, 2, Duration.WhileOnBattlefield, filter, false));
        Effect effect = new GainAbilityControlledEffect(FlyingAbility.getInstance(), Duration.WhileOnBattlefield, filter);
        effect.setText("and have flying");
        ability.addEffect(effect);
        Ability gainedAbility = new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostSourceEffect(1, 0, Duration.EndOfTurn), new ManaCostsImpl("{R}"));
        effect = new GainAbilityControlledEffect(gainedAbility, Duration.WhileOnBattlefield, filter);
        effect.setText("and \"{R}: This creature gets +1/+0 until end of turn.\"");
        ability.addEffect(effect);
        this.addAbility(ability);
    }
}
