package mage.cards.l;

import mage.MageInt;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.dynamicvalue.common.SourcePermanentPowerCount;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.mana.DynamicManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.target.common.TargetPlayerOrPlaneswalker;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LeafkinAvenger extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("creature with power 4 or greater you control");

    static {
        filter.add(new PowerPredicate(ComparisonType.MORE_THAN, 3));
    }

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(filter);
    private static final DynamicValue xValue2 = new SourcePermanentPowerCount(false);

    public LeafkinAvenger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{G}");

        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // {T}: Add {G} for each creature you control with power 4 or greater.
        this.addAbility(new DynamicManaAbility(Mana.GreenMana(1), xValue));

        // {7}{R}: Leafkin Avenger deals damage equal to its power to target player or planeswalker.
        Ability ability = new SimpleActivatedAbility(
                new DamageTargetEffect(xValue2)
                        .setText("{this} deals damage equal to its power to target player or planeswalker"),
                new ManaCostsImpl<>("{7}{R}")
        );
        ability.addTarget(new TargetPlayerOrPlaneswalker());
        this.addAbility(ability);
    }

    private LeafkinAvenger(final LeafkinAvenger card) {
        super(card);
    }

    @Override
    public LeafkinAvenger copy() {
        return new LeafkinAvenger(this);
    }
}
