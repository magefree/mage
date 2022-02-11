package mage.cards.o;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.ZoneChangeAllTriggeredAbility;
import mage.abilities.condition.common.ModeChoiceSourceCondition;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.effects.common.ChooseModeEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.ExileTopXMayPlayUntilEndOfTurnEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author LevelX2
 */
public final class OutpostSiege extends CardImpl {

    private static final String ruleTrigger1 = "&bull  Khans &mdash; At the beginning of your upkeep, exile the top card of your library. Until end of turn, you may play that card.";
    private static final String ruleTrigger2 = "&bull  Dragons &mdash; Whenever a creature you control leaves the battlefield, {this} deals 1 damage to any target.";

    public OutpostSiege(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{R}");

        // As Outpost Siege enters the battlefield, choose Khans or Dragons.
        this.addAbility(new EntersBattlefieldAbility(new ChooseModeEffect("Khans or Dragons?", "Khans", "Dragons"), null,
                "As {this} enters the battlefield, choose Khans or Dragons.", ""));

        // * Khans - At the beginning of your upkeep, exile the top card of your library. Until end of turn, you may play that card.
        this.addAbility(new ConditionalTriggeredAbility(
                new BeginningOfUpkeepTriggeredAbility(new ExileTopXMayPlayUntilEndOfTurnEffect(1), TargetController.YOU, false),
                new ModeChoiceSourceCondition("Khans"),
                ruleTrigger1));

        // * Dragons - Whenever a creature you control leaves the battlefield, Outpost Siege deals 1 damage to any target.
        Ability ability2 = new ConditionalTriggeredAbility(
                new ZoneChangeAllTriggeredAbility(Zone.BATTLEFIELD, Zone.BATTLEFIELD, null, new DamageTargetEffect(1),
                        new FilterControlledCreaturePermanent(), "", false),
                new ModeChoiceSourceCondition("Dragons"),
                ruleTrigger2);
        ability2.addTarget(new TargetAnyTarget());
        this.addAbility(ability2);

    }

    private OutpostSiege(final OutpostSiege card) {
        super(card);
    }

    @Override
    public OutpostSiege copy() {
        return new OutpostSiege(this);
    }
}
