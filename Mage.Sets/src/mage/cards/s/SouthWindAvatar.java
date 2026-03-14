package mage.cards.s;

import java.util.Optional;
import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.abilities.Ability;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.common.GainLifeControllerTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class SouthWindAvatar extends CardImpl {

    public SouthWindAvatar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");

        this.subtype.add(SubType.SNAKE);
        this.subtype.add(SubType.SPIRIT);
        this.subtype.add(SubType.AVATAR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // Whenever another creature you control dies, you gain life equal to its toughness.
        this.addAbility(new DiesCreatureTriggeredAbility(
            new GainLifeEffect(SouthWindAvatarValue.instance, "you gain life equal to its toughness"),
            false,
            StaticFilters.FILTER_ANOTHER_CREATURE_YOU_CONTROL
        ));

        // Whenever you gain life, each opponent loses 1 life.
        this.addAbility(new GainLifeControllerTriggeredAbility(new LoseLifeOpponentsEffect(1), false));
    }

    private SouthWindAvatar(final SouthWindAvatar card) {
        super(card);
    }

    @Override
    public SouthWindAvatar copy() {
        return new SouthWindAvatar(this);
    }
}

enum SouthWindAvatarValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return Optional
                .ofNullable((Permanent) effect.getValue("creatureDied"))
                .map(MageObject::getToughness)
                .map(MageInt::getValue)
                .orElse(0);
    }

    @Override
    public SouthWindAvatarValue copy() {
        return this;
    }

    @Override
    public String getMessage() {
        return "equal to its toughness";
    }

    @Override
    public String toString() {
        return "equal to its toughness";
    }
}
