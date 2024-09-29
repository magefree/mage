package mage.cards.q;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksWhileSaddledTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.abilities.keyword.SaddleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class QuilledCharger extends CardImpl {

    public QuilledCharger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");

        this.subtype.add(SubType.PORCUPINE);
        this.subtype.add(SubType.MOUNT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Whenever Quilled Charger attacks while saddled, it gets +1/+2 and gains menace until end of turn.
        Ability ability = new AttacksWhileSaddledTriggeredAbility(
                new BoostSourceEffect(1, 2, Duration.EndOfTurn).setText("it gets +1/+2")
        );
        ability.addEffect(new GainAbilitySourceEffect(new MenaceAbility(false))
                .setText("and gains menace until end of turn"));
        this.addAbility(ability);

        // Saddle 2
        this.addAbility(new SaddleAbility(2));
    }

    private QuilledCharger(final QuilledCharger card) {
        super(card);
    }

    @Override
    public QuilledCharger copy() {
        return new QuilledCharger(this);
    }
}
