package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GoldfuryStrider extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("untapped artifacts and/or creatures you control");

    static {
        filter.add(TappedPredicate.UNTAPPED);
        filter.add(Predicates.or(
                CardType.ARTIFACT.getPredicate(),
                CardType.CREATURE.getPredicate()
        ));
    }

    public GoldfuryStrider(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{4}{R}");

        this.subtype.add(SubType.GOLEM);
        this.power = new MageInt(3);
        this.toughness = new MageInt(5);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Tap two untapped artifacts and/or creatures you control: Target creature gets +2/+0 until end of turn. Activate only as a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(
                new BoostTargetEffect(2, 0),
                new TapTargetCost(new TargetControlledPermanent(2, filter))
        );
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private GoldfuryStrider(final GoldfuryStrider card) {
        super(card);
    }

    @Override
    public GoldfuryStrider copy() {
        return new GoldfuryStrider(this);
    }
}
