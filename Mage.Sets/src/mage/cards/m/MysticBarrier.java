package mage.cards.m;

import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.PlayerCanOnlyAttackInDirectionRestrictionEffect;
import mage.abilities.meta.OrTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.TargetController;
import mage.constants.Zone;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class MysticBarrier extends CardImpl {

    public MysticBarrier(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{4}{W}");

        // When Mystic Barrier enters the battlefield or at the beginning of your upkeep, choose left or right.
        this.addAbility(new OrTriggeredAbility(Zone.BATTLEFIELD, PlayerCanOnlyAttackInDirectionRestrictionEffect.choiceEffect(),
                new EntersBattlefieldTriggeredAbility(null, false),
                new BeginningOfUpkeepTriggeredAbility(null, TargetController.YOU, false)));

        // Each player may attack only the nearest opponent in the last chosen direction and planeswalkers controlled by that player.
        this.addAbility(new SimpleStaticAbility(
                new PlayerCanOnlyAttackInDirectionRestrictionEffect(
                        Duration.WhileOnBattlefield,
                        "the last chosen direction"
                )
        ));
    }

    private MysticBarrier(final MysticBarrier card) {
        super(card);
    }

    @Override
    public MysticBarrier copy() {
        return new MysticBarrier(this);
    }
}
