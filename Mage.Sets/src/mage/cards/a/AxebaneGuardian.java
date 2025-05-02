package mage.cards.a;

import mage.MageInt;
import mage.Mana;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.DefenderAbility;
import mage.abilities.mana.DynamicManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.AbilityPredicate;

import java.util.UUID;

/**
 * @author Plopman
 */
public final class AxebaneGuardian extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent();

    static {
        filter.add(new AbilityPredicate(DefenderAbility.class));
    }

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(filter);
    private static final Hint hint = new ValueHint("Creatures you control with defender", xValue);

    public AxebaneGuardian(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.DRUID);

        this.power = new MageInt(0);
        this.toughness = new MageInt(3);

        // Defender
        this.addAbility(DefenderAbility.getInstance());

        // {tap}: Add X mana in any combination of colors, where X is the number of creatures with defender you control.
        this.addAbility(new DynamicManaAbility(
                Mana.AnyMana(1), xValue, "Add X mana in any combination of colors, " +
                "where X is the number of creatures you control with defender."
        ).addHint(hint));
    }

    private AxebaneGuardian(final AxebaneGuardian card) {
        super(card);
    }

    @Override
    public AxebaneGuardian copy() {
        return new AxebaneGuardian(this);
    }
}
