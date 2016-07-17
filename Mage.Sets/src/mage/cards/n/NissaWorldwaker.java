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
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.PlanswalkerEntersWithLoyalityCountersAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureTargetEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterBasicLandCard;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.Token;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetLandPermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */
public class NissaWorldwaker extends CardImpl {

    private static final FilterLandPermanent filter = new FilterLandPermanent("land you control");
    private static final FilterPermanent filterForest = new FilterPermanent("Forest", "Forest");

    static {
        filter.add(new ControllerPredicate(TargetController.YOU));
    }

    public NissaWorldwaker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.PLANESWALKER},"{3}{G}{G}");
        this.subtype.add("Nissa");

        this.addAbility(new PlanswalkerEntersWithLoyalityCountersAbility(3));

        // +1: Target land you control becomes a 4/4 Elemental creature with trample.  It's still a land.
        LoyaltyAbility ability = new LoyaltyAbility(new BecomesCreatureTargetEffect(new NissaWorldwakerToken(), false, true, Duration.Custom), 1);
        ability.addTarget(new TargetLandPermanent(filter));
        this.addAbility(ability);

        // +1: Untap up to four target Forests.
        ability = new LoyaltyAbility(new UntapTargetEffect(), 1);
        ability.addTarget(new TargetPermanent(0, 4, filterForest, false));
        this.addAbility(ability);

        // -7: Search your library for any number of basic land cards, put them onto the battlefield, then shuffle your library.  Those lands become 4/4 Elemental creatures with trample.  They're still lands.
        this.addAbility(new LoyaltyAbility(new NissaWorldwakerSearchEffect(), -7));
    }

    public NissaWorldwaker(final NissaWorldwaker card) {
        super(card);
    }

    @Override
    public NissaWorldwaker copy() {
        return new NissaWorldwaker(this);
    }
}

class NissaWorldwakerSearchEffect extends OneShotEffect {

    public NissaWorldwakerSearchEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "Search your library for any number of basic land cards, put them onto the battlefield, then shuffle your library.  Those lands become 4/4 Elemental creatures with trample.  They're still lands";
    }

    public NissaWorldwakerSearchEffect(final NissaWorldwakerSearchEffect effect) {
        super(effect);
    }

    @Override
    public NissaWorldwakerSearchEffect copy() {
        return new NissaWorldwakerSearchEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        TargetCardInLibrary target = new TargetCardInLibrary(0, Integer.MAX_VALUE, new FilterBasicLandCard());
        if (controller.searchLibrary(target, game)) {
            if (target.getTargets().size() > 0) {
                for (UUID cardId : target.getTargets()) {
                    Card card = controller.getLibrary().getCard(cardId, game);
                    if (card != null) {
                        if (controller.moveCards(card, Zone.BATTLEFIELD, source, game)) {
                            Permanent land = game.getPermanent(card.getId());
                            if (land != null) {
                                ContinuousEffect effect = new BecomesCreatureTargetEffect(new NissaWorldwakerToken(), false, true, Duration.Custom);
                                effect.setTargetPointer(new FixedTarget(land, game));
                                game.addEffect(effect, source);

                            }
                        }
                    }
                }
            }
        }
        controller.shuffleLibrary(source, game);
        return true;
    }
}

class NissaWorldwakerToken extends Token {

    public NissaWorldwakerToken() {
        super("", "4/4 Elemental creature with trample");
        this.cardType.add(CardType.CREATURE);

        this.subtype.add("Elemental");
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);
        this.addAbility(TrampleAbility.getInstance());
    }
}
