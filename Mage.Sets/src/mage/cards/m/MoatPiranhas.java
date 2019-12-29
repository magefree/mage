package mage.cards.m;

import mage.MageInt;
import mage.abilities.keyword.DefenderAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MoatPiranhas extends CardImpl {

    public MoatPiranhas(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        this.subtype.add(SubType.FISH);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Defender
        this.addAbility(DefenderAbility.getInstance());
    }

    private MoatPiranhas(final MoatPiranhas card) {
        super(card);
    }

    @Override
    public MoatPiranhas copy() {
        return new MoatPiranhas(this);
    }
}
