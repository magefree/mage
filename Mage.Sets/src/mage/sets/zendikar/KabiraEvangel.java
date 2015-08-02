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
package mage.sets.zendikar;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.choices.ChoiceColor;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardIdPredicate;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author jeffwadsworth
 */
public class KabiraEvangel extends CardImpl {

    public KabiraEvangel(UUID ownerId) {
        super(ownerId, 15, "Kabira Evangel", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{2}{W}");
        this.expansionSetCode = "ZEN";
        this.subtype.add("Human");
        this.subtype.add("Cleric");
        this.subtype.add("Ally");

        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        FilterPermanent filter = new FilterPermanent(getName() + " or another Ally");
        filter.add(Predicates.or(
        new CardIdPredicate(this.getId()),
        new SubtypePredicate("Ally")));

        // Whenever Kabira Evangel or another Ally enters the battlefield under your control, you may choose a color. If you do, Allies you control gain protection from the chosen color until end of turn.
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(Zone.BATTLEFIELD, new KabiraEvangelChooseColorEffect(), filter, true));
    }

    public KabiraEvangel(final KabiraEvangel card) {
        super(card);
    }

    @Override
    public KabiraEvangel copy() {
        return new KabiraEvangel(this);
    }
}



class KabiraEvangelChooseColorEffect extends OneShotEffect {

    private static final FilterCreaturePermanent filter1 = new FilterCreaturePermanent();

    static {
        filter1.add(new ControllerPredicate(TargetController.YOU));
        filter1.add(new SubtypePredicate("Ally"));
    }

    public KabiraEvangelChooseColorEffect() {
        super(Outcome.Benefit);
        staticText = "choose a color.  All Allies you control gain protection from the chosen color until end of turn";
    }

    public KabiraEvangelChooseColorEffect(final KabiraEvangelChooseColorEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent sourceObject = game.getPermanent(source.getSourceId());
        if (sourceObject != null && controller != null) {
            ChoiceColor choice = new ChoiceColor();
            while (!choice.isChosen()) {
                controller.choose(outcome, choice, game);
                if (!controller.canRespond()) {
                    return false;
                }
            }
            if (choice.getColor() == null) {
                return false;
            }
            game.informPlayers(sourceObject.getName() + ": " + controller.getLogName() + " has chosen " + choice.getChoice());
            FilterCard filterColor = new FilterCard();
            filterColor.add(new ColorPredicate(choice.getColor()));
            filterColor.setMessage(choice.getChoice());
            ContinuousEffect effect = new GainAbilityAllEffect(new ProtectionAbility(new FilterCard(filterColor)), Duration.EndOfTurn, filter1);
            game.addEffect(effect, source);
            return true;
        }
        return false;
    }

    @Override
    public KabiraEvangelChooseColorEffect copy() {
        return new KabiraEvangelChooseColorEffect(this);
    }
}
