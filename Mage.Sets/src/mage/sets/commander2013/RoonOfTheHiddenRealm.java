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
package mage.sets.commander2013;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.delayed.AtEndOfTurnDelayedTriggeredAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnFromExileEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public class RoonOfTheHiddenRealm extends CardImpl<RoonOfTheHiddenRealm> {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("another target creature");
    static {
        filter.add(new AnotherPredicate());
    }

    public RoonOfTheHiddenRealm(UUID ownerId) {
        super(ownerId, 206, "Roon of the Hidden Realm", Rarity.MYTHIC, new CardType[]{CardType.CREATURE}, "{2}{G}{W}{U}");
        this.expansionSetCode = "C13";
        this.supertype.add("Legendary");
        this.subtype.add("Rhino");
        this.subtype.add("Soldier");

        this.color.setBlue(true);
        this.color.setGreen(true);
        this.color.setWhite(true);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());
        // Trample
        this.addAbility(TrampleAbility.getInstance());
        // {2}, {tap}: Exile another target creature. Return that card to the battlefield under its owner's control at the beginning of the next end step.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new RoonOfTheHiddenRealmEffect(), new GenericManaCost(2));
        ability.addTarget(new TargetCreaturePermanent(filter, true));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);

    }

    public RoonOfTheHiddenRealm(final RoonOfTheHiddenRealm card) {
        super(card);
    }

    @Override
    public RoonOfTheHiddenRealm copy() {
        return new RoonOfTheHiddenRealm(this);
    }
}

class RoonOfTheHiddenRealmEffect extends OneShotEffect<RoonOfTheHiddenRealmEffect> {

    public RoonOfTheHiddenRealmEffect() {
        super(Outcome.Benefit);
        this.staticText = "Exile another target creature. Return that card to the battlefield under its owner's control at the beginning of the next end step";
    }

    public RoonOfTheHiddenRealmEffect(final RoonOfTheHiddenRealmEffect effect) {
        super(effect);
    }

    @Override
    public RoonOfTheHiddenRealmEffect copy() {
        return new RoonOfTheHiddenRealmEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (getTargetPointer().getFirst(game, source) != null) {
            Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
            Card card = game.getCard(getTargetPointer().getFirst(game, source));
            if (permanent != null) {
                if (permanent.moveToExile(source.getSourceId(), "Roon of the Hidden Realm", source.getSourceId(), game)) {
                    if (card != null) {
                        AtEndOfTurnDelayedTriggeredAbility delayedAbility = new AtEndOfTurnDelayedTriggeredAbility(new ReturnFromExileEffect(source.getSourceId(), Zone.BATTLEFIELD));
                        delayedAbility.setSourceId(source.getSourceId());
                        delayedAbility.setControllerId(card.getOwnerId());
                        game.addDelayedTriggeredAbility(delayedAbility);
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
