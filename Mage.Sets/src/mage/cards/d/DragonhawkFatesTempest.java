package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldOrAttacksSourceTriggeredAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.asthought.PlayFromNotOwnHandZoneTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.targetpointer.FixedTargets;
import mage.util.CardUtil;

import java.util.Set;
import java.util.UUID;

/**
 * @author notgreat
 */
public final class DragonhawkFatesTempest extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("creatures you control with power 4 or greater");

    static {
        filter.add(new PowerPredicate(ComparisonType.OR_GREATER, 4));
    }

    public DragonhawkFatesTempest(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.BIRD);
        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever Dragonhawk enters or attacks, exile the top X cards of your library, where X is the number of creatures you control with power 4 or greater. You may play those cards until your next end step.
        // At the beginning of your next end step, Dragonhawk deals 2 damage to each opponent for each of those cards that are still exiled.
        this.addAbility(new EntersBattlefieldOrAttacksSourceTriggeredAbility(new DragonhawkExileEffect(
                new PermanentsOnBattlefieldCount(filter, null), Duration.UntilYourNextEndStep)
                .withTextOptions("those cards", true)));
    }

    private DragonhawkFatesTempest(final DragonhawkFatesTempest card) {
        super(card);
    }

    @Override
    public DragonhawkFatesTempest copy() {
        return new DragonhawkFatesTempest(this);
    }
}

// Copied from ExileTopXMayPlayUntilEffect but with addDelayedTriggeredAbility
class DragonhawkExileEffect extends OneShotEffect {

    private final DynamicValue amount;
    private final Duration duration;

    public DragonhawkExileEffect(int amount, Duration duration) {
        this(StaticValue.get(amount), duration);
    }

    public DragonhawkExileEffect(DynamicValue amount, Duration duration) {
        super(Outcome.Benefit);
        this.amount = amount.copy();
        this.duration = duration;
        makeText(amount.toString().equals("1") ? "that card" : "those cards", duration == Duration.EndOfTurn);
    }

    private DragonhawkExileEffect(final DragonhawkExileEffect effect) {
        super(effect);
        this.amount = effect.amount.copy();
        this.duration = effect.duration;
    }

    @Override
    public DragonhawkExileEffect copy() {
        return new DragonhawkExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        int resolvedAmount = amount.calculate(game, source, this);
        Set<Card> cards = controller.getLibrary().getTopCards(game, resolvedAmount);
        if (cards.isEmpty()) {
            return true;
        }
        controller.moveCardsToExile(cards, source, game, true, CardUtil.getExileZoneId(game, source), CardUtil.getSourceName(game, source));
        // remove cards that could not be moved to exile
        cards.removeIf(card -> !Zone.EXILED.equals(game.getState().getZone(card.getId())));
        if (!cards.isEmpty()) {
            game.addEffect(new PlayFromNotOwnHandZoneTargetEffect(Zone.EXILED, duration)
                    .setTargetPointer(new FixedTargets(cards, game)), source);
            game.addDelayedTriggeredAbility(new AtTheBeginOfNextEndStepDelayedTriggeredAbility(
                    new DragonhawkFatesTempestDamageEffect(new FixedTargets(cards, game)), TargetController.YOU), source);
        }
        return true;
    }

    /**
     * [Until end of turn, ] you may play [refCardText] [this turn]
     */
    public DragonhawkExileEffect withTextOptions(String refCardText, boolean durationRuleAtEnd) {
        makeText(refCardText, durationRuleAtEnd);
        return this;
    }

    private void makeText(String refCardText, boolean durationRuleAtEnd) {
        String text = "exile the top ";
        boolean singular = amount.toString().equals("1");
        text += singular ? "card" : CardUtil.numberToText(amount.toString()) + " cards";
        if (amount.toString().equals("X")) {
            text += " of your library, where X is " + amount.getMessage() + ". ";
        } else {
            text += " of your library. ";
        }
        if (durationRuleAtEnd) {
            text += "You may play " + refCardText + ' ' + (duration == Duration.EndOfTurn ? "this turn" : duration.toString());
        } else {
            text += CardUtil.getTextWithFirstCharUpperCase(duration.toString()) + ", you may play " + refCardText;
        }
        this.staticText = text+". "+DragonhawkFatesTempestDamageEffect.STATIC_TEXT;
    }
}

class DragonhawkFatesTempestDamageEffect extends OneShotEffect {
    FixedTargets cards;
    public static String STATIC_TEXT = "{this} deals 2 damage to each opponent for each of those cards that are still exiled.";

    DragonhawkFatesTempestDamageEffect(FixedTargets cards) {
        super(Outcome.Benefit);
        this.setText(STATIC_TEXT);
        this.cards = cards;
    }

    private DragonhawkFatesTempestDamageEffect(final DragonhawkFatesTempestDamageEffect effect) {
        super(effect);
        cards = effect.cards;
    }

    @Override
    public DragonhawkFatesTempestDamageEffect copy() {
        return new DragonhawkFatesTempestDamageEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        int count = cards.getTargets(game, source).size(); //Automatically filters out moved cards
        if (count < 1) {
            return false;
        }
        for (UUID playerId : game.getOpponents(source.getControllerId())) {
            player = game.getPlayer(playerId);
            if (playerId == null) {
                continue;
            }
            player.damage(count * 2, source.getSourceId(), source, game);
        }
        return true;
    }
}
