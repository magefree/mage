package mage.cards.k;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KoalaSheep extends CardImpl {

    public KoalaSheep(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.BEAR);
        this.subtype.add(SubType.SHEEP);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // When this creature enters, you gain 3 life.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new GainLifeEffect(3)));
    }

    private KoalaSheep(final KoalaSheep card) {
        super(card);
    }

    @Override
    public KoalaSheep copy() {
        return new KoalaSheep(this);
    }
}
