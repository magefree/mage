
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
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.filter.predicate.permanent.AttachedToControlledPermanentPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author L_J
 */
public final class RemoveEnchantments extends CardImpl {

    private static final FilterPermanent filter1 = new FilterControlledEnchantmentPermanent();
    private static final FilterPermanent filter2 = new FilterPermanent();
    private static final FilterPermanent filter3 = new FilterPermanent();
    private static final FilterPermanent filter4 = new FilterControlledEnchantmentPermanent();
    private static final FilterPermanent filter5 = new FilterPermanent();
    private static final FilterPermanent filter6 = new FilterPermanent();
    static {
            // all enchantments you both own and control
        filter1.add(TargetController.YOU.getOwnerPredicate());
            // all Auras you own attached to permanents you control
        filter2.add(new AttachedToControlledPermanentPredicate());
        filter2.add(SubType.AURA.getPredicate());
        filter2.add(TargetController.YOU.getOwnerPredicate());
            // all Auras you own attached to attacking creatures your opponents control
        filter3.add(new AttachedToOpponentControlledAttackingCreaturePredicate());
        filter3.add(SubType.AURA.getPredicate());
        filter3.add(TargetController.YOU.getOwnerPredicate());
            // all other enchantments you control (i.e. that you don't own)
        filter4.add(TargetController.NOT_YOU.getOwnerPredicate());
            // all other Auras attached to permanents you control (i.e. that you don't own)
        filter5.add(new AttachedToControlledPermanentPredicate());
        filter5.add(SubType.AURA.getPredicate());
        filter5.add(TargetController.NOT_YOU.getOwnerPredicate());
            // all other Auras attached to attacking creatures your opponents control (i.e. that you don't own)
        filter6.add(new AttachedToOpponentControlledAttackingCreaturePredicate());
        filter6.add(SubType.AURA.getPredicate());
        filter6.add(TargetController.NOT_YOU.getOwnerPredicate());
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

    private RemoveEnchantments(final RemoveEnchantments card) {
        super(card);
    }

    @Override
    public RemoveEnchantments copy() {
        return new RemoveEnchantments(this);
    }
}

class AttachedToOpponentControlledAttackingCreaturePredicate implements ObjectSourcePlayerPredicate<ObjectSourcePlayer<Permanent>> {

    @Override
    public boolean apply(ObjectSourcePlayer<Permanent> input, Game game) {
        Permanent attachement = input.getObject();
        if (attachement != null) {
            Permanent permanent = game.getPermanent(attachement.getAttachedTo());
            if (permanent != null) {
                if (permanent.isCreature(game)) {
                    if (permanent.isAttacking()) {
                        if (!permanent.isControlledBy(input.getPlayerId()) &&
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
