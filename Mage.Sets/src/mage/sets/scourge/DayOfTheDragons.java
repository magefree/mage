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
package mage.sets.scourge;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.Constants.TargetController;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.ExileZone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.Token;

/**
 *
 * @author jeffwadsworth
 */
public class DayOfTheDragons extends CardImpl<DayOfTheDragons> {

    public DayOfTheDragons(UUID ownerId) {
        super(ownerId, 31, "Day of the Dragons", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{4}{U}{U}{U}");
        this.expansionSetCode = "SCG";

        this.color.setBlue(true);

        // When Day of the Dragons enters the battlefield, exile all creatures you control. Then put that many 5/5 red Dragon creature tokens with flying onto the battlefield.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DayOfTheDragonsEntersEffect(), false));

        // When Day of the Dragons leaves the battlefield, sacrifice all Dragons you control. Then return the exiled cards to the battlefield under your control.
        this.addAbility(new LeavesBattlefieldTriggeredAbility(new DayOfTheDragonsLeavesEffect(), false));
    }

    public DayOfTheDragons(final DayOfTheDragons card) {
        super(card);
    }

    @Override
    public DayOfTheDragons copy() {
        return new DayOfTheDragons(this);
    }
}

class DayOfTheDragonsEntersEffect extends OneShotEffect<DayOfTheDragonsEntersEffect> {

    private static final FilterPermanent filter = new FilterPermanent("all creatures you control");

    static {
        filter.add(new ControllerPredicate(TargetController.YOU));
        filter.add(new CardTypePredicate(CardType.CREATURE));
    }

    public DayOfTheDragonsEntersEffect() {
        super(Constants.Outcome.Benefit);
        staticText = "exile all creatures you control. Then put that many 5/5 red Dragon creature tokens with flying onto the battlefield";
    }

    public DayOfTheDragonsEntersEffect(final DayOfTheDragonsEntersEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        UUID exileId = source.getSourceId();
        int creaturesExiled = 0;
        if (exileId != null) {
            for (Permanent creature : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), game)) {
                if (creature != null) {
                    if (creature.moveToExile(exileId, "Day of the Dragons", source.getId(), game)) {
                        creaturesExiled++;
                    }
                }
            }
            DragonToken token = new DragonToken();
            token.putOntoBattlefield(creaturesExiled, game, source.getId(), source.getControllerId());
            return true;
        }
        return false;
    }

    @Override
    public DayOfTheDragonsEntersEffect copy() {
        return new DayOfTheDragonsEntersEffect(this);
    }
}

class DayOfTheDragonsLeavesEffect extends OneShotEffect<DayOfTheDragonsLeavesEffect> {

    private static final FilterPermanent filter = new FilterPermanent("all Dragons you control");

    static {
        filter.add(new ControllerPredicate(TargetController.YOU));
        filter.add(new SubtypePredicate("Dragon"));
    }

    public DayOfTheDragonsLeavesEffect() {
        super(Constants.Outcome.Neutral);
        staticText = "sacrifice all Dragons you control. Then return the exiled cards to the battlefield under your control";
    }

    public DayOfTheDragonsLeavesEffect(final DayOfTheDragonsLeavesEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        UUID exileId = source.getSourceId();
        for (Permanent dragon : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), game)) {
            if (dragon != null) {
                dragon.sacrifice(source.getId(), game);
            }
        }
        ExileZone exile = game.getExile().getExileZone(exileId);
        if (exile != null) {
            exile = exile.copy();
            for (UUID cardId : exile) {
                Card card = game.getCard(cardId);
                card.putOntoBattlefield(game, Constants.Zone.EXILED, source.getId(), source.getControllerId());
            }
            game.getExile().getExileZone(exileId).clear();
            return true;
        }
        return false;
    }

    @Override
    public DayOfTheDragonsLeavesEffect copy() {
        return new DayOfTheDragonsLeavesEffect(this);
    }
}

class DragonToken extends Token {

    public DragonToken() {
        super("Dragon", "5/5 red Dragon creature token with flying");
        cardType.add(CardType.CREATURE);
        color = ObjectColor.RED;
        subtype.add("Dragon");
        power = new MageInt(5);
        toughness = new MageInt(5);
        addAbility(FlyingAbility.getInstance());
    }
}