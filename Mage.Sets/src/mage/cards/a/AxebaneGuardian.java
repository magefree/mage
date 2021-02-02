
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.Mana;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.keyword.DefenderAbility;
import mage.abilities.mana.DynamicManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.AbilityPredicate;

/**
 *
 * @author Plopman
 */
public final class AxebaneGuardian extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent("creatures with defender you control");

    static{
        filter.add(new AbilityPredicate(DefenderAbility.class));
    }

    public AxebaneGuardian(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.DRUID);

        this.power = new MageInt(0);
        this.toughness = new MageInt(3);

        // Defender
        this.addAbility(DefenderAbility.getInstance());

        // {tap}: Add X mana in any combination of colors, where X is the number of creatures with defender you control.
        this.addAbility(new DynamicManaAbility(new Mana(0, 0, 0, 0,0, 0,1, 0), new PermanentsOnBattlefieldCount(filter),
                "Add X mana in any combination of colors, where X is the number of creatures with defender you control."));
    }

    private AxebaneGuardian(final AxebaneGuardian card) {
        super(card);
    }

    @Override
    public AxebaneGuardian copy() {
        return new AxebaneGuardian(this);
    }
}
