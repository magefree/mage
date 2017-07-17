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
package mage.cards.c;

import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileAllEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.effects.common.continuous.BecomesColorTargetEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureTypeTargetEffect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreatureCard;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInGraveyard;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 *
 * @author emerald000
 */
public class ChainerDementiaMaster extends CardImpl {
    
    private static final FilterCreaturePermanent filterCreature = new FilterCreaturePermanent("Nightmare creatures");
    private static final FilterPermanent filterPermanent = new FilterPermanent("Nightmares");
    static {
        filterCreature.add(new SubtypePredicate(SubType.NIGHTMARE));
        filterPermanent.add(new SubtypePredicate(SubType.NIGHTMARE));
    }

    public ChainerDementiaMaster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{B}{B}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add("Human");
        this.subtype.add("Minion");
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Nightmare creatures get +1/+1.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostAllEffect(1, 1, Duration.WhileOnBattlefield, filterCreature, false)));
        
        // {B}{B}{B}, Pay 3 life: Put target creature card from a graveyard onto the battlefield under your control. That creature is black and is a Nightmare in addition to its other creature types.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ChainerDementiaMasterEffect(), new ManaCostsImpl<>("{B}{B}{B}"));
        ability.addCost(new PayLifeCost(3));
        ability.addTarget(new TargetCardInGraveyard(new FilterCreatureCard("creature card from a graveyard")));
        this.addAbility(ability);
        
        // When Chainer, Dementia Master leaves the battlefield, exile all Nightmares.
        this.addAbility(new LeavesBattlefieldTriggeredAbility(new ExileAllEffect(filterPermanent), false));
    }

    public ChainerDementiaMaster(final ChainerDementiaMaster card) {
        super(card);
    }

    @Override
    public ChainerDementiaMaster copy() {
        return new ChainerDementiaMaster(this);
    }
}

class ChainerDementiaMasterEffect extends OneShotEffect {
    
    ChainerDementiaMasterEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "Put target creature card from a graveyard onto the battlefield under your control. That creature is black and is a Nightmare in addition to its other creature types";
    }
    
    ChainerDementiaMasterEffect(final ChainerDementiaMasterEffect effect) {
        super(effect);
    }
    
    @Override
    public ChainerDementiaMasterEffect copy() {
        return new ChainerDementiaMasterEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            UUID cardId = this.getTargetPointer().getFirst(game, source);
            new ReturnFromGraveyardToBattlefieldTargetEffect().apply(game, source);
            Permanent permanent = game.getPermanent(cardId);
            if (permanent != null) {
                ContinuousEffectImpl effect = new BecomesColorTargetEffect(ObjectColor.BLACK, Duration.WhileOnBattlefield);
                effect.setTargetPointer(new FixedTarget(permanent, game));
                game.addEffect(effect, source);
                effect = new BecomesCreatureTypeTargetEffect(Duration.WhileOnBattlefield, SubType.NIGHTMARE, false);
                effect.setTargetPointer(new FixedTarget(permanent, game));
                game.addEffect(effect, source);
            }
            return true;
        }
        return false;
    }
}
