package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.keyword.SurveilEffect;
import mage.constants.SubType;
import mage.abilities.keyword.DefenderAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author TheElk801
 */
public final class BarrierOfBones extends CardImpl {

    public BarrierOfBones(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}");

        this.subtype.add(SubType.SKELETON);
        this.subtype.add(SubType.WALL);
        this.power = new MageInt(0);
        this.toughness = new MageInt(3);

        // Defender
        this.addAbility(DefenderAbility.getInstance());

        // When Barrier of Bones enters the battlefield, surveil 1.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new SurveilEffect(1), false
        ));
    }

    private BarrierOfBones(final BarrierOfBones card) {
        super(card);
    }

    @Override
    public BarrierOfBones copy() {
        return new BarrierOfBones(this);
    }
}
