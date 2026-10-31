package mage.cards.b;

import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.dynamicvalue.common.SignInversionDynamicValue;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;

import java.util.UUID;

/**
 * @author muz
 */
public final class BattleOfWolf359 extends CardImpl {

    private static final FilterCreaturePermanent nonartifact = new FilterCreaturePermanent("nonartifact creatures");
    private static final FilterControlledCreaturePermanent artifact = new FilterControlledCreaturePermanent("artifact creatures you control");

    static {
        nonartifact.add(Predicates.not(CardType.ARTIFACT.getPredicate()));
        artifact.add(CardType.ARTIFACT.getPredicate());
    }

    private static final DynamicValue amount = new SignInversionDynamicValue(new PermanentsOnBattlefieldCount(artifact));

    public BattleOfWolf359(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{B}{B}");

        // All nonartifact creatures get -X/-X until end of turn, where X is the number of artifact creatures you control.
        this.getSpellAbility().addEffect(new BoostAllEffect(amount, amount, Duration.EndOfTurn, nonartifact, false)
            .setText("all nonartifact creatures get -X/-X until end of turn, where X is the number of artifact creatures you control"));
    }

    private BattleOfWolf359(final BattleOfWolf359 card) {
        super(card);
    }

    @Override
    public BattleOfWolf359 copy() {
        return new BattleOfWolf359(this);
    }
}
