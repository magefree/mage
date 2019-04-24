
package mage.cards.c;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.CopyPermanentEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.util.functions.EmptyApplyToPermanent;

/**
 *
 * @author LevelX2
 */
public final class CopyEnchantment extends CardImpl {

    public CopyEnchantment(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{U}");

        // You may have Copy Enchantment enter the battlefield as a copy of any enchantment on the battlefield.
        //this.addAbility(new EntersBattlefieldAbility(new CopyEnchantmentEffect(new FilterEnchantmentPermanent("any enchantment")), true));
        this.addAbility(new EntersBattlefieldAbility(new CopyPermanentEffect(StaticFilters.FILTER_ENCHANTMENT_PERMANENT), true));
    }

    public CopyEnchantment(final CopyEnchantment card) {
        super(card);
    }

    @Override
    public CopyEnchantment copy() {
        return new CopyEnchantment(this);
    }
}

class CopyEnchantmentEffect extends CopyPermanentEffect {

    public CopyEnchantmentEffect(FilterPermanent filter) {
        super(filter, new EmptyApplyToPermanent());
    }

    public CopyEnchantmentEffect(final CopyEnchantmentEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent sourcePermanent = game.getPermanentEntering(source.getSourceId());
        if (controller != null && sourcePermanent != null) {
            if (super.apply(game, source)) {
                Permanent permanentToCopy = getBluePrintPermanent();
                if (permanentToCopy != null) {
                    if (permanentToCopy.hasSubtype(SubType.AURA, game)) {
                        Target target = getBluePrintPermanent().getSpellAbility().getTargets().get(0);
                        Outcome auraOutcome = Outcome.BoostCreature;
                        Ability:
                        for (Ability ability : getBluePrintPermanent().getAbilities()) {
                            if (ability instanceof SpellAbility) {
                                for (Effect effect : ability.getEffects()) {
                                    if (effect instanceof AttachEffect) {
                                        auraOutcome = effect.getOutcome();
                                        break Ability;
                                    }
                                }
                            }
                        }
                        target.setNotTarget(true);
                        if (controller.choose(auraOutcome, target, source.getSourceId(), game)) {
                            UUID targetId = target.getFirstTarget();
                            Permanent targetPermanent = game.getPermanent(targetId);
                            Player targetPlayer = game.getPlayer(targetId);
                            if (targetPermanent != null) {
                                targetPermanent.addAttachment(sourcePermanent.getId(), game);
                            } else if (targetPlayer != null) {
                                targetPlayer.addAttachment(sourcePermanent.getId(), game);
                            } else {
                                return false;
                            }
                        }
                    }
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public CopyEnchantmentEffect copy() {
        return new CopyEnchantmentEffect(this);
    }

}
