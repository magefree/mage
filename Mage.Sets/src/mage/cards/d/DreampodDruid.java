package mage.cards.d;

import mage.MageInt;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.EnchantedSourceCondition;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.game.permanent.token.SaprolingToken;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class DreampodDruid extends CardImpl {

    private static final Condition condition = new EnchantedSourceCondition();

    public DreampodDruid(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.DRUID);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // At the beginning of each upkeep, if Dreampod Druid is enchanted, create a 1/1 green Saproling creature token.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(
                TargetController.ANY, new CreateTokenEffect(new SaprolingToken(), 1), false
        ).withInterveningIf(condition));
    }

    private DreampodDruid(final DreampodDruid card) {
        super(card);
    }

    @Override
    public DreampodDruid copy() {
        return new DreampodDruid(this);
    }
}
