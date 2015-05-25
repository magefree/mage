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
package mage.sets.archenemy;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BecomesTargetTriggeredAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.SubLayer;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author emerald000
 */
public class MakeshiftMannequin extends CardImpl {

    public MakeshiftMannequin(UUID ownerId) {
        super(ownerId, 20, "Makeshift Mannequin", Rarity.UNCOMMON, new CardType[]{CardType.INSTANT}, "{3}{B}");
        this.expansionSetCode = "ARC";

        // Return target creature card from your graveyard to the battlefield with a mannequin counter on it. For as long as that creature has a mannequin counter on it, it has "When this creature becomes the target of a spell or ability, sacrifice it."
        this.getSpellAbility().addEffect(new MakeshiftMannequinEffect());
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(new FilterCreatureCard("creature card from your graveyard")));
    }

    public MakeshiftMannequin(final MakeshiftMannequin card) {
        super(card);
    }

    @Override
    public MakeshiftMannequin copy() {
        return new MakeshiftMannequin(this);
    }
}

class MakeshiftMannequinEffect extends OneShotEffect {
    
    MakeshiftMannequinEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "Return target creature card from your graveyard to the battlefield with a mannequin counter on it. For as long as that creature has a mannequin counter on it, it has \"When this creature becomes the target of a spell or ability, sacrifice it.\"";
    }
    
    MakeshiftMannequinEffect(final MakeshiftMannequinEffect effect) {
        super(effect);
    }
    
    @Override
    public MakeshiftMannequinEffect copy() {
        return new MakeshiftMannequinEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            UUID cardId = this.getTargetPointer().getFirst(game, source);
            Card card = controller.getGraveyard().get(cardId, game);
            if (card != null) {
                if (controller.putOntoBattlefieldWithInfo(card, game, Zone.GRAVEYARD, source.getSourceId())) {
                    Permanent permanent = game.getPermanent(cardId);
                    if (permanent != null) {
                        permanent.addCounters(CounterType.MANNEQUIN.createInstance(), game);
                        ContinuousEffect gainedEffect = new MakeshiftMannequinGainAbilityEffect();
                        gainedEffect.setTargetPointer(new FixedTarget(cardId));
                        game.addEffect(gainedEffect, source);
                    }
                }
                return true;
            }
        }
        return false;
    }
}

class MakeshiftMannequinGainAbilityEffect extends ContinuousEffectImpl {

    MakeshiftMannequinGainAbilityEffect() {
        super(Duration.Custom, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
    }

    MakeshiftMannequinGainAbilityEffect(final MakeshiftMannequinGainAbilityEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(this.getTargetPointer().getFirst(game, source));
        if (permanent != null) {
            permanent.addAbility(new BecomesTargetTriggeredAbility(new SacrificeSourceEffect()), source.getSourceId(), game);
            return true;
        }
        return false;
    }

    @Override
    public boolean isInactive(Ability source, Game game) {
        Permanent permanent = game.getPermanent(this.getTargetPointer().getFirst(game, source));
        return permanent == null || permanent.getCounters().getCount(CounterType.MANNEQUIN) < 1;
    }

    @Override
    public MakeshiftMannequinGainAbilityEffect copy() {
        return new MakeshiftMannequinGainAbilityEffect(this);
    }
}
