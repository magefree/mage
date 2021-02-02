
package mage.cards.g;

import java.util.Set;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.game.Game;

/**
 * @author North
 */
public final class GuulDrazSpecter extends CardImpl {

    private static final String ruleText = "{this} gets +3/+3 as long as an opponent has no cards in hand";

    public GuulDrazSpecter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{B}");
        this.subtype.add(SubType.SPECTER);

        this.color.setBlack(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        this.addAbility(FlyingAbility.getInstance());
        // Guul Draz Specter gets +3/+3 as long as an opponent has no cards in hand.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ConditionalContinuousEffect(
                new BoostSourceEffect(3, 3, Duration.WhileOnBattlefield),
                GuulDrazSpecterCondition.instance,
                ruleText)));
        // Whenever Guul Draz Specter deals combat damage to a player, that player discards a card.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new DiscardTargetEffect(1), false, true));
    }

    private GuulDrazSpecter(final GuulDrazSpecter card) {
        super(card);
    }

    @Override
    public GuulDrazSpecter copy() {
        return new GuulDrazSpecter(this);
    }
}

enum GuulDrazSpecterCondition implements Condition {

    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        boolean result = false;

        Set<UUID> opponents = game.getOpponents(source.getControllerId());
        for (UUID opponentId : opponents) {
            result |= game.getPlayer(opponentId).getHand().isEmpty();
        }

        return result;
    }
}