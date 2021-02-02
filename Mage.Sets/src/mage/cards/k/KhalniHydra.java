package mage.cards.k;

import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCost;
import mage.abilities.costs.mana.ManaCosts;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.cost.SpellCostReductionForEachSourceEffect;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.ColorPredicate;

import java.util.UUID;

/**
 * @author maurer.it_at_gmail.com
 */
public final class KhalniHydra extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("green creature you control");

    static {
        filter.add(new ColorPredicate(ObjectColor.GREEN));
    }

    public KhalniHydra(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}{G}{G}{G}{G}{G}{G}{G}");
        this.subtype.add(SubType.HYDRA);

        this.power = new MageInt(8);
        this.toughness = new MageInt(8);

        // This spell costs {G} less to cast for each green creature you control.
        ManaCosts<ManaCost> manaReduce = new ManaCostsImpl<>("{G}");
        DynamicValue xValue = new PermanentsOnBattlefieldCount(filter);
        this.addAbility(new SimpleStaticAbility(Zone.ALL,
                new SpellCostReductionForEachSourceEffect(manaReduce, xValue))
                .addHint(new ValueHint("Green creature you control", xValue))
        );

        // Trample
        this.addAbility(TrampleAbility.getInstance());
    }

    private KhalniHydra(final KhalniHydra card) {
        super(card);
    }

    @Override
    public KhalniHydra copy() {
        return new KhalniHydra(this);
    }
}
