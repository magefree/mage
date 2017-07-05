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
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfPreCombatMainTriggeredAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.EmptyToken;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

/**
 *
 * @author jeffwadsworth
 */
public class GodPharaohsGift extends CardImpl {

    public GodPharaohsGift(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{7}");

        // At the beginning of combat on your turn, you may exile a creature card from your graveyard. If you do, create a token that's a copy of that card, except it's a 4/4 black Zombie. It gains haste until end of turn.
        this.addAbility(new BeginningOfPreCombatMainTriggeredAbility(Zone.BATTLEFIELD, new GodPharaohsGiftEffect(), TargetController.YOU, true, false));

    }

    public GodPharaohsGift(final GodPharaohsGift card) {
        super(card);
    }

    @Override
    public GodPharaohsGift copy() {
        return new GodPharaohsGift(this);
    }
}

class GodPharaohsGiftEffect extends OneShotEffect {

    private static final FilterCreatureCard filter = new FilterCreatureCard("creature card from your graveyard");
    private UUID exileId = UUID.randomUUID();

    public GodPharaohsGiftEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "you may exile a creature card from your graveyard. If you do, create a token that's a copy of that card, except it's a 4/4 black Zombie. It gains haste until end of turn";
    }

    public GodPharaohsGiftEffect(final GodPharaohsGiftEffect effect) {
        super(effect);
    }

    @Override
    public GodPharaohsGiftEffect copy() {
        return new GodPharaohsGiftEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        TargetCardInYourGraveyard target = new TargetCardInYourGraveyard(filter);
        target.setNotTarget(true);
        if (controller != null
                && !controller.getGraveyard().getCards(filter, game).isEmpty()
                && controller.choose(Outcome.PutCreatureInPlay, target, source.getId(), game)) {
            Card cardChosen = game.getCard(target.getFirstTarget());
            if (cardChosen != null
                    && cardChosen.moveToExile(exileId, "God-Pharaoh's Gift", source.getId(), game)) {
                EmptyToken token = new EmptyToken();
                CardUtil.copyTo(token).from(cardChosen);
                token.getPower().modifyBaseValue(4);
                token.getToughness().modifyBaseValue(4);
                token.getColor(game).setColor(ObjectColor.BLACK);
                token.getSubtype(game).clear();
                token.getSubtype(game).add("Zombie");
                if (token.putOntoBattlefield(1, game, source.getSourceId(), source.getControllerId())) {
                    Permanent tokenPermanent = game.getPermanent(token.getLastAddedToken());
                    if (tokenPermanent != null) {
                        ContinuousEffect effect = new GainAbilityTargetEffect(HasteAbility.getInstance(), Duration.EndOfTurn);
                        effect.setTargetPointer(new FixedTarget(tokenPermanent.getId()));
                        game.addEffect(effect, source);
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
