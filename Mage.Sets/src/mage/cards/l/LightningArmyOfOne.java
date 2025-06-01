package mage.cards.l;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LightningArmyOfOne extends CardImpl {

    public LightningArmyOfOne(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // Stagger -- Whenever Lightning deals combat damage to a player, until your next turn, if a source would deal damage to that player or a permanent that player controls, it deals double that damage instead.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(
                new LightningArmyOfOneEffect(), false, true
        ).withFlavorWord("Stagger"));
    }

    private LightningArmyOfOne(final LightningArmyOfOne card) {
        super(card);
    }

    @Override
    public LightningArmyOfOne copy() {
        return new LightningArmyOfOne(this);
    }
}

class LightningArmyOfOneEffect extends ReplacementEffectImpl {

    LightningArmyOfOneEffect() {
        super(Duration.UntilYourNextTurn, Outcome.Benefit);
        staticText = "until your next turn, if a source would deal damage to that player " +
                "or a permanent that player controls, it deals double that damage instead";
    }

    private LightningArmyOfOneEffect(final LightningArmyOfOneEffect effect) {
        super(effect);
    }

    @Override
    public LightningArmyOfOneEffect copy() {
        return new LightningArmyOfOneEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        switch (event.getType()) {
            case DAMAGE_PLAYER:
            case DAMAGE_PERMANENT:
                return true;
            default:
                return false;
        }
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        UUID targetId = this.getTargetPointer().getFirst(game, source);
        if (targetId == null) {
            return false;
        }
        switch (event.getType()) {
            case DAMAGE_PLAYER:
                return targetId.equals(event.getPlayerId());
            case DAMAGE_PERMANENT:
                return targetId.equals(game.getControllerId(event.getTargetId()));
            default:
                return false;
        }
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        event.setAmount(CardUtil.overflowMultiply(event.getAmount(), 2));
        return false;
    }
}
