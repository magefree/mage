package mage.cards.s;

import mage.MageInt;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.mana.AddManaOfAnyColorEffect;
import mage.abilities.keyword.ReachAbility;
import mage.abilities.mana.LimitedTimesPerTurnActivatedManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ScarecrowGuide extends CardImpl {

    public ScarecrowGuide(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{2}");

        this.subtype.add(SubType.SCARECROW);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // {1}: Add one mana of any color. Activate only once each turn.
        this.addAbility(new LimitedTimesPerTurnActivatedManaAbility(
                Zone.BATTLEFIELD,
                new AddManaOfAnyColorEffect(),
                new GenericManaCost(1)
        ));
    }

    private ScarecrowGuide(final ScarecrowGuide card) {
        super(card);
    }

    @Override
    public ScarecrowGuide copy() {
        return new ScarecrowGuide(this);
    }
}
