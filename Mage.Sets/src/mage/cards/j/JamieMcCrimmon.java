package mage.cards.j;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.DoctorsCompanionAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterSpell;
import mage.filter.common.FilterHistoricSpell;
import mage.game.Game;
import mage.game.stack.Spell;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class JamieMcCrimmon extends CardImpl {

    private static final FilterSpell filter = new FilterHistoricSpell();

    public JamieMcCrimmon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Whenever you cast a historic spell, Jamie McCrimmon gets +X/+X until end of turn, where X is that spell's mana value.
        this.addAbility(new SpellCastControllerTriggeredAbility(new BoostSourceEffect(
                JamieMcCrimmonValue.instance, JamieMcCrimmonValue.instance, Duration.EndOfTurn
        ), filter, false));

        // Doctor's companion
        this.addAbility(DoctorsCompanionAbility.getInstance());
    }

    private JamieMcCrimmon(final JamieMcCrimmon card) {
        super(card);
    }

    @Override
    public JamieMcCrimmon copy() {
        return new JamieMcCrimmon(this);
    }
}

enum JamieMcCrimmonValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return Optional
                .ofNullable((Spell) effect.getValue("spellCast"))
                .filter(Objects::nonNull)
                .map(Spell::getManaValue)
                .orElse(0);
    }

    @Override
    public JamieMcCrimmonValue copy() {
        return this;
    }

    @Override
    public String getMessage() {
        return "that spell's mana value";
    }

    @Override
    public String toString() {
        return "X";
    }
}
