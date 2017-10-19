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
package mage.cards.n;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.abilities.text.TextPartSubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.constants.*;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.TextPartSubtypePredicate;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

import java.util.LinkedHashSet;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 *
 * @author LevelX2
 */
public class NewBlood extends CardImpl {

    public NewBlood(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{B}{B}");

        TextPartSubType textPartVampire = (TextPartSubType) addTextPart(new TextPartSubType(SubType.VAMPIRE));
        FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("an untapped Vampire you control");
        filter.add(new TextPartSubtypePredicate(textPartVampire));
        filter.add(Predicates.not(new TappedPredicate()));
        // As an additional cost to cast New Blood, tap an untapped Vampire you control.
        this.getSpellAbility().addCost(new TapTargetCost(
                new TargetControlledCreaturePermanent(1, 1, filter, true)));

        // Gain control of target creature. Change the text of that creature by replacing all instances of one creature type with Vampire.
        getSpellAbility().addEffect(new NewBloodEffect());
        getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    public NewBlood(final NewBlood card) {
        super(card);
    }

    @Override
    public NewBlood copy() {
        return new NewBlood(this);
    }
}

class NewBloodEffect extends OneShotEffect {

    public NewBloodEffect() {
        super(Outcome.Benefit);
        this.staticText = "Gain control of target creature. Change the text of that creature by replacing all instances of one creature type with Vampire";
    }

    public NewBloodEffect(final NewBloodEffect effect) {
        super(effect);
    }

    @Override
    public NewBloodEffect copy() {
        return new NewBloodEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Permanent targetPermanent = game.getPermanent(getTargetPointer().getFirst(game, source));
            ContinuousEffect effect = new GainControlTargetEffect(Duration.Custom, true);
            effect.setTargetPointer(new FixedTarget(targetPermanent, game));
            game.addEffect(effect, source);
            effect = new ChangeCreatureTypeTargetEffect(null, SubType.VAMPIRE, Duration.Custom);
            effect.setTargetPointer(new FixedTarget(targetPermanent, game));
            game.addEffect(effect, source);
            return true;
        }
        return false;
    }
}

class ChangeCreatureTypeTargetEffect extends ContinuousEffectImpl {

    private SubType fromSubType;
    private SubType toSubType;

    public ChangeCreatureTypeTargetEffect(SubType fromSubType, SubType toSubType, Duration duration) {
        super(duration, Layer.TextChangingEffects_3, SubLayer.NA, Outcome.Benefit);
        this.fromSubType = fromSubType;
        this.toSubType = toSubType;
    }

    public ChangeCreatureTypeTargetEffect(final ChangeCreatureTypeTargetEffect effect) {
        super(effect);
        this.fromSubType = effect.fromSubType;
        this.toSubType = effect.toSubType;
    }

    @Override
    public void init(Ability source, Game game) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return;
        }
        if (fromSubType == null) {
            Choice typeChoice = new ChoiceImpl(true);
            typeChoice.setMessage("Choose creature type to change to Vampire");
            typeChoice.setChoices(SubType.getCreatureTypes(false).stream().map(SubType::toString).collect(Collectors.toCollection(LinkedHashSet::new)));
            while (!controller.choose(outcome, typeChoice, game)) {
                if (!controller.canRespond()) {
                    return;
                }
            }
            if (typeChoice.getChoice() == null) {
                return;
            }
            fromSubType = SubType.byDescription(typeChoice.getChoice());
            if (!game.isSimulation()) {
                game.informPlayers(controller.getLogName() + " has chosen the creature type: " + fromSubType.toString());
            }
        }

        super.init(source, game); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        if (fromSubType != null) {
            boolean objectFound = false;
            for (UUID targetId : targetPointer.getTargets(game, source)) {
                MageObject targetObject = game.getObject(targetId);
                if (targetObject != null) {
                    objectFound = true;
                    switch (layer) {
                        case TextChangingEffects_3:
                            targetObject.changeSubType(fromSubType, toSubType);
                            break;
                        case TypeChangingEffects_4:
                            if (sublayer == SubLayer.NA) {
                                if (targetObject.hasSubtype(fromSubType, game)) {
                                    targetObject.getSubtype(game).remove(fromSubType);
                                    if (!targetObject.hasSubtype(toSubType, game)) {
                                        targetObject.getSubtype(game).add(toSubType);
                                    }
                                }
                                break;
                            }
                    }
                }
                if (!objectFound && this.getDuration() == Duration.Custom) {
                    this.discard();
                }
            }
            return true;
        } else {
            throw new UnsupportedOperationException("No subtype to change set");
        }
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean hasLayer(Layer layer) {
        return layer == Layer.TextChangingEffects_3
                || layer == Layer.TypeChangingEffects_4;
    }

    @Override
    public ChangeCreatureTypeTargetEffect copy() {
        return new ChangeCreatureTypeTargetEffect(this);
    }

    @Override
    public String getText(Mode mode) {
        return "Change the text of that creature by replacing all instances of one creature type with Vampire";
    }
}
