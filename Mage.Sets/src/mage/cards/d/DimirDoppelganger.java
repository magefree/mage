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
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CopyEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentCard;
import mage.players.Player;
import mage.target.common.TargetCardInGraveyard;
import mage.util.functions.ApplyToPermanent;

/**
 *
 * @author jeffwadsworth
 */
public class DimirDoppelganger extends CardImpl {

    public DimirDoppelganger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}{B}");
        this.subtype.add("Shapeshifter");

        this.power = new MageInt(0);
        this.toughness = new MageInt(2);

        // {1}{U}{B}: Exile target creature card from a graveyard. Dimir Doppelganger becomes a copy of that card and gains this ability.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DimirDoppelgangerEffect(), new ManaCostsImpl("{1}{U}{B}"));
        ability.addTarget(new TargetCardInGraveyard(new FilterCreatureCard("creature card in a graveyard")));
        this.addAbility(ability);

    }

    public DimirDoppelganger(final DimirDoppelganger card) {
        super(card);
    }

    @Override
    public DimirDoppelganger copy() {
        return new DimirDoppelganger(this);
    }
}

class DimirDoppelgangerEffect extends OneShotEffect {

    DimirDoppelgangerEffect() {
        super(Outcome.Copy);
        staticText = "Exile target creature card from a graveyard. {this} becomes a copy of that card and gains this ability";
    }

    DimirDoppelgangerEffect(final DimirDoppelgangerEffect effect) {
        super(effect);
    }

    @Override
    public DimirDoppelgangerEffect copy() {
        return new DimirDoppelgangerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent dimirDoppelganger = game.getPermanent(source.getSourceId());
        Permanent newBluePrint = null;
        if (controller != null
                && dimirDoppelganger != null) {
            Card copyFromCard = game.getCard(source.getFirstTarget());
            if (copyFromCard != null) {
                Cards cardsToExile = new CardsImpl();
                cardsToExile.add(copyFromCard);
                controller.moveCards(cardsToExile, Zone.EXILED, source, game);
                newBluePrint = new PermanentCard((Card) copyFromCard, source.getControllerId(), game);
                newBluePrint.assignNewId();
                ApplyToPermanent applier = new DimirDoppelgangerApplier();
                applier.apply(game, newBluePrint, source);
                CopyEffect copyEffect = new CopyEffect(Duration.Custom, newBluePrint, dimirDoppelganger.getId());
                copyEffect.newId();
                copyEffect.setApplier(applier);
                Ability newAbility = source.copy();
                copyEffect.init(newAbility, game);
                game.addEffect(copyEffect, newAbility);
            }
            return true;
        }
        return false;
    }
}

class DimirDoppelgangerApplier extends ApplyToPermanent {

    @Override
    public boolean apply(Game game, Permanent permanent, Ability source) {
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DimirDoppelgangerEffect(), new ManaCostsImpl("{1}{U}{B}"));
        ability.addTarget(new TargetCardInGraveyard(new FilterCreatureCard("creature card in a graveyard")));
        permanent.getAbilities().add(ability);
        return true;
    }

    @Override
    public boolean apply(Game game, MageObject mageObject, Ability source) {
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DimirDoppelgangerEffect(), new ManaCostsImpl("{1}{U}{B}"));
        ability.addTarget(new TargetCardInGraveyard(new FilterCreatureCard("creature card in a graveyard")));
        mageObject.getAbilities().add(ability);
        return true;
    }
}
