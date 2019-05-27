package mage.cards.g;

import mage.MageInt;
import mage.abilities.keyword.ExaltedAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GoblinChampion extends CardImpl {

    public GoblinChampion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}");

        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(0);
        this.toughness = new MageInt(1);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Exalted
        this.addAbility(new ExaltedAbility());
    }

    private GoblinChampion(final GoblinChampion card) {
        super(card);
    }

    @Override
    public GoblinChampion copy() {
        return new GoblinChampion(this);
    }
}
