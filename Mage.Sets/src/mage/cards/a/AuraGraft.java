
package mage.cards.a;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.Filter;
import mage.filter.FilterPermanent;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetPermanent;
import mage.util.TargetAddress;

/**
 * @author duncant
 */
public final class AuraGraft extends CardImpl {

    public AuraGraft(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}");

        // Gain control of target Aura that's attached to a permanent. Attach it to another permanent it can enchant.
        FilterPermanent filter = new FilterPermanent("Aura that's attached to a permanent");
        filter.add(SubType.AURA.getPredicate());
        filter.add(new AttachedToPermanentPredicate());
        this.getSpellAbility().addTarget(new TargetPermanent(filter));

        Effect gainControlEffect = new GainControlTargetEffect(Duration.EndOfGame);
        this.getSpellAbility().addEffect(gainControlEffect);

        this.getSpellAbility().addEffect(new MoveTargetAuraEffect());
    }

    private AuraGraft(final AuraGraft card) {
        super(card);
    }

    @Override
    public AuraGraft copy() {
        return new AuraGraft(this);
    }
}

class AttachedToPermanentPredicate implements ObjectSourcePlayerPredicate<Permanent> {

    public AttachedToPermanentPredicate() {
        super();
    }

    public boolean apply(ObjectSourcePlayer<Permanent> input, Game game) {
        Permanent attached = input.getObject();
        return attached != null && game.getPermanent(attached.getAttachedTo()) != null;
    }
}

class PermanentCanBeAttachedToPredicate implements ObjectSourcePlayerPredicate<Permanent> {

    protected Permanent aura;

    public PermanentCanBeAttachedToPredicate(Permanent aura) {
        super();
        this.aura = aura;
    }

    @Override
    public boolean apply(ObjectSourcePlayer<Permanent> input, Game game) {
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
    public boolean apply(Game game, Ability source) {
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
        filter.add(AnotherPredicate.instance);
        filter.add(new PermanentCanBeAttachedToPredicate(enchantment));
        Target target = new TargetPermanent(filter);
        target.setNotTarget(true);
        if (target.canChoose(oldAttachment.getId(), controller.getId(), game)
                && controller.choose(outcome, target, oldAttachment.getId(), game)) {
            Permanent newAttachment = game.getPermanent(target.getFirstTarget());
            if (newAttachment != null
                    && oldAttachment.removeAttachment(enchantment.getId(), source, game)) {
                newAttachment.addAttachment(enchantment.getId(), source, game);
                game.informPlayers(enchantment.getLogName() + " was unattached from " + oldAttachment.getLogName() + " and attached to " + newAttachment.getLogName());
                return true;
            }
        }
        return false;
    }
}
