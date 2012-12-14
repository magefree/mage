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
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Duration;
import mage.Constants.Rarity;
import mage.Constants.TargetController;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continious.GainAbilityControlledEffect;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.choices.ChoiceColor;
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
public class KabiraEvangel extends CardImpl<KabiraEvangel> {

    public KabiraEvangel(UUID ownerId) {
        super(ownerId, 15, "Kabira Evangel", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{2}{W}");
        this.expansionSetCode = "ZEN";
        this.subtype.add("Human");
        this.subtype.add("Cleric");
        this.subtype.add("Ally");

        this.color.setWhite(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        FilterPermanent filter = new FilterPermanent("Kabira Evangel or another Ally");
        filter.add(Predicates.or(
        new CardIdPredicate(this.getId()),
        new SubtypePredicate("Ally")));

        // Whenever Kabira Evangel or another Ally enters the battlefield under your control, you may choose a color. If you do, Allies you control gain protection from the chosen color until end of turn.
        this.addAbility(new EntersBattlefieldAllTriggeredAbility(Constants.Zone.BATTLEFIELD, new ChooseColorEffect(), filter, true));
    }

    public KabiraEvangel(final KabiraEvangel card) {
        super(card);
    }

    @Override
    public KabiraEvangel copy() {
        return new KabiraEvangel(this);
    }
}

class ChooseColorEffect extends OneShotEffect<ChooseColorEffect> {

    public ChooseColorEffect() {
        super(Constants.Outcome.Benefit);
        staticText = "choose a color.  All Allies you control gain protection from the chosen color until end of turn";
    }

    public ChooseColorEffect(final ChooseColorEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent perm = game.getPermanent(source.getSourceId());
        if (player != null && perm != null) {
            ChoiceColor colorChoice = new ChoiceColor();
            if (player.choose(Constants.Outcome.Benefit, colorChoice, game)) {
                game.informPlayers(perm.getName() + ": " + player.getName() + " has chosen " + colorChoice.getChoice());
                game.getState().setValue(perm.getId() + "_color", colorChoice.getColor());
                game.addEffect(new GainProtectionFromChosenColorEffect(), source);
            }
        }
        return false;
    }

    @Override
    public ChooseColorEffect copy() {
        return new ChooseColorEffect(this);
    }
}

class GainProtectionFromChosenColorEffect extends GainAbilityControlledEffect {

    private static final FilterCreaturePermanent filter1 = new FilterCreaturePermanent();

    static {
        filter1.add(new ControllerPredicate(TargetController.YOU));
        filter1.add(new SubtypePredicate("Ally"));
    }
    FilterCard filter2;

    public GainProtectionFromChosenColorEffect() {
        super(new ProtectionAbility(new FilterCard()), Duration.EndOfTurn, filter1);
        filter2 = (FilterCard) ((ProtectionAbility) getFirstAbility()).getFilter();
    }

    public GainProtectionFromChosenColorEffect(final GainProtectionFromChosenColorEffect effect) {
        super(effect);
        this.filter2 = effect.filter2.copy();
    }

    @Override
    public GainProtectionFromChosenColorEffect copy() {
        return new GainProtectionFromChosenColorEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        ObjectColor chosenColor = (ObjectColor) game.getState().getValue(source.getSourceId() + "_color");
        filter2.add(new ColorPredicate(chosenColor));
        filter2.setMessage(chosenColor.getDescription());
        setAbility(new ProtectionAbility(new FilterCard(filter2)));
        return super.apply(game, source);
    }
}