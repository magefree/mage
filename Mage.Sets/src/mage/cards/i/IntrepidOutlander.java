package mage.cards.i;

import mage.MageInt;
import mage.abilities.effects.keyword.VentureIntoTheDungeonEffect;
import mage.abilities.keyword.PackTacticsAbility;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class IntrepidOutlander extends CardImpl {

    public IntrepidOutlander(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.ORC);
        this.subtype.add(SubType.RANGER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // Pack tactics — Whenever Intrepid Outlander attacks, if you attacked with creatures with total power 6 or greater this combat, venture into the dungeon.
        this.addAbility(new PackTacticsAbility(new VentureIntoTheDungeonEffect()));
    }

    private IntrepidOutlander(final IntrepidOutlander card) {
        super(card);
    }

    @Override
    public IntrepidOutlander copy() {
        return new IntrepidOutlander(this);
    }
}
