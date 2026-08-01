package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.abilities.common.AttacksWithCreaturesTriggeredAbility;
import mage.abilities.keyword.CrewAbility;
import mage.abilities.effects.keyword.RecruitEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class GreatGildedBoat extends CardImpl {

    public GreatGildedBoat(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}{U}");

        this.subtype.add(SubType.VEHICLE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Whenever you attack, recruit.
        this.addAbility(new AttacksWithCreaturesTriggeredAbility(new RecruitEffect(), 1));

        // Crew 2
        this.addAbility(new CrewAbility(2));
    }

    private GreatGildedBoat(final GreatGildedBoat card) {
        super(card);
    }

    @Override
    public GreatGildedBoat copy() {
        return new GreatGildedBoat(this);
    }
}
