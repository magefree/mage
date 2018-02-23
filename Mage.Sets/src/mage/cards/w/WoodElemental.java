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
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.effects.common.continuous.SetPowerToughnessSourceEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author L_J
 */
public class WoodElemental extends CardImpl {

    public WoodElemental(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}");
        this.subtype.add(SubType.ELEMENTAL);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // As Wood Elemental enters the battlefield, sacrifice any number of untapped Forests.
        this.addAbility(new AsEntersBattlefieldAbility(new WoodElementalEffect()));

        // Wood Elemental's power and toughness are each equal to the number of Forests sacrificed as it entered the battlefield.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new InfoEffect("{this}'s power and toughness are each equal to the number of Forests sacrificed as it entered the battlefield")));
    }

    public WoodElemental(final WoodElemental card) {
        super(card);
    }

    @Override
    public WoodElemental copy() {
        return new WoodElemental(this);
    }
}

class WoodElementalEffect extends OneShotEffect {
    
    private static final FilterControlledPermanent filter = new FilterControlledPermanent("untapped Forests you control");
    
    static {
        filter.add(Predicates.not(new TappedPredicate()));
        filter.add(new SubtypePredicate(SubType.FOREST));
    }

    public WoodElementalEffect() {
        super(Outcome.Sacrifice);
        staticText = "sacrifice any number of untapped Forests";
    }

    public WoodElementalEffect(final WoodElementalEffect effect) {
        super(effect);
    }

    @Override
    public WoodElementalEffect copy() {
        return new WoodElementalEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Card sourceCard = game.getCard(source.getSourceId());
        if (controller != null && sourceCard != null) {
            Target target = new TargetControlledPermanent(0, Integer.MAX_VALUE, filter, true);
            if (target.canChoose(source.getSourceId(), source.getControllerId(), game) 
                    && controller.chooseTarget(Outcome.Detriment, target, source, game)) {
                if (!target.getTargets().isEmpty()) {
                    int sacrificedForests = target.getTargets().size();
                    game.informPlayers(controller.getLogName() + " sacrifices " + sacrificedForests + " untapped Forests for " + sourceCard.getLogName());
                    for (UUID targetId : target.getTargets()) {
                        Permanent targetPermanent = game.getPermanent(targetId);
                        if (targetPermanent != null) {
                            targetPermanent.sacrifice(source.getSourceId(), game);
                        }
                    }
                    game.addEffect(new SetPowerToughnessSourceEffect(sacrificedForests, sacrificedForests, Duration.Custom, SubLayer.SetPT_7b), source);
                    return true;
                }
            }
        }
        return false;
    }
}
