package mage.cards.h;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.keyword.MyriadAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetadjustment.EachOpponentPermanentTargetsAdjuster;
import mage.target.targetpointer.EachTargetPointer;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HammersOfMoradin extends CardImpl {

    public HammersOfMoradin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.DWARF);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Myriad
        this.addAbility(new MyriadAbility());

        // Whenever Hammers of Moradin attacks, for each opponent, tap up to one target creature that player controls.
        Ability ability = new AttacksTriggeredAbility(
                new TapTargetEffect()
                        .setTargetPointer(new EachTargetPointer())
                        .setText("for each opponent, tap up to one target creature that player controls")
        );
        ability.setTargetAdjuster(new EachOpponentPermanentTargetsAdjuster());
        ability.addTarget(new TargetCreaturePermanent(0,1));
        this.addAbility(ability);
    }

    private HammersOfMoradin(final HammersOfMoradin card) {
        super(card);
    }

    @Override
    public HammersOfMoradin copy() {
        return new HammersOfMoradin(this);
    }
}
