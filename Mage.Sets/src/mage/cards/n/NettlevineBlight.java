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

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.constants.Outcome;
import mage.target.TargetPermanent;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.permanent.CanBeEnchantedByPredicate;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;

/**
 *
 * @author jeffwadsworth
 */
public class NettlevineBlight extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("creature or land permanent");

    static {
        filter.add(Predicates.or(new CardTypePredicate(CardType.CREATURE),
                new CardTypePredicate(CardType.LAND)));
    }

    public NettlevineBlight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{4}{B}{B}");

        this.subtype.add("Aura");

        // Enchant creature or land
        TargetPermanent auraTarget = new TargetPermanent(filter);
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);

        // Enchanted permanent has "At the beginning of your end step, sacrifice this permanent and attach Nettlevine Blight to a creature or land you control."
        this.addAbility(new BeginningOfEndStepTriggeredAbility(new NettlevineBlightEffect(), TargetController.CONTROLLER_ATTACHED_TO, false));

    }

    public NettlevineBlight(final NettlevineBlight card) {
        super(card);
    }

    @Override
    public NettlevineBlight copy() {
        return new NettlevineBlight(this);
    }
}

class NettlevineBlightEffect extends OneShotEffect {

    public NettlevineBlightEffect() {
        super(Outcome.Detriment);
        this.staticText = "sacrifice this permanent and attach {this} to a creature or land you control";
    }

    public NettlevineBlightEffect(final NettlevineBlightEffect effect) {
        super(effect);
    }

    @Override
    public NettlevineBlightEffect copy() {
        return new NettlevineBlightEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent nettlevineBlight = game.getPermanent(source.getSourceId());
        Player newController = null;
        if (controller != null
                && nettlevineBlight != null) {
            Permanent enchantedPermanent = game.getPermanent(nettlevineBlight.getAttachedTo());
            if (enchantedPermanent != null) {
                newController = game.getPlayer(enchantedPermanent.getControllerId());
                enchantedPermanent.sacrifice(source.getSourceId(), game);
            }
            if (newController != null) {
                FilterPermanent filter = new FilterPermanent("creature or land permanent you control");
                filter.add(Predicates.or(new CardTypePredicate(CardType.CREATURE),
                        new CardTypePredicate(CardType.LAND)));
                filter.add(new ControllerIdPredicate(newController.getId()));
                filter.add(new CanBeEnchantedByPredicate(nettlevineBlight));
                Target target = new TargetPermanent(filter);
                target.setNotTarget(true);
                if (target.canChoose(source.getSourceId(), newController.getId(), game)
                        && newController.choose(outcome, target, source.getSourceId(), game)) {
                    Permanent chosenPermanent = game.getPermanent(target.getFirstTarget());
                    if (chosenPermanent != null) {
                        Card nettlevineBlightCard = game.getCard(source.getSourceId());
                        if (nettlevineBlightCard != null) {
                            Zone zone = game.getState().getZone(nettlevineBlightCard.getId());
                            nettlevineBlightCard.putOntoBattlefield(game, zone, source.getSourceId(), newController.getId());
                            game.getState().setValue("attachTo:" + nettlevineBlight.getId(), chosenPermanent);
                            chosenPermanent.addAttachment(nettlevineBlight.getId(), game);
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
}
