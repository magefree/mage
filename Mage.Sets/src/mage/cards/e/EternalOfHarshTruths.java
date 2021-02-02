
package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AttacksAndIsNotBlockedTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.AfflictAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author emerald000
 */
public final class EternalOfHarshTruths extends CardImpl {

    public EternalOfHarshTruths(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Afflict 2
        this.addAbility(new AfflictAbility(2));

        // Whenever Eternal of Harsh Truths attacks and isn't blocked, draw a card.
        this.addAbility(new AttacksAndIsNotBlockedTriggeredAbility(new DrawCardSourceControllerEffect(1)));
    }

    private EternalOfHarshTruths(final EternalOfHarshTruths card) {
        super(card);
    }

    @Override
    public EternalOfHarshTruths copy() {
        return new EternalOfHarshTruths(this);
    }
}
