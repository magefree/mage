
package mage.cards.k;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.MonarchIsSourceControllerCondition;
import mage.abilities.effects.common.BecomesMonarchSourceEffect;
import mage.abilities.effects.common.combat.CantBeBlockedAllEffect;
import mage.abilities.hint.common.MonarchHint;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class KeeperOfKeys extends CardImpl {

    public KeeperOfKeys(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ROGUE);
        this.subtype.add(SubType.MUTANT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // When Keeper of Keys enters the battlefield, you become the monarch.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new BecomesMonarchSourceEffect()).addHint(MonarchHint.instance));

        // At the beginning of your upkeep, if you're the monarch, creatures you control can't be blocked this turn.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new CantBeBlockedAllEffect(
                StaticFilters.FILTER_CONTROLLED_CREATURES, Duration.EndOfTurn
        )).withInterveningIf(MonarchIsSourceControllerCondition.instance));
    }

    private KeeperOfKeys(final KeeperOfKeys card) {
        super(card);
    }

    @Override
    public KeeperOfKeys copy() {
        return new KeeperOfKeys(this);
    }
}
