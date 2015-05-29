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
package mage.sets.venservskoth;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnFromExileEffect;
import mage.abilities.keyword.FlyingAbility;
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
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public class GalepowderMage extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("another target creature");
    
    static {
        filter.add(new AnotherPredicate());
    }
    
    public GalepowderMage(UUID ownerId) {
        super(ownerId, 12, "Galepowder Mage", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{3}{W}");
        this.expansionSetCode = "DDI";
        this.subtype.add("Kithkin");
        this.subtype.add("Wizard");

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Whenever Galepowder Mage attacks, exile another target creature. Return that card to the battlefield under its owner's control at the beginning of the next end step.
        Ability ability = new AttacksTriggeredAbility(new GalepowderMageEffect(), false);
        ability.addTarget(new TargetCreaturePermanent(filter));                
        this.addAbility(ability);
    }

    public GalepowderMage(final GalepowderMage card) {
        super(card);
    }

    @Override
    public GalepowderMage copy() {
        return new GalepowderMage(this);
    }
}

class GalepowderMageEffect extends OneShotEffect {

    public GalepowderMageEffect() {
        super(Outcome.Benefit);
        this.staticText = "exile another target creature. Return that card to the battlefield under its owner's control at the beginning of the next end step";
    }

    public GalepowderMageEffect(final GalepowderMageEffect effect) {
        super(effect);
    }

    @Override
    public GalepowderMageEffect copy() {
        return new GalepowderMageEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source.getSourceId());
        if (controller != null && sourceObject != null) {
            if (getTargetPointer().getFirst(game, source) != null) {
                Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
                Card card = game.getCard(getTargetPointer().getFirst(game, source));
                if (permanent != null) {
                    UUID exileId = UUID.randomUUID();
                    if (controller.moveCardToExileWithInfo(permanent, exileId, sourceObject.getIdName(), source.getSourceId(), game, Zone.BATTLEFIELD, true)) {
                        if (card != null) {
                            AtTheBeginOfNextEndStepDelayedTriggeredAbility delayedAbility = new AtTheBeginOfNextEndStepDelayedTriggeredAbility(new ReturnFromExileEffect(exileId, Zone.BATTLEFIELD));
                            delayedAbility.setSourceId(source.getSourceId());
                            delayedAbility.setControllerId(card.getOwnerId());
                            delayedAbility.setSourceObject(source.getSourceObject(game), game);
                            game.addDelayedTriggeredAbility(delayedAbility);
                        }
                    }
                }
            }            
            return true;
        }        
        return false;
    }
}