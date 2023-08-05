package mage.cards.a;

import java.util.UUID;
import mage.abilities.Ability;
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
import mage.filter.predicate.permanent.CanBeEnchantedByPredicate;
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

    private static final FilterPermanent filter = new FilterPermanent("Aura that's attached to a permanent");
    static {
        filter.add(SubType.AURA.getPredicate());
        filter.add(AuraGraftAttachedToPermanentPredicate.instance);
    }

    public AuraGraft(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}");

        // Gain control of target Aura that's attached to a permanent. Attach it to another permanent it can enchant.
        this.getSpellAbility().addTarget(new TargetPermanent(filter));
        this.getSpellAbility().addEffect(new GainControlTargetEffect(Duration.EndOfGame));
        this.getSpellAbility().addEffect(new AuraGraftMoveAuraEffect());
    }

    private AuraGraft(final AuraGraft card) {
        super(card);
    }

    @Override
    public AuraGraft copy() {
        return new AuraGraft(this);
    }
}

enum AuraGraftAttachedToPermanentPredicate implements ObjectSourcePlayerPredicate<Permanent> {
    instance;

    public boolean apply(ObjectSourcePlayer<Permanent> input, Game game) {
        Permanent attached = input.getObject();
        return attached != null && game.getPermanent(attached.getAttachedTo()) != null;
    }
}

class AuraGraftAuraCanEnchantPredicate implements ObjectSourcePlayerPredicate<Permanent> {

    protected Permanent aura;

    AuraGraftAuraCanEnchantPredicate(Permanent aura) {
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

class AuraGraftMoveAuraEffect extends OneShotEffect {

    AuraGraftMoveAuraEffect() {
        super(Outcome.Benefit);
        staticText = "Attach it to another permanent it can enchant";
    }

    private AuraGraftMoveAuraEffect(final AuraGraftMoveAuraEffect effect) {
        super(effect);
    }

    @Override
    public AuraGraftMoveAuraEffect copy() {
        return new AuraGraftMoveAuraEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent enchantment = game.getPermanent(targetPointer.getFirst(game, source));
        Player controller = game.getPlayer(source.getControllerId());
        if (enchantment == null || controller == null) {
            return false;
        }
        Permanent previouslyEnchanted = game.getPermanent(enchantment.getAttachedTo());
        if (previouslyEnchanted == null) {
            return false;
        }

        FilterPermanent filter = new FilterPermanent("another permanent " + enchantment.getLogName() + " can enchant");
        filter.add(AnotherPredicate.instance);
        filter.add(new AuraGraftAuraCanEnchantPredicate(enchantment)); // extracts Targets from Aura spell ability
        filter.add(new CanBeEnchantedByPredicate(enchantment)); // checks for protection
        Target target = new TargetPermanent(filter);
        target.setNotTarget(true);
        if (target.canChoose(controller.getId(), source, game)
                && controller.choose(outcome, target, source, game)) {
            Permanent newAttachment = game.getPermanent(target.getFirstTarget());
            if (newAttachment != null
                    && previouslyEnchanted.removeAttachment(enchantment.getId(), source, game)) {
                newAttachment.addAttachment(enchantment.getId(), source, game);
                game.informPlayers(enchantment.getLogName() + " was unattached from " + previouslyEnchanted.getLogName() + " and attached to " + newAttachment.getLogName());
                return true;
            }
        }
        return false;
    }
}
