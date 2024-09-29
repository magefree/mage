package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author xenohedron
 */
public final class SunshotMilitia extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("untapped artifacts and/or creatures you control");

    static {
        filter.add(TappedPredicate.UNTAPPED);
        filter.add(Predicates.or(
                CardType.ARTIFACT.getPredicate(),
                CardType.CREATURE.getPredicate()
        ));
    }

    public SunshotMilitia(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");
        
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Tap two untapped artifacts and/or creatures you control: Sunshot Militia deals 1 damage to each opponent. Activate only as a sorcery.
        this.addAbility(new ActivateAsSorceryActivatedAbility(new DamagePlayersEffect(1, TargetController.OPPONENT),
                new TapTargetCost(new TargetControlledPermanent(2, filter))));

    }

    private SunshotMilitia(final SunshotMilitia card) {
        super(card);
    }

    @Override
    public SunshotMilitia copy() {
        return new SunshotMilitia(this);
    }
}
