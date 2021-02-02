
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
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
        Ability ability = new ConditionalInterveningIfTriggeredAbility(new DiesSourceTriggeredAbility(new DrawCardSourceControllerEffect(3)), new RushingTideZuberaCondition(),
                "When {this} dies, if 4 or more damage was dealt to it this turn, draw three cards.");
        this.addAbility(ability);
    }

    private RushingTideZubera(final RushingTideZubera card) {
        super(card);
    }

    @Override
    public RushingTideZubera copy() {
        return new RushingTideZubera(this);
    }
}

class RushingTideZuberaCondition implements Condition {

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent == null) {
            permanent = (Permanent) game.getLastKnownInformation(source.getSourceId(), Zone.BATTLEFIELD);
        }
        return permanent.getDamage() > 3;
    }
}
