package mage.cards.r;

import mage.MageInt;
import mage.abilities.common.AttacksEachCombatStaticAbility;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.token.ClueAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RedHerring extends CardImpl {

    public RedHerring(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.CLUE);
        this.subtype.add(SubType.FISH);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Red Herring attacks each combat if able.
        this.addAbility(new AttacksEachCombatStaticAbility());

        // {2}, Sacrifice Red Herring: Draw a card.
        this.addAbility(new ClueAbility(true));
    }

    private RedHerring(final RedHerring card) {
        super(card);
    }

    @Override
    public RedHerring copy() {
        return new RedHerring(this);
    }
}
