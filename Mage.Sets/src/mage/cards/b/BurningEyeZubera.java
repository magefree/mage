package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetAnyTarget;

import java.util.Optional;
import java.util.UUID;

/**
 * @author LevelX2
 */
public final class BurningEyeZubera extends CardImpl {

    public BurningEyeZubera(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{R}");
        this.subtype.add(SubType.ZUBERA, SubType.SPIRIT);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When Burning-Eye Zubera dies, if 4 or more damage was dealt to it this turn, Burning-Eye Zubera deals 3 damage to any target.
        Ability ability = new DiesSourceTriggeredAbility(new DamageTargetEffect(3))
                .withInterveningIf(BurningEyeZuberaCondition.instance);
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    private BurningEyeZubera(final BurningEyeZubera card) {
        super(card);
    }

    @Override
    public BurningEyeZubera copy() {
        return new BurningEyeZubera(this);
    }
}

enum BurningEyeZuberaCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return Optional
                .ofNullable(source.getSourcePermanentOrLKI(game))
                .map(Permanent::getDamage)
                .orElse(0) >= 4;
    }

    @Override
    public String toString() {
        return "4 or more damage was dealt to it this turn";
    }
}
