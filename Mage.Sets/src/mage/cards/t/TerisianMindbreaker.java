package mage.cards.t;

import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.MillHalfLibraryTargetEffect;
import mage.abilities.keyword.UnearthAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TerisianMindbreaker extends CardImpl {

    public TerisianMindbreaker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{7}");

        this.subtype.add(SubType.JUGGERNAUT);
        this.power = new MageInt(6);
        this.toughness = new MageInt(4);

        // Whenever Terisian Mindbreaker attacks, defending player mills half their library, rounded up.
        this.addAbility(new AttacksTriggeredAbility(
                new MillHalfLibraryTargetEffect(true)
                        .setText("defending player mills half their library, rounded up"),
                false, null, SetTargetPointer.PLAYER
        ));

        // Unearth {1}{U}{U}{U}
        this.addAbility(new UnearthAbility(new ManaCostsImpl<>("{1}{U}{U}{U}")));
    }

    private TerisianMindbreaker(final TerisianMindbreaker card) {
        super(card);
    }

    @Override
    public TerisianMindbreaker copy() {
        return new TerisianMindbreaker(this);
    }
}
