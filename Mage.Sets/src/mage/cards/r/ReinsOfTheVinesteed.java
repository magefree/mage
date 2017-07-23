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
package mage.cards.r;

import java.util.ArrayList;
import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.DiesAttachedTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicate;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author jeffwadsworth
 */
public class ReinsOfTheVinesteed extends CardImpl {

    public ReinsOfTheVinesteed(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{3}{G}");
        this.subtype.add("Aura");

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.AddAbility));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);

        // Enchanted creature gets +2/+2.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEnchantedEffect(2, 2, Duration.WhileOnBattlefield)));

        // When enchanted creature dies, you may return Reins of the Vinesteed from your graveyard to the battlefield attached to a creature that shares a creature type with that creature.
        this.addAbility(new DiesAttachedTriggeredAbility(new ReinsOfTheVinesteedEffect(), "enchanted creature", true));

    }

    public ReinsOfTheVinesteed(final ReinsOfTheVinesteed card) {
        super(card);
    }

    @Override
    public ReinsOfTheVinesteed copy() {
        return new ReinsOfTheVinesteed(this);
    }
}

class ReinsOfTheVinesteedEffect extends OneShotEffect {

    public ReinsOfTheVinesteedEffect() {
        super(Outcome.PutCardInPlay);
        staticText = "you may return {this} from your graveyard to the battlefield attached to a creature that shares a creature type with that creature";
    }

    public ReinsOfTheVinesteedEffect(final ReinsOfTheVinesteedEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card aura = game.getCard(source.getSourceId());
        if (aura != null
                && game.getState().getZone(aura.getId()) == Zone.GRAVEYARD) {
            Player controller = game.getPlayer(source.getControllerId());
            Permanent lastStateAura = (Permanent) game.getLastKnownInformation(aura.getId(), Zone.BATTLEFIELD);
            Permanent lastStateCreature = game.getPermanentOrLKIBattlefield(lastStateAura.getAttachedTo());
            if (lastStateCreature == null) {
                return false;
            }
            FilterCreaturePermanent FILTER = new FilterCreaturePermanent();
            StringBuilder sb = new StringBuilder("creature that shares a creature type with the formerly attached creature: ");
            ArrayList<Predicate<MageObject>> subtypes = new ArrayList<>();
            for (SubType subtype : lastStateCreature.getSubtype(game)) {
                subtypes.add(new SubtypePredicate(subtype));
                sb.append(subtype).append(", ");
            }
            FILTER.add(Predicates.or(subtypes));
            sb.delete(sb.length() - 2, sb.length());
            FILTER.setMessage(sb.toString());
            TargetPermanent target = new TargetPermanent(FILTER);
            target.setNotTarget(true);
            if (controller != null
                    && controller.choose(Outcome.PutCardInPlay, target, source.getSourceId(), game)) {
                Permanent targetPermanent = game.getPermanent(target.getFirstTarget());
                if (!targetPermanent.cantBeAttachedBy(aura, game)) {
                    game.getState().setValue("attachTo:" + aura.getId(), targetPermanent);
                    aura.putOntoBattlefield(game, Zone.GRAVEYARD, source.getSourceId(), controller.getId());
                    return targetPermanent.addAttachment(aura.getId(), game);
                }
            }
        }
        return false;
    }

    @Override
    public ReinsOfTheVinesteedEffect copy() {
        return new ReinsOfTheVinesteedEffect(this);
    }
}
