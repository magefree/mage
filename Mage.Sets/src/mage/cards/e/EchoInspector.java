package mage.cards.e;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.keyword.ConniveSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EchoInspector extends CardImpl {

    public EchoInspector(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");

        this.subtype.add(SubType.BIRD);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Echo Inspector enters the battlefield, it connives.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new ConniveSourceEffect()));
    }

    private EchoInspector(final EchoInspector card) {
        super(card);
    }

    @Override
    public EchoInspector copy() {
        return new EchoInspector(this);
    }
}
