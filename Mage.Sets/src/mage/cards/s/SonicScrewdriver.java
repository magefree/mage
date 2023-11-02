package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.combat.CantBeBlockedTargetEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.abilities.mana.AnyColorManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterArtifactPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.common.TargetArtifactPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class SonicScrewdriver extends CardImpl {

    private static final FilterArtifactPermanent filter
            = new FilterArtifactPermanent("another target artifact");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public SonicScrewdriver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // {T}: Add one mana of any color.
        this.addAbility(new AnyColorManaAbility());

        // {1}, {T}: Untap another target artifact.
        Ability ability = new SimpleActivatedAbility(
                new UntapTargetEffect(),
                new GenericManaCost(1)
        );
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetArtifactPermanent(filter));
        this.addAbility(ability);

        // {2}, {T}: Scry 1.
        ability = new SimpleActivatedAbility(
                new ScryEffect(1, false),
                new GenericManaCost(2)
        );
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);

        // {3}, {T}: Target creature can't be blocked this turn.
        ability = new SimpleActivatedAbility(
                new CantBeBlockedTargetEffect(),
                new GenericManaCost(3)
        );
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private SonicScrewdriver(final SonicScrewdriver card) {
        super(card);
    }

    @Override
    public SonicScrewdriver copy() {
        return new SonicScrewdriver(this);
    }
}
