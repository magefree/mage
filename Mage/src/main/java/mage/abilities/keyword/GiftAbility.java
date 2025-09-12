package mage.abilities.keyword;

import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.StaticAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.GiftWasPromisedCondition;
import mage.abilities.costs.*;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.constants.GiftType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.common.TargetOpponent;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public class GiftAbility extends StaticAbility implements OptionalAdditionalSourceCosts {

    private static final String keywordText = "Gift";
    private static final String reminderText = "You may promise an opponent a gift as you cast this spell. If you do, ";
    private final String rule;

    // this is set to null first when the player chooses to use it then set to the playerId later once the player is chosen
    public static final String GIFT_ACTIVATION_VALUE_KEY = "giftPromisedActivation";

    protected OptionalAdditionalCost additionalCost;
    private final GiftType giftType;

    private static String makeReminderText(GiftType giftType, boolean isPermanent) {
        return reminderText + (isPermanent ? "when it enters, " : "") + "they " +
                giftType.getDescription() + (isPermanent ? "." : " before its other effects.");
    }

    public GiftAbility(Card card, GiftType giftType) {
        super(Zone.STACK, null);
        this.additionalCost = new OptionalAdditionalCostImpl(
                keywordText + ' ' + giftType.getName(),
                makeReminderText(giftType, card.isPermanent()),
                new PromiseGiftCost(giftType)
        );
        this.additionalCost.setRepeatable(false);
        this.giftType = giftType;
        this.rule = additionalCost.getName() + ' ' + additionalCost.getReminderText();
        this.setRuleAtTheTop(true);
        if (card.isPermanent()) {
            this.addSubAbility(new EntersBattlefieldTriggeredAbility(new PromiseGiftEffect(giftType))
                    .setTriggerPhrase("When this permanent enters, ")
                    .withInterveningIf(GiftWasPromisedCondition.TRUE)
                    .setRuleVisible(false));
        } else {
            card.getSpellAbility().addEffect(new PromiseGiftEffect(giftType));
        }
    }

    private GiftAbility(final GiftAbility ability) {
        super(ability);
        this.rule = ability.rule;
        this.additionalCost = ability.additionalCost.copy();
        this.giftType = ability.giftType;
    }

    @Override
    public GiftAbility copy() {
        return new GiftAbility(this);
    }

    @Override
    public void addOptionalAdditionalCosts(Ability ability, Game game) {
        if (!(ability instanceof SpellAbility)) {
            return;
        }
        Player player = game.getPlayer(ability.getControllerId());
        if (player == null) {
            return;
        }
        additionalCost.reset();
        if (!additionalCost.canPay(ability, this, ability.getControllerId(), game)
                || !player.chooseUse(Outcome.Benefit, "Promise an opponent " + giftType.getName() + '?', ability, game)) {
            return;
        }
        additionalCost.activate();
        for (Cost cost : ((Costs<Cost>) additionalCost)) {
            ability.getCosts().add(cost.copy());
        }
        ability.setCostsTag(GIFT_ACTIVATION_VALUE_KEY, null);
    }

    @Override
    public String getCastMessageSuffix() {
        return additionalCost.getCastSuffixMessage(0);
    }

    @Override
    public String getRule() {
        return rule;
    }
}

class PromiseGiftCost extends CostImpl {

    PromiseGiftCost(GiftType giftType) {
        text = "Gift " + giftType.getName();
    }

    private PromiseGiftCost(final PromiseGiftCost cost) {
        super(cost);
    }

    @Override
    public boolean canPay(Ability ability, Ability source, UUID controllerId, Game game) {
        return true;
    }

    @Override
    public boolean pay(Ability ability, Game game, Ability source, UUID controllerId, boolean noMana, Cost costToPay) {
        Player player = game.getPlayer(controllerId);
        if (player == null) {
            return paid;
        }
        TargetPlayer target = new TargetOpponent();
        target.withNotTarget(true);
        player.choose(Outcome.Detriment, target, source, game);
        Player opponent = game.getPlayer(target.getFirstTarget());
        if (opponent == null) {
            return paid;
        }
        source.setCostsTag(GiftAbility.GIFT_ACTIVATION_VALUE_KEY, opponent.getId());
        paid = true;
        return paid;
    }

    @Override
    public PromiseGiftCost copy() {
        return new PromiseGiftCost(this);
    }

}

class PromiseGiftEffect extends OneShotEffect {

    private final GiftType giftType;

    PromiseGiftEffect(GiftType giftType) {
        super(Outcome.Benefit);
        this.giftType = giftType;
        staticText = "they " + giftType.getDescription();
    }

    private PromiseGiftEffect(final PromiseGiftEffect effect) {
        super(effect);
        this.giftType = effect.giftType;
    }

    @Override
    public PromiseGiftEffect copy() {
        return new PromiseGiftEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(CardUtil.getSourceCostsTag(
                game, source, GiftAbility.GIFT_ACTIVATION_VALUE_KEY, (UUID) null
        ));
        if (player != null && giftType.applyGift(player, game, source)) {
            game.fireEvent(GameEvent.getEvent(
                    GameEvent.EventType.GAVE_GIFT, player.getId(),
                    source, source.getControllerId()
            ));
            return true;
        }
        return false;
    }
}
