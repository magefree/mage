package mage.cards.r;

import mage.MageInt;
import mage.abilities.abilityword.ReparteeAbility;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RehearsedDebater extends CardImpl {

    public RehearsedDebater(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.DJINN);
        this.subtype.add(SubType.BARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Repartee -- Whenever you cast an instant or sorcery spell that targets a creature, this creature gets +1/+1 until end of turn.
        this.addAbility(new ReparteeAbility(new BoostSourceEffect(1, 1, Duration.EndOfTurn)));
    }

    private RehearsedDebater(final RehearsedDebater card) {
        super(card);
    }

    @Override
    public RehearsedDebater copy() {
        return new RehearsedDebater(this);
    }
}
