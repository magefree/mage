package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.MutagenToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SlitheringCryptid extends CardImpl {

    public SlitheringCryptid(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G/U}");

        this.subtype.add(SubType.FISH);
        this.subtype.add(SubType.MUTANT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // When this creature enters, create a Mutagen token.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new MutagenToken())));
    }

    private SlitheringCryptid(final SlitheringCryptid card) {
        super(card);
    }

    @Override
    public SlitheringCryptid copy() {
        return new SlitheringCryptid(this);
    }
}
