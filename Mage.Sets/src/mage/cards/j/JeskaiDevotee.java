package mage.cards.j;

import mage.MageInt;
import mage.abilities.common.FlurryAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.mana.AddManaFromColorChoicesEffect;
import mage.abilities.mana.LimitedTimesPerTurnActivatedManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.ManaType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class JeskaiDevotee extends CardImpl {

    public JeskaiDevotee(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.ORC);
        this.subtype.add(SubType.MONK);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flurry -- Whenever you cast your second spell each turn, this creature gets +1/+1 until end of turn.
        this.addAbility(new FlurryAbility(new BoostSourceEffect(1, 1, Duration.EndOfTurn)));

        // {1}: Add {U}, {R}, or {W}. Activate only once each turn.
        this.addAbility(new LimitedTimesPerTurnActivatedManaAbility(
                new AddManaFromColorChoicesEffect(ManaType.BLUE, ManaType.RED, ManaType.WHITE), new GenericManaCost(1)
        ));
    }

    private JeskaiDevotee(final JeskaiDevotee card) {
        super(card);
    }

    @Override
    public JeskaiDevotee copy() {
        return new JeskaiDevotee(this);
    }
}
