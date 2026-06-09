package mage.cards.g;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.cards.AdventureCard;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GiantKiller extends AdventureCard {

    private static final FilterPermanent filter = new FilterCreaturePermanent("creature with power 4 or greater");

    static {
        filter.add(new PowerPredicate(ComparisonType.MORE_THAN, 3));
    }

    public GiantKiller(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN, SubType.PEASANT}, "{W}",
                "Chop Down",
                new CardType[]{CardType.INSTANT}, "{2}{W}");

        // Giant Killer
        this.getLeftHalfCard().setPT(1, 2);

        // {1}{W}, {T}: Tap target creature.
        Ability ability = new SimpleActivatedAbility(new TapTargetEffect(), new ManaCostsImpl<>("{1}{W}"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent());
        this.getLeftHalfCard().addAbility(ability);

        // Chop Down
        // Destroy target creature with power 4 or greater.
        this.getRightHalfCard().getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getRightHalfCard().getSpellAbility().addTarget(new TargetPermanent(filter));

        finalizeCard();
    }

    private GiantKiller(final GiantKiller card) {
        super(card);
    }

    @Override
    public GiantKiller copy() {
        return new GiantKiller(this);
    }
}
