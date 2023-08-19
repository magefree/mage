package mage.cards.a;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.keyword.AmassEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AvenEternal extends CardImpl {

    public AvenEternal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.BIRD);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Aven Eternal enters the battlefield, amass 1.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new AmassEffect(1, SubType.ZOMBIE)));
    }

    private AvenEternal(final AvenEternal card) {
        super(card);
    }

    @Override
    public AvenEternal copy() {
        return new AvenEternal(this);
    }
}
