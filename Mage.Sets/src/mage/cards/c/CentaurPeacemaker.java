package mage.cards.c;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.GainLifeAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CentaurPeacemaker extends CardImpl {

    public CentaurPeacemaker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}{W}");

        this.subtype.add(SubType.CENTAUR);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When Centaur Mediator enters the battlefield, each player gains 4 life.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new GainLifeAllEffect(4)));
    }

    private CentaurPeacemaker(final CentaurPeacemaker card) {
        super(card);
    }

    @Override
    public CentaurPeacemaker copy() {
        return new CentaurPeacemaker(this);
    }
}
