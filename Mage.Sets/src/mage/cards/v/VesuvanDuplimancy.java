package mage.cards.v;

import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.FilterSpell;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.target.Target;
import mage.util.TargetAddress;

import java.util.Collection;
import java.util.Objects;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class VesuvanDuplimancy extends CardImpl {

    private static final FilterSpell filter
            = new FilterSpell("a spell that targets only a single artifact or creature you control");

    static {
        filter.add(VesuvanDuplimancyPredicate.instance);
    }

    public VesuvanDuplimancy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{U}");

        // Whenever you cast a spell that targets only a single artifact or creature you control, create a token that's a copy of that artifact or creature, except it's not legendary.
        this.addAbility(new SpellCastControllerTriggeredAbility(new VesuvanDuplimancyEffect(), filter, false));
    }

    private VesuvanDuplimancy(final VesuvanDuplimancy card) {
        super(card);
    }

    @Override
    public VesuvanDuplimancy copy() {
        return new VesuvanDuplimancy(this);
    }
}

enum VesuvanDuplimancyPredicate implements ObjectSourcePlayerPredicate<Spell> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<Spell> input, Game game) {
        Spell spell = input.getObject();
        if (spell == null) {
            return false;
        }
        UUID singleTarget = null;
        for (TargetAddress addr : TargetAddress.walk(spell)) {
            Target targetInstance = addr.getTarget(spell);
            for (UUID targetId : targetInstance.getTargets()) {
                if (singleTarget == null) {
                    singleTarget = targetId;
                } else if (!singleTarget.equals(targetId)) {
                    return false;
                }
            }
        }
        if (singleTarget == null) {
            return false;
        }
        Permanent permanent = game.getPermanent(singleTarget);
        return permanent != null
                && permanent.isControlledBy(input.getPlayerId())
                && (permanent.isCreature(game) || permanent.isArtifact(game));
    }
}

class VesuvanDuplimancyEffect extends OneShotEffect {

    VesuvanDuplimancyEffect() {
        super(Outcome.Benefit);
        staticText = "create a token that's a copy of that artifact or creature, except it's not legendary";
    }

    private VesuvanDuplimancyEffect(final VesuvanDuplimancyEffect effect) {
        super(effect);
    }

    @Override
    public VesuvanDuplimancyEffect copy() {
        return new VesuvanDuplimancyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Spell spell = (Spell) getValue("spellCast");
        if (spell == null) {
            return false;
        }
        Permanent permanent = spell
                .getSpellAbility()
                .getTargets()
                .stream()
                .map(Target::getTargets)
                .flatMap(Collection::stream)
                .map(game::getPermanentOrLKIBattlefield)
                .filter(Objects::nonNull)
                .findAny()
                .orElse(null);
        return new CreateTokenCopyTargetEffect()
                .setIsntLegendary(true)
                .setSavedPermanent(permanent)
                .apply(game, source);
    }
}
