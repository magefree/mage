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
package mage.sets.dragonsmaze;

import java.util.UUID;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.SourceMatchesFilterCondition;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.EntersBattlefieldEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CopyEffect;
import mage.abilities.effects.common.CopyPermanentEffect;
import mage.cards.CardImpl;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.sets.tokens.EmptyToken;
import mage.util.functions.ApplyToPermanent;

/**
 *
 * @author LevelX2
 */
public class ProgenitorMimic extends CardImpl<ProgenitorMimic> {

    public ProgenitorMimic(UUID ownerId) {
        super(ownerId, 92, "Progenitor Mimic", Rarity.MYTHIC, new CardType[]{CardType.CREATURE}, "{4}{G}{U}");
        this.expansionSetCode = "DGM";
        this.subtype.add("Shapeshifter");

        this.color.setBlue(true);
        this.color.setGreen(true);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // You may have Progenitor Mimic enter the battlefield as a copy of any creature on the battlefield
        // except it gains "At the beginning of your upkeep, if this creature isn't a token,
        // put a token onto the battlefield that's a copy of this creature."
        this.addAbility(new SimpleStaticAbility(
                Zone.BATTLEFIELD,
                new EntersBattlefieldEffect(new CopyPermanentEffect(new ProgenitorMimicApplyToPermanent()),
                "You may have {this} enter the battlefield as a copy of any creature on the battlefield except it gains \"At the beginning of your upkeep, if this creature isn't a token, put a token onto the battlefield that's a copy of this creature.\"",
                true)));
    }

    public ProgenitorMimic(final ProgenitorMimic card) {
        super(card);
    }

    @Override
    public ProgenitorMimic copy() {
        return new ProgenitorMimic(this);
    }
}

class ProgenitorMimicApplyToPermanent extends ApplyToPermanent {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("no Token");
    static {
        filter.add(Predicates.not(new TokenPredicate()));
    }
    @Override
    public Boolean apply(Game game, Permanent permanent) {
        Ability ability = new ConditionalTriggeredAbility(
                new BeginningOfUpkeepTriggeredAbility(new ProgenitorMimicCopyEffect(), TargetController.YOU, false),
                new SourceMatchesFilterCondition(filter),
                "At the beginning of your upkeep, if this creature isn't a token, put a token onto the battlefield that's a copy of this creature.");
        permanent.addAbility(ability, game);
        return true;
    }
}

class ProgenitorMimicCopyEffect extends OneShotEffect<ProgenitorMimicCopyEffect> {

    public ProgenitorMimicCopyEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "put a token onto the battlefield that's a copy of this creature";
    }

    public ProgenitorMimicCopyEffect(final ProgenitorMimicCopyEffect effect) {
        super(effect);
    }

    @Override
    public ProgenitorMimicCopyEffect copy() {
        return new ProgenitorMimicCopyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent copyFromPermanent = null;
        // retrieve the copied permanent of Progenitor Mimic
        for (Effect effect : game.getState().getContinuousEffects().getLayeredEffects(game)) {
            if (effect instanceof CopyEffect) {
                CopyEffect copyEffect = (CopyEffect) effect;
                // take the exiting copy effect of Progenitor Mimic
                if (copyEffect.getSourceId().equals(source.getSourceId())) {
                    MageObject object = ((CopyEffect) effect).getTarget();
                    if (object instanceof Permanent) {
                        copyFromPermanent = (Permanent)object;
                    }
                }
            }
        }
        if (copyFromPermanent != null) {
            EmptyToken token = new EmptyToken();
            token.putOntoBattlefield(1, game, source.getSourceId(), source.getControllerId());
            Permanent sourcePermanent = game.getPermanent(token.getLastAddedToken());
            if (sourcePermanent != null) {
                game.copyPermanent(copyFromPermanent, sourcePermanent, source, new ProgenitorMimicApplyToPermanent());
                return true;
            }
        }
        return false;
    }
}
