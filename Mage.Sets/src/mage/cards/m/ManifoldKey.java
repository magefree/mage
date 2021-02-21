package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.combat.CantBeBlockedTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterArtifactPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ManifoldKey extends CardImpl {

    private static final FilterPermanent filter = new FilterArtifactPermanent("another target artifact");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public ManifoldKey(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}");

        // {1}, {T}: Untap another target artifact.
        Ability ability = new SimpleActivatedAbility(new UntapTargetEffect(), new GenericManaCost(1));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);

        // {3}, {T}: Target creature can't be blocked this turn.
        ability = new SimpleActivatedAbility(new CantBeBlockedTargetEffect(Duration.EndOfTurn), new GenericManaCost(3));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private ManifoldKey(final ManifoldKey card) {
        super(card);
    }

    @Override
    public ManifoldKey copy() {
        return new ManifoldKey(this);
    }
}
