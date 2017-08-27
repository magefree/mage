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
package mage.cards.e;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AddManaOfAnyTypeProducedEffect;
import mage.abilities.mana.TriggeredManaAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ManaEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetLandPermanent;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

/**
 *
 * @author jeffwadsworth
 */
public class ExtraplanarLens extends CardImpl {

    private static final FilterLandPermanent filter = new FilterLandPermanent("land you control");

    static {
        filter.add(new ControllerPredicate(TargetController.YOU));
    }

    public ExtraplanarLens(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // Imprint - When Extraplanar Lens enters the battlefield, you may exile target land you control.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ExtraplanarLensImprintEffect(), true);
        ability.setAbilityWord(AbilityWord.IMPRINT);
        ability.addTarget(new TargetLandPermanent(filter));
        this.addAbility(ability);

        // Whenever a land with the same name as the exiled card is tapped for mana, its controller adds one mana to his or her mana pool of any type that land produced.
        this.addAbility(new ExtraplanarLensTriggeredAbility());

    }

    public ExtraplanarLens(final ExtraplanarLens card) {
        super(card);
    }

    @Override
    public ExtraplanarLens copy() {
        return new ExtraplanarLens(this);
    }
}

class ExtraplanarLensImprintEffect extends OneShotEffect {

    public ExtraplanarLensImprintEffect() {
        super(Outcome.Neutral);
        staticText = "you may exile target land you control";
    }

    public ExtraplanarLensImprintEffect(ExtraplanarLensImprintEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent extraplanarLens = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (controller != null) {
            Permanent targetLand = game.getPermanent(source.getFirstTarget());
            if (targetLand != null) {
                targetLand.moveToExile(null, extraplanarLens.getName() + " (Imprint)", source.getSourceId(), game);
                extraplanarLens.imprint(targetLand.getId(), game);
                extraplanarLens.addInfo("imprint", CardUtil.addToolTipMarkTags("[Imprinted card - " + targetLand.getLogName() + ']'), game);
            }
            return true;
        }
        return false;
    }

    @Override
    public ExtraplanarLensImprintEffect copy() {
        return new ExtraplanarLensImprintEffect(this);
    }

}

class ExtraplanarLensTriggeredAbility extends TriggeredManaAbility {

    public ExtraplanarLensTriggeredAbility() {
        super(Zone.BATTLEFIELD, new AddManaOfAnyTypeProducedEffect());
    }

    public ExtraplanarLensTriggeredAbility(final ExtraplanarLensTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.TAPPED_FOR_MANA;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent landTappedForMana = game.getPermanentOrLKIBattlefield(event.getSourceId());
        Permanent extraplanarLens = game.getPermanent(getSourceId());
        if (extraplanarLens != null
                && landTappedForMana != null
                && !extraplanarLens.getImprinted().isEmpty()) {
            Card imprinted = game.getCard(extraplanarLens.getImprinted().get(0));
            if (imprinted != null
                    && game.getState().getZone(imprinted.getId()) == Zone.EXILED) {
                if (landTappedForMana.getName().equals(imprinted.getName())
                        && landTappedForMana.isLand()) {
                    ManaEvent mEvent = (ManaEvent) event;
                    for (Effect effect : getEffects()) {
                        effect.setValue("mana", mEvent.getMana());
                    }
                    getEffects().get(0).setTargetPointer(new FixedTarget(landTappedForMana.getId()));
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return new StringBuilder("Whenever a land with the same name as the exiled card is tapped for mana, ").append(super.getRule()).toString();
    }

    @Override
    public ExtraplanarLensTriggeredAbility copy() {
        return new ExtraplanarLensTriggeredAbility(this);
    }

}
