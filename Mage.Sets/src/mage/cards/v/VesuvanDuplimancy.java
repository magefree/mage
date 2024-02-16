package mage.cards.v;

import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.FilterPermanent;
import mage.filter.FilterSpell;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.other.HasOnlySingleTargetPermanentPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.target.Target;

import java.util.Collection;
import java.util.Objects;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class VesuvanDuplimancy extends CardImpl {

    private static final FilterSpell filter
            = new FilterSpell("a spell that targets only a single artifact or creature you control");

    private static final FilterPermanent subfilter = new FilterControlledPermanent("artifact or creature you control");

    static {
        subfilter.add(Predicates.or(
                CardType.ARTIFACT.getPredicate(),
                CardType.CREATURE.getPredicate()
        ));
        filter.add(new HasOnlySingleTargetPermanentPredicate(subfilter));
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
