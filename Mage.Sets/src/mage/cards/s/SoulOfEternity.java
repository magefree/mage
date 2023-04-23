package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.ControllerLifeCount;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessSourceEffect;
import mage.abilities.keyword.EncoreAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SoulOfEternity extends CardImpl {

    public SoulOfEternity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{W}{W}");

        this.subtype.add(SubType.AVATAR);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Soul of Eternity's power and toughness are each equal to your life total.
        this.addAbility(new SimpleStaticAbility(
                Zone.ALL,
                new SetBasePowerToughnessSourceEffect(
                        ControllerLifeCount.instance
                ).setText("{this}'s power and toughness are each equal to your life total")
        ));

        // Encore {7}{W}{W}
        this.addAbility(new EncoreAbility(new ManaCostsImpl<>("{7}{W}{W}")));
    }

    private SoulOfEternity(final SoulOfEternity card) {
        super(card);
    }

    @Override
    public SoulOfEternity copy() {
        return new SoulOfEternity(this);
    }
}
