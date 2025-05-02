package mage.cards.e;

import mage.MageInt;
import mage.abilities.common.LandfallAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.ElfWarriorToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ElfswornGiant extends CardImpl {

    public ElfswornGiant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}{G}");

        this.subtype.add(SubType.GIANT);
        this.power = new MageInt(5);
        this.toughness = new MageInt(3);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // Landfall -- Whenever a land you control enters, create a 1/1 green Elf Warrior creature token.
        this.addAbility(new LandfallAbility(new CreateTokenEffect(new ElfWarriorToken())));
    }

    private ElfswornGiant(final ElfswornGiant card) {
        super(card);
    }

    @Override
    public ElfswornGiant copy() {
        return new ElfswornGiant(this);
    }
}
