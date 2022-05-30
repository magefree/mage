
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.TurnedFaceUpSourceTriggeredAbility;
import mage.constants.SubType;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.keyword.MorphAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.common.FilterCreaturePermanent;

/**
 *
 * @author TheElk801
 */
public final class BloodstokeHowler extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Beast creatures you control");

    static {
        filter.add(SubType.BEAST.getPredicate());
    }

    public BloodstokeHowler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{R}");

        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Morph {6}{R}
        this.addAbility(new MorphAbility(new ManaCostsImpl<>("{6}{R}")));

        // When Bloodstoke Howler is turned face up, Beast creatures you control get +3/+0 until end of turn.
        this.addAbility(new TurnedFaceUpSourceTriggeredAbility(new BoostControlledEffect(3, 0, Duration.EndOfTurn, filter)));
    }

    private BloodstokeHowler(final BloodstokeHowler card) {
        super(card);
    }

    @Override
    public BloodstokeHowler copy() {
        return new BloodstokeHowler(this);
    }
}
