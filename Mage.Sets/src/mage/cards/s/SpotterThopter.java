package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.common.SourcePermanentPowerValue;
import mage.abilities.effects.keyword.ScryEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.PrototypeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SpotterThopter extends CardImpl {

    public SpotterThopter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{8}");

        this.subtype.add(SubType.THOPTER);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // Prototype {3}{U} -- 2/3
        this.addAbility(new PrototypeAbility(this, "{3}{U}", 2, 3));

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Spotter Thopter enters the battlefield, scry X, where X is its power.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new ScryEffect(SourcePermanentPowerValue.NOT_NEGATIVE)
                        .setText("scry X, where X is its power")));
    }

    private SpotterThopter(final SpotterThopter card) {
        super(card);
    }

    @Override
    public SpotterThopter copy() {
        return new SpotterThopter(this);
    }
}
