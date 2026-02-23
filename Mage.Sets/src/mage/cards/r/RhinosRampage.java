package mage.cards.r;

import mage.abilities.Ability;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Outcome;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterArtifactPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetOpponentsCreaturePermanent;
import mage.target.targetpointer.EachTargetPointer;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author Jmlundeen
 */
public final class RhinosRampage extends CardImpl {

    public RhinosRampage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{R/G}");

        // Target creature you control gets +1/+0 until end of turn. It fights target creature an opponent controls. When excess damage is dealt to the creature an opponent controls this way, destroy up to one target noncreature artifact with mana value 3 or less.
        this.getSpellAbility().addEffect(new BoostTargetEffect(1, 0));
        this.getSpellAbility().addEffect(new RhinosRampageEffect());
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
        this.getSpellAbility().addTarget(new TargetOpponentsCreaturePermanent());
    }

    private RhinosRampage(final RhinosRampage card) {
        super(card);
    }

    @Override
    public RhinosRampage copy() {
        return new RhinosRampage(this);
    }
}

class RhinosRampageEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterArtifactPermanent("noncreature artifact with mana value 3 or less");

    static {
        filter.add(Predicates.not(CardType.CREATURE.getPredicate()));
        filter.add(new ManaValuePredicate(ComparisonType.OR_LESS, 3));
    }

    RhinosRampageEffect() {
        super(Outcome.BoostCreature);
        staticText = "it fights target creature an opponent controls. When excess damage is dealt to the creature " +
                "an opponent controls this way, destroy up to one target noncreature artifact with mana value 3 or less.";
        this.setTargetPointer(new EachTargetPointer());
    }

    protected RhinosRampageEffect(final RhinosRampageEffect effect) {
        super(effect);
    }

    @Override
    public RhinosRampageEffect copy() {
        return new RhinosRampageEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        List<Permanent> permanents = this
                .getTargetPointer()
                .getTargets(game, source)
                .stream()
                .map(game::getPermanent)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        if (permanents.size() < 2) {
            return false;
        }
        int excess = permanents.get(0).fightWithExcess(permanents.get(1), source, game, true);
        if (excess < 1) {
            return true;
        }
        ReflexiveTriggeredAbility ability = new ReflexiveTriggeredAbility(new DestroyTargetEffect(), false);
        ability.addTarget(new TargetPermanent(0, 1, filter));
        game.fireReflexiveTriggeredAbility(ability, source);
        return true;
    }
}
