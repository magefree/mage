package mage.cards.t;

import mage.MageInt;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.mana.AddManaFromColorChoicesEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.abilities.mana.LimitedTimesPerTurnActivatedManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ManaType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TemurDevotee extends CardImpl {

    public TemurDevotee(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Defender
        this.addAbility(DefenderAbility.getInstance());

        // {1}: Add {G}, {U}, or {R}. Activate only once each turn.
        this.addAbility(new LimitedTimesPerTurnActivatedManaAbility(
                new AddManaFromColorChoicesEffect(ManaType.GREEN, ManaType.BLUE, ManaType.RED), new GenericManaCost(1)
        ));
    }

    private TemurDevotee(final TemurDevotee card) {
        super(card);
    }

    @Override
    public TemurDevotee copy() {
        return new TemurDevotee(this);
    }
}
