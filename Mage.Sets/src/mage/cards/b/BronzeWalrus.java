package mage.cards.b;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.keyword.ScryEffect;
import mage.abilities.mana.AnyColorManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BronzeWalrus extends CardImpl {

    public BronzeWalrus(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{3}");

        this.subtype.add(SubType.WALRUS);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // When Bronze Walrus enters the battlefield, scry 2.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new ScryEffect(2)));

        // {T}: Add one mana of any color.
        this.addAbility(new AnyColorManaAbility());
    }

    private BronzeWalrus(final BronzeWalrus card) {
        super(card);
    }

    @Override
    public BronzeWalrus copy() {
        return new BronzeWalrus(this);
    }
}
