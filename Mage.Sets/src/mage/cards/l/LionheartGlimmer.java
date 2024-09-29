package mage.cards.l;

import mage.MageInt;
import mage.abilities.common.AttacksWithCreaturesTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.keyword.WardAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LionheartGlimmer extends CardImpl {

    public LionheartGlimmer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "{3}{W}{W}");

        this.subtype.add(SubType.CAT);
        this.subtype.add(SubType.GLIMMER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(5);

        // Ward {2}
        this.addAbility(new WardAbility(new ManaCostsImpl<>("{2}")));

        // Whenever you attack, creatures you control get +1/+1 until end of turn.
        this.addAbility(new AttacksWithCreaturesTriggeredAbility(
                new BoostControlledEffect(1, 1, Duration.EndOfTurn), 1
        ));
    }

    private LionheartGlimmer(final LionheartGlimmer card) {
        super(card);
    }

    @Override
    public LionheartGlimmer copy() {
        return new LionheartGlimmer(this);
    }
}
