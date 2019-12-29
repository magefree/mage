package mage.cards.b;

import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BelleOfTheBrawl extends CardImpl {

    private static final FilterCreaturePermanent filter
            = new FilterCreaturePermanent(SubType.KNIGHT, "Knights");

    public BelleOfTheBrawl(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Menace
        this.addAbility(new MenaceAbility());

        // Whenever Belle of the Brawl attacks, other Knights you control get +1/+0 until end of turn.
        this.addAbility(new AttacksTriggeredAbility(new BoostControlledEffect(
                1, 0, Duration.EndOfTurn, filter, true
        ), false));
    }

    private BelleOfTheBrawl(final BelleOfTheBrawl card) {
        super(card);
    }

    @Override
    public BelleOfTheBrawl copy() {
        return new BelleOfTheBrawl(this);
    }
}
