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
package mage.sets.tenthedition;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.cards.CardImpl;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.Filter;
import mage.filter.FilterPermanent;
import mage.filter.predicate.ObjectPlayer;
import mage.filter.predicate.ObjectPlayerPredicate;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetPermanent;
import mage.util.TargetAddress;

/**
 * @author duncant
 */
public class AuraGraft extends CardImpl {

    public AuraGraft(UUID ownerId) {
        super(ownerId, 67, "Aura Graft", Rarity.UNCOMMON, new CardType[]{CardType.INSTANT}, "{1}{U}");
        this.expansionSetCode = "10E";

        // Gain control of target Aura that's attached to a permanent. Attach it to another permanent it can enchant.
        FilterPermanent filter = new FilterPermanent("Aura that's attached to a permanent");
        filter.add(new SubtypePredicate("Aura"));
        filter.add(new AttachedToPermanentPredicate());
        this.getSpellAbility().addTarget(new TargetPermanent(filter));

        Effect gainControlEffect = new GainControlTargetEffect(Duration.EndOfGame);
        this.getSpellAbility().addEffect(gainControlEffect);

        this.getSpellAbility().addEffect(new MoveTargetAuraEffect());
    }

    public AuraGraft(final AuraGraft card) {
        super(card);
    }

    @Override
    public AuraGraft copy() {
        return new AuraGraft(this);
    }
}


class AttachedToPermanentPredicate implements ObjectPlayerPredicate<ObjectPlayer<Permanent>> {

    public AttachedToPermanentPredicate() {
        super();
    }

    public boolean apply(ObjectPlayer<Permanent> input, Game game) {
        Permanent attached = input.getObject();
        return attached != null && game.getPermanent(attached.getAttachedTo()) != null;
    }
}

class PermanentCanBeAttachedToPredicate implements ObjectPlayerPredicate<ObjectPlayer<Permanent>> {
    protected Permanent aura;

    public PermanentCanBeAttachedToPredicate(Permanent aura) {
        super();
        this.aura = aura;
    }

    public boolean apply(ObjectPlayer<Permanent> input, Game game) {
        Permanent potentialAttachment = input.getObject();
        for (TargetAddress addr : TargetAddress.walk(aura)) {
            Target target = addr.getTarget(aura);
            Filter filter = target.getFilter();
            return filter.match(potentialAttachment, game);
        }
        return false;
    }
}

class MoveTargetAuraEffect extends OneShotEffect {

    public MoveTargetAuraEffect() {
        super(Outcome.Benefit);
        staticText = "Attach it to another permanent it can enchant";
    }

    public MoveTargetAuraEffect(final MoveTargetAuraEffect effect) {
        super(effect);
    }

    @Override
    public MoveTargetAuraEffect copy() {
        return new MoveTargetAuraEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source){
        Permanent enchantment = game.getPermanent(targetPointer.getFirst(game, source));
        if (enchantment == null) {
            return false;
        }
        Permanent oldAttachment = game.getPermanent(enchantment.getAttachedTo());
        if (oldAttachment == null) {
            return false;
        }
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }

        FilterPermanent filter = new FilterPermanent("another permanent " + enchantment.getLogName() + " can enchant");
        filter.add(new AnotherPredicate());
        filter.add(new PermanentCanBeAttachedToPredicate(enchantment));
        Target target = new TargetPermanent(filter);
        target.setNotTarget(true);
        if (target.canChoose(oldAttachment.getId(), controller.getId(), game)
            && controller.choose(outcome, target, oldAttachment.getId(), game)) {
            Permanent newAttachment = game.getPermanent(target.getFirstTarget());
            if (newAttachment != null &&
                oldAttachment.removeAttachment(enchantment.getId(), game)) {
                newAttachment.addAttachment(enchantment.getId(), game);
                game.informPlayers(enchantment.getLogName() + " was unattached from " + oldAttachment.getLogName() + " and attached to " + newAttachment.getLogName());
                return true;
            }
        }
        return false;
    }
}
