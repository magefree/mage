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
package mage.sets.commander2014;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.PutTokenOntoBattlefieldCopyTargetEffect;
import mage.abilities.effects.common.SacrificeTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */
public class FeldonOfTheThirdPath extends CardImpl {

    public FeldonOfTheThirdPath(UUID ownerId) {
        super(ownerId, 35, "Feldon of the Third Path", Rarity.MYTHIC, new CardType[]{CardType.CREATURE}, "{1}{R}{R}");
        this.expansionSetCode = "C14";
        this.supertype.add("Legendary");
        this.subtype.add("Human");
        this.subtype.add("Artificer");

        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // {2}{R}, {T}: Put a token onto the battlefield that's a copy of target creature card in your graveyard, except it's an artifact in addition to its other types. It gains haste. Sacrifice it at the beginning of the next end step.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new FeldonOfTheThirdPathEffect(), new ManaCostsImpl("{2}{R}"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetCardInYourGraveyard(1, 1, new FilterCreatureCard("creature card in your graveyard")));
        this.addAbility(ability);
    }

    public FeldonOfTheThirdPath(final FeldonOfTheThirdPath card) {
        super(card);
    }

    @Override
    public FeldonOfTheThirdPath copy() {
        return new FeldonOfTheThirdPath(this);
    }
}

class FeldonOfTheThirdPathEffect extends OneShotEffect {

    public FeldonOfTheThirdPathEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "Put a token onto the battlefield that's a copy of target creature card in your graveyard, except it's an artifact in addition to its other types. It gains haste. Sacrifice it at the beginning of the next end step";
    }

    public FeldonOfTheThirdPathEffect(final FeldonOfTheThirdPathEffect effect) {
        super(effect);
    }

    @Override
    public FeldonOfTheThirdPathEffect copy() {
        return new FeldonOfTheThirdPathEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card card = game.getCard(getTargetPointer().getFirst(game, source));
        if (card != null) {
            PutTokenOntoBattlefieldCopyTargetEffect effect = new PutTokenOntoBattlefieldCopyTargetEffect(source.getControllerId(), CardType.ARTIFACT, true);
            effect.setTargetPointer(new FixedTarget(card.getId(), game.getState().getZoneChangeCounter(card.getId())));
            effect.apply(game, source);
            for (Permanent addedToken : effect.getAddedPermanent()) {
                SacrificeTargetEffect sacrificeEffect = new SacrificeTargetEffect("Sacrifice the token at the beginning of the next end step", source.getControllerId());
                sacrificeEffect.setTargetPointer(new FixedTarget(addedToken, game));
                DelayedTriggeredAbility delayedAbility = new AtTheBeginOfNextEndStepDelayedTriggeredAbility(sacrificeEffect);
                delayedAbility.setSourceId(source.getSourceId());
                delayedAbility.setControllerId(source.getControllerId());
                delayedAbility.setSourceObject(source.getSourceObject(game), game);
                game.addDelayedTriggeredAbility(delayedAbility);
            }
            return true;
        }

        return false;
    }
}
