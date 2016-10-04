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
package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.MadnessAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInHand;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public class NightshadeAssassin extends CardImpl {

    public NightshadeAssassin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}{B}");
        this.subtype.add("Human");
        this.subtype.add("Assassin");
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // When Nightshade Assassin enters the battlefield, you may reveal X black cards in your hand. If you do, target creature gets -X/-X until end of turn.
        Ability ability = new EntersBattlefieldTriggeredAbility(new NightshadeAssassinEffect(), true);
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

        // Madness {1}{B}
        this.addAbility(new MadnessAbility(this, new ManaCostsImpl("{1}{B}")));
    }

    public NightshadeAssassin(final NightshadeAssassin card) {
        super(card);
    }

    @Override
    public NightshadeAssassin copy() {
        return new NightshadeAssassin(this);
    }
}

class NightshadeAssassinEffect extends OneShotEffect {

    public NightshadeAssassinEffect() {
        super(Outcome.UnboostCreature);
        staticText = "you may reveal X black cards in your hand. If you do, target creature gets -X/-X until end of turn";
    }

    public NightshadeAssassinEffect(final NightshadeAssassinEffect effect) {
        super(effect);
    }

    @Override
    public NightshadeAssassinEffect copy() {
        return new NightshadeAssassinEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (controller == null || sourceObject == null) {
            return false;
        }
        FilterCard filter = new FilterCard();
        filter.add(new ColorPredicate(ObjectColor.BLACK));
        int blackCards = controller.getHand().count(filter, source.getSourceId(), source.getControllerId(), game);
        int cardsToReveal = controller.getAmount(0, blackCards, "Reveal how many black cards?", game);
        game.informPlayers(controller.getLogName() + " chooses to reveal " + cardsToReveal + " black cards.");
        if (cardsToReveal > 0) {
            TargetCardInHand target = new TargetCardInHand(cardsToReveal, cardsToReveal, filter);
            if (controller.choose(Outcome.Benefit, target, source.getSourceId(), game)) {
                controller.revealCards(sourceObject.getIdName(), new CardsImpl(target.getTargets()), game);
                int unboost = target.getTargets().size() * -1;
                ContinuousEffect effect = new BoostTargetEffect(unboost, unboost, Duration.EndOfTurn);
                effect.setTargetPointer(getTargetPointer());
                game.addEffect(effect, source);
            }
        }
        return true;
    }
}
