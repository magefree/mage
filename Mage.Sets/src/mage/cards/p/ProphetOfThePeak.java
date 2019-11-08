package mage.cards.p;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.keyword.ScryEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ProphetOfThePeak extends CardImpl {

    public ProphetOfThePeak(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{6}");

        this.subtype.add(SubType.CAT);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // When Prophet of the Peak enters the battlefield, scry 2.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new ScryEffect(2)));
    }

    private ProphetOfThePeak(final ProphetOfThePeak card) {
        super(card);
    }

    @Override
    public ProphetOfThePeak copy() {
        return new ProphetOfThePeak(this);
    }
}
