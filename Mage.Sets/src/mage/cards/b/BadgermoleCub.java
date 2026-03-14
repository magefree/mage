package mage.cards.b;

import mage.MageInt;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.TapForManaAllTriggeredManaAbility;
import mage.abilities.effects.keyword.EarthbendTargetEffect;
import mage.abilities.effects.mana.BasicManaEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.common.TargetControlledLandPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BadgermoleCub extends CardImpl {

    public BadgermoleCub(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.BADGER);
        this.subtype.add(SubType.MOLE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When this creature enters, earthbend 1.
        Ability ability = new EntersBattlefieldTriggeredAbility(new EarthbendTargetEffect(1));
        ability.addTarget(new TargetControlledLandPermanent());
        this.addAbility(ability);

        // Whenever you tap a creature for mana, add an additional {G}.
        this.addAbility(new TapForManaAllTriggeredManaAbility(
                new BasicManaEffect(Mana.GreenMana(1)).setText("add an additional {G}"),
                StaticFilters.FILTER_CONTROLLED_CREATURE, SetTargetPointer.NONE
        ).setTriggerPhrase("Whenever you tap a creature for mana, "));
    }

    private BadgermoleCub(final BadgermoleCub card) {
        super(card);
    }

    @Override
    public BadgermoleCub copy() {
        return new BadgermoleCub(this);
    }
}
