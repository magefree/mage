package mage.cards.p;

import mage.Mana;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.hint.ValueHint;
import mage.abilities.mana.DynamicManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.NamePredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PowerstoneShard extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("artifact you control named Powerstone Shard");

    static {
        filter.add(new NamePredicate("Powerstone Shard"));
        filter.add(CardType.ARTIFACT.getPredicate());
    }

    public PowerstoneShard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // {T}: Add {C} for each artifact you control named Powerstone Shard.
        DynamicValue xValue = new PermanentsOnBattlefieldCount(filter);
        this.addAbility(new DynamicManaAbility(Mana.ColorlessMana(1), xValue)
                .addHint(new ValueHint("Artifact you control named Powerstone Shard", xValue)));
    }

    private PowerstoneShard(final PowerstoneShard card) {
        super(card);
    }

    @Override
    public PowerstoneShard copy() {
        return new PowerstoneShard(this);
    }
}
