package mage.cards.p;

import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.BattleCryAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PrimarisChaplain extends CardImpl {

    public PrimarisChaplain(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}{B}");

        this.subtype.add(SubType.ASTARTES);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Battle cry
        this.addAbility(new BattleCryAbility());

        // Rosarius -- Whenever Primaris Chaplain attacks, it gains indestructible until end of turn.
        this.addAbility(new AttacksTriggeredAbility(new GainAbilitySourceEffect(
                IndestructibleAbility.getInstance(), Duration.EndOfTurn
        ).setText("it gains indestructible until end of turn")).withFlavorWord("Rosarius"));
    }

    private PrimarisChaplain(final PrimarisChaplain card) {
        super(card);
    }

    @Override
    public PrimarisChaplain copy() {
        return new PrimarisChaplain(this);
    }
}
