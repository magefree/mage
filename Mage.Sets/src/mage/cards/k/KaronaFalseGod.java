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
package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.Choice;
import mage.choices.ChoiceCreatureType;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author emerald000
 */
public class KaronaFalseGod extends CardImpl {

    public KaronaFalseGod(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}{U}{B}{R}{G}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.AVATAR);

        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // At the beginning of each player's upkeep, that player untaps Karona, False God and gains control of it.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, new KaronaFalseGodUntapGetControlEffect(), TargetController.ANY, false, true));

        // Whenever Karona attacks, creatures of the creature type of your choice get +3/+3 until end of turn.
        this.addAbility(new AttacksTriggeredAbility(new KaronaFalseGodEffect(), false));
    }

    public KaronaFalseGod(final KaronaFalseGod card) {
        super(card);
    }

    @Override
    public KaronaFalseGod copy() {
        return new KaronaFalseGod(this);
    }
}

class KaronaFalseGodUntapGetControlEffect extends OneShotEffect {

    public KaronaFalseGodUntapGetControlEffect() {
        super(Outcome.GainControl);
        this.staticText = "that player untaps {this} and gains control of it";
    }

    public KaronaFalseGodUntapGetControlEffect(final KaronaFalseGodUntapGetControlEffect effect) {
        super(effect);
    }

    @Override
    public KaronaFalseGodUntapGetControlEffect copy() {
        return new KaronaFalseGodUntapGetControlEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        Permanent sourcePermanent = game.getPermanent(source.getSourceId());
        Player newController = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (newController != null && controller != null && sourceObject != null && sourceObject.equals(sourcePermanent)) {
            sourcePermanent.untap(game);
            game.informPlayers(newController.getLogName() + " untaps " + sourceObject.getIdName());
            // remove old control effects of the same player
            for (ContinuousEffect effect : game.getState().getContinuousEffects().getLayeredEffects(game)) {
                if (effect instanceof GainControlTargetEffect) {
                    UUID checkId = (UUID) ((GainControlTargetEffect) effect).getValue("KaronaFalseGodSourceId");
                    UUID controllerId = (UUID) ((GainControlTargetEffect) effect).getValue("KaronaFalseGodControllerId");
                    if (source.getSourceId().equals(checkId) && newController.getId().equals(controllerId)) {
                        effect.discard();
                    }
                }
            }
            ContinuousEffect effect = new GainControlTargetEffect(Duration.Custom, true, newController.getId());
            effect.setValue("KaronaFalseGodSourceId", source.getSourceId());
            effect.setValue("KaronaFalseGodControllerId", newController.getId());
            effect.setTargetPointer(new FixedTarget(sourcePermanent.getId()));
            effect.setText("and gains control of it");
            game.addEffect(effect, source);
            return true;
        }
        return false;
    }
}

class KaronaFalseGodEffect extends OneShotEffect {

    public KaronaFalseGodEffect() {
        super(Outcome.BoostCreature);
        this.staticText = "creatures of the creature type of your choice get +3/+3 until end of turn";
    }

    public KaronaFalseGodEffect(final KaronaFalseGodEffect effect) {
        super(effect);
    }

    @Override
    public KaronaFalseGodEffect copy() {
        return new KaronaFalseGodEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source.getSourceId());
        if (sourceObject != null && controller != null) {
            Choice typeChoice = new ChoiceCreatureType();
            while (!controller.choose(Outcome.BoostCreature, typeChoice, game)) {
                if (!controller.canRespond()) {
                    return false;
                }
            }
            String typeChosen = typeChoice.getChoice();
            if (!typeChosen.isEmpty()) {
                game.informPlayers(controller.getLogName() + " has chosen " + typeChosen);
                FilterCreaturePermanent filter = new FilterCreaturePermanent();
                filter.add(new SubtypePredicate(SubType.byDescription(typeChosen)));
                game.addEffect(new BoostAllEffect(3, 3, Duration.EndOfTurn, filter, false), source);
            }
            return true;
        }
        return false;
    }
}
