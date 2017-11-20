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

import java.util.UUID;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.abilities.effects.common.ReturnToHandFromBattlefieldAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledEnchantmentPermanent;
import mage.filter.predicate.ObjectPlayer;
import mage.filter.predicate.ObjectPlayerPredicate;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.other.OwnerPredicate;
import mage.filter.predicate.permanent.AttachedToControlledPermanentPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author L_J
 */
public class RemoveEnchantments extends CardImpl {

    private static final FilterPermanent filter1 = new FilterControlledEnchantmentPermanent();
    private static final FilterPermanent filter2 = new FilterPermanent();
    private static final FilterPermanent filter3 = new FilterPermanent();
    private static final FilterPermanent filter4 = new FilterControlledEnchantmentPermanent();
    private static final FilterPermanent filter5 = new FilterPermanent();
    private static final FilterPermanent filter6 = new FilterPermanent();
    static {
            // all enchantments you both own and control
        filter1.add(new OwnerPredicate(TargetController.YOU));
            // all Auras you own attached to permanents you control
        filter2.add(new AttachedToControlledPermanentPredicate());
        filter2.add(new SubtypePredicate(SubType.AURA));
        filter2.add(new OwnerPredicate(TargetController.YOU));
            // all Auras you own attached to attacking creatures your opponents control
        filter3.add(new AttachedToOpponentControlledAttackingCreaturePredicate());
        filter3.add(new SubtypePredicate(SubType.AURA));
        filter3.add(new OwnerPredicate(TargetController.YOU));
            // all other enchantments you control (i.e. that you don't own)
        filter4.add(new OwnerPredicate(TargetController.NOT_YOU));
            // all other Auras attached to permanents you control (i.e. that you don't own)
        filter5.add(new AttachedToControlledPermanentPredicate());
        filter5.add(new SubtypePredicate(SubType.AURA));
        filter5.add(new OwnerPredicate(TargetController.NOT_YOU));
            // all other Auras attached to attacking creatures your opponents control (i.e. that you don't own)
        filter6.add(new AttachedToOpponentControlledAttackingCreaturePredicate());
        filter6.add(new SubtypePredicate(SubType.AURA));
        filter6.add(new OwnerPredicate(TargetController.NOT_YOU));
    }

    public RemoveEnchantments(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{W}");

        // Return to your hand all enchantments you both own and control, all Auras you own attached to permanents you control, and all Auras you own attached to attacking creatures your opponents control. Then destroy all other enchantments you control, all other Auras attached to permanents you control, and all other Auras attached to attacking creatures your opponents control.
        this.getSpellAbility().addEffect(new ReturnToHandFromBattlefieldAllEffect(filter1).setText("Return to your hand all enchantments you both own and control,"));
        this.getSpellAbility().addEffect(new ReturnToHandFromBattlefieldAllEffect(filter2).setText(" all Auras you own attached to permanents you control"));
        this.getSpellAbility().addEffect(new ReturnToHandFromBattlefieldAllEffect(filter3).setText("and all Auras you own attached to attacking creatures your opponents control"));
        this.getSpellAbility().addEffect(new DestroyAllEffect(filter4).setText("Then destroy all other enchantments you control,"));
        this.getSpellAbility().addEffect(new DestroyAllEffect(filter5).setText(" all other Auras attached to permanents you control"));
        this.getSpellAbility().addEffect(new DestroyAllEffect(filter6).setText("and all other Auras attached to attacking creatures your opponents control"));
    }

    public RemoveEnchantments(final RemoveEnchantments card) {
        super(card);
    }

    @Override
    public RemoveEnchantments copy() {
        return new RemoveEnchantments(this);
    }
}

class AttachedToOpponentControlledAttackingCreaturePredicate implements ObjectPlayerPredicate<ObjectPlayer<Permanent>> {

    @Override
    public boolean apply(ObjectPlayer<Permanent> input, Game game) {
        Permanent attachement = input.getObject();
        if (attachement != null) {
            Permanent permanent = game.getPermanent(attachement.getAttachedTo());
            if (permanent != null) {
                if (permanent.isCreature()) {
                    if (permanent.isAttacking()) {
                        if (!permanent.getControllerId().equals(input.getPlayerId()) &&
                                game.getPlayer(input.getPlayerId()).hasOpponent(permanent.getControllerId(), game)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "Attached to attacking creatures your opponents control";
    }
}
