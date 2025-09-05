package mage.cards.r;

import mage.MageInt;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.MayhemAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RagingGoblinoids extends CardImpl {

    public RagingGoblinoids(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}");

        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.BERSERKER);
        this.subtype.add(SubType.VILLAIN);
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Mayhem {2}{R}
        this.addAbility(new MayhemAbility(this, "{2}{R}"));
    }

    private RagingGoblinoids(final RagingGoblinoids card) {
        super(card);
    }

    @Override
    public RagingGoblinoids copy() {
        return new RagingGoblinoids(this);
    }
}
