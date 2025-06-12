package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.Optional;
import java.util.UUID;

/**
 * @author LevelX2
 */
public final class RushingTideZubera extends CardImpl {

    public RushingTideZubera(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}{U}");
        this.subtype.add(SubType.ZUBERA);
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When Rushing-Tide Zubera dies, if 4 or more damage was dealt to it this turn, draw three cards.
        this.addAbility(new DiesSourceTriggeredAbility(new DrawCardSourceControllerEffect(3)).withInterveningIf(RushingTideZuberaCondition.instance));
    }

    private RushingTideZubera(final RushingTideZubera card) {
        super(card);
    }

    @Override
    public RushingTideZubera copy() {
        return new RushingTideZubera(this);
    }
}

enum RushingTideZuberaCondition implements Condition {
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
