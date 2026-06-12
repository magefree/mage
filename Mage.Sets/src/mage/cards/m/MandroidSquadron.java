package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class MandroidSquadron extends CardImpl {

    public MandroidSquadron(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(0);
        this.toughness = new MageInt(4);

        // When this creature enters, you gain 2 life.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new GainLifeEffect(2)));
    }

    private MandroidSquadron(final MandroidSquadron card) {
        super(card);
    }

    @Override
    public MandroidSquadron copy() {
        return new MandroidSquadron(this);
    }
}
