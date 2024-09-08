package mage.cards.s;

import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.common.CantBeCounteredSourceAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.GainsChoiceOfAbilitiesEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.ProtectionAbility;
import mage.abilities.keyword.ReachAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ShiftingCeratops extends CardImpl {

    public ShiftingCeratops(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{G}");

        this.subtype.add(SubType.DINOSAUR);
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // This spell can't be countered.
        this.addAbility(new CantBeCounteredSourceAbility());

        // Protection from blue
        this.addAbility(ProtectionAbility.from(ObjectColor.BLUE));

        // {G}: Shifting Ceratops gains your choice of reach, trample, or haste until end of turn.
        this.addAbility(new SimpleActivatedAbility(new GainsChoiceOfAbilitiesEffect(GainsChoiceOfAbilitiesEffect.TargetType.Source,
                ReachAbility.getInstance(), TrampleAbility.getInstance(), HasteAbility.getInstance()), new ManaCostsImpl<>("{G}")));
    }

    private ShiftingCeratops(final ShiftingCeratops card) {
        super(card);
    }

    @Override
    public ShiftingCeratops copy() {
        return new ShiftingCeratops(this);
    }
}
