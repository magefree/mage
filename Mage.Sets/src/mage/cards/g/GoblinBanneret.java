package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.constants.SubType;
import mage.abilities.keyword.MentorAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;

/**
 *
 * @author TheElk801
 */
public final class GoblinBanneret extends CardImpl {

    public GoblinBanneret(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}");

        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Mentor
        this.addAbility(new MentorAbility());

        // {1}{R}: Goblin Banneret gets +2/+0 until end of turn.
        this.addAbility(new SimpleActivatedAbility(
                Zone.BATTLEFIELD,
                new BoostSourceEffect(2, 0, Duration.EndOfTurn),
                new ManaCostsImpl<>("{1}{R}")
        ));
    }

    private GoblinBanneret(final GoblinBanneret card) {
        super(card);
    }

    @Override
    public GoblinBanneret copy() {
        return new GoblinBanneret(this);
    }
}
