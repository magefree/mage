package mage.cards.e;

import java.util.UUID;
import mage.MageItem;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.AttachmentAttachedToCardTypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.StackObject;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetPermanent;

/**
 *
 * @author jeffwadsworth
 */
public final class EnchantmentAlteration extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("aura attached to a creature or land");
    private static final FilterPermanent filter2 = new FilterPermanent("another target permanent that shares that type of creature or land");

    static {
        filter.add(SubType.AURA.getPredicate());
        filter.add(Predicates.or(new AttachmentAttachedToCardTypePredicate(CardType.CREATURE),
                new AttachmentAttachedToCardTypePredicate(CardType.LAND)));
        filter2.add(new SharesEnchantedCardTypePredicate());
        // another target permanent is handled in this predicate
    }

    public EnchantmentAlteration(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{U}");

        // Attach target Aura attached to a creature or land to another permanent of that type.
        this.getSpellAbility().addEffect(new EnchantmentAlterationEffect());
        TargetPermanent targetAura = new TargetPermanent(filter);
        this.getSpellAbility().addTarget(targetAura);

        TargetPermanent targetCreatureOrLandThatSharesTheEnchantedCardType = new TargetPermanent(filter2);
        this.getSpellAbility().addTarget(targetCreatureOrLandThatSharesTheEnchantedCardType);

    }

    private EnchantmentAlteration(final EnchantmentAlteration card) {
        super(card);
    }

    @Override
    public EnchantmentAlteration copy() {
        return new EnchantmentAlteration(this);
    }

}

class SharesEnchantedCardTypePredicate implements ObjectSourcePlayerPredicate<MageItem> {

    @Override
    public boolean apply(ObjectSourcePlayer<MageItem> input, Game game) {
        StackObject source = game.getStack().getStackObject(input.getSourceId());
        Permanent auraIsAttachedToThisPermanent = null;
        Permanent newPermanentToAttachAuraTo;
        if (source != null) {
            if (source.getStackAbility().getTargets().isEmpty()
                    || source.getStackAbility().getTargets().get(0).getTargets().isEmpty()) {
                return true;
            }
            Permanent auraPermanent = game.getPermanent(
                    source.getStackAbility().getTargets().get(0).getTargets().get(0)); // targeted aura enchanting land or creature
            if (auraPermanent != null) {
                auraIsAttachedToThisPermanent = game.getPermanent(auraPermanent.getAttachedTo());
            }
            if (auraIsAttachedToThisPermanent == null) { // the original permanent the aura is attached to
                return false;
            }
            newPermanentToAttachAuraTo = game.getPermanent(input.getObject().getId()); // the new target creature or land to enchant
            if (newPermanentToAttachAuraTo == auraIsAttachedToThisPermanent) {
                return false;  // must be another permanent
            }
            if (auraIsAttachedToThisPermanent.isCreature(game)
                    && newPermanentToAttachAuraTo.isCreature(game)) {
                return true;
            }
            if (auraIsAttachedToThisPermanent.isLand(game)
                    && newPermanentToAttachAuraTo.isLand(game)) {
                return true;
            }
            return false;
        }
        return true;
    }

}

class EnchantmentAlterationEffect extends OneShotEffect {

    public EnchantmentAlterationEffect() {
        super(Outcome.BoostCreature);
        this.staticText = "Attach target Aura attached to a creature or land to another permanent of that type";
    }

    public EnchantmentAlterationEffect(final EnchantmentAlterationEffect effect) {
        super(effect);
    }

    @Override
    public EnchantmentAlterationEffect copy() {
        return new EnchantmentAlterationEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Permanent aura = game.getPermanent(source.getFirstTarget());
            Permanent permanentToBeAttachedTo = game.getPermanent(source.getTargets().get(1).getFirstTarget());
            if (aura != null
                    && permanentToBeAttachedTo != null) {
                Permanent oldPermanent = game.getPermanent(aura.getAttachedTo());
                if (oldPermanent != null
                        && !oldPermanent.equals(permanentToBeAttachedTo)) {
                    Target auraTarget = aura.getSpellAbility().getTargets().get(0);
                    if (!auraTarget.canTarget(permanentToBeAttachedTo.getId(), game)) {
                        game.informPlayers(aura.getLogName() + " was not attched to " + permanentToBeAttachedTo.getLogName() + " because it's no legal target for the aura");
                    } else if (oldPermanent.removeAttachment(aura.getId(), source, game)) {
                        game.informPlayers(aura.getLogName() + " was unattached from " + oldPermanent.getLogName() + " and attached to " + permanentToBeAttachedTo.getLogName());
                        permanentToBeAttachedTo.addAttachment(aura.getId(), source, game);
                    }
                }
            }
            return true;
        }
        return false;
    }
}
