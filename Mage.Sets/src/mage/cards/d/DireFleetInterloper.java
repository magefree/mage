
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.keyword.ExploreSourceEffect;
import mage.constants.SubType;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author TheElk801
 */
public final class DireFleetInterloper extends CardImpl {

    public DireFleetInterloper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.PIRATE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Menace
        this.addAbility(new MenaceAbility(false));

        // When Dire Fleet Interloper enters the battlefield, it explores. 
        this.addAbility(new EntersBattlefieldTriggeredAbility(new ExploreSourceEffect()));
    }

    private DireFleetInterloper(final DireFleetInterloper card) {
        super(card);
    }

    @Override
    public DireFleetInterloper copy() {
        return new DireFleetInterloper(this);
    }
}
