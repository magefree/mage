package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.constants.SubType;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.ElfKnightToken;

/**
 *
 * @author TheElk801
 */
public final class ConclaveCavalier extends CardImpl {

    public ConclaveCavalier(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}{G}{W}{W}");

        this.subtype.add(SubType.CENTAUR);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // When Conclave Cavalier dies, create two green and white 2/2 Elf Knight creature tokens with vigilance.
        this.addAbility(new DiesSourceTriggeredAbility(
                new CreateTokenEffect(new ElfKnightToken(), 2)
        ));
    }

    private ConclaveCavalier(final ConclaveCavalier card) {
        super(card);
    }

    @Override
    public ConclaveCavalier copy() {
        return new ConclaveCavalier(this);
    }
}
