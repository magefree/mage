package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.CreaturesYouControlCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.SetBasePowerSourceEffect;
import mage.abilities.hint.common.CreaturesYouControlHint;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;

import java.util.UUID;

/**
 * @author Loki
 */
public final class SoulsurgeElemental extends CardImpl {

    public SoulsurgeElemental(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");
        this.subtype.add(SubType.ELEMENTAL);

        this.power = new MageInt(0);
        this.toughness = new MageInt(1);

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // Soulsurge Elemental's power is equal to the number of creatures you control.
        Effect effect = new SetBasePowerSourceEffect(CreaturesYouControlCount.instance, Duration.EndOfGame);
        this.addAbility(new SimpleStaticAbility(Zone.ALL, effect)
                .addHint(CreaturesYouControlHint.instance));
    }

    private SoulsurgeElemental(final SoulsurgeElemental card) {
        super(card);
    }

    @Override
    public SoulsurgeElemental copy() {
        return new SoulsurgeElemental(this);
    }
}
