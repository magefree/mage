package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.TieredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;

import java.util.Objects;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CloudsLimitBreak extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("tapped creature");

    static {
        filter.add(TappedPredicate.TAPPED);
    }

    public CloudsLimitBreak(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{W}");

        // Tiered
        this.addAbility(new TieredAbility(this));

        // * Cross-Slash -- {0} -- Destroy target tapped creature.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(filter));
        this.getSpellAbility().withFirstModeFlavorWord("Cross-Slash");
        this.getSpellAbility().withFirstModeCost(new GenericManaCost(0));

        // * Blade Beam -- {1} -- Destroy any number of target tapped creatures with different controllers.
        this.getSpellAbility().addMode(new Mode(new DestroyTargetEffect())
                .addTarget(new CloudsLimitBreakTarget())
                .withFlavorWord("Blade Beam")
                .withCost(new GenericManaCost(1)));

        // * Omnislash -- {3}{W} -- Destroy all tapped creatures.
        this.getSpellAbility().addMode(new Mode(new DestroyAllEffect(filter)
                .setText("destroy all tapped creatures"))
                .withFlavorWord("Omnislash")
                .withCost(new ManaCostsImpl<>("{3}{W}")));
    }

    private CloudsLimitBreak(final CloudsLimitBreak card) {
        super(card);
    }

    @Override
    public CloudsLimitBreak copy() {
        return new CloudsLimitBreak(this);
    }
}

class CloudsLimitBreakTarget extends TargetPermanent {

    private static final FilterPermanent filter
            = new FilterCreaturePermanent("tapped creatures with different controllers");

    CloudsLimitBreakTarget() {
        super(0, Integer.MAX_VALUE, filter, false);
    }

    private CloudsLimitBreakTarget(final CloudsLimitBreakTarget target) {
        super(target);
    }

    @Override
    public CloudsLimitBreakTarget copy() {
        return new CloudsLimitBreakTarget(this);
    }

    @Override
    public boolean canTarget(UUID playerId, UUID id, Ability source, Game game) {
        if (!super.canTarget(playerId, id, source, game)) {
            return false;
        }
        Permanent creature = game.getPermanent(id);
        if (creature == null) {
            return false;
        }
        return this
                .getTargets()
                .stream()
                .map(game::getPermanent)
                .filter(Objects::nonNull)
                .noneMatch(permanent -> !creature.getId().equals(permanent.getId())
                        && creature.isControlledBy(permanent.getControllerId())
                );
    }
}
