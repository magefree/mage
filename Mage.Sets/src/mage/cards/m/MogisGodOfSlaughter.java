
package mage.cards.m;

import java.util.Locale;
import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.dynamicvalue.common.DevotionCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.continuous.LoseCreatureTypeSourceEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import static mage.filter.StaticFilters.FILTER_CONTROLLED_CREATURE_SHORT_TEXT;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.util.CardUtil;

/**
 *
 * @author LevelX2
 */
public final class MogisGodOfSlaughter extends CardImpl {

    public MogisGodOfSlaughter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "{2}{B}{R}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.GOD);

        this.power = new MageInt(7);
        this.toughness = new MageInt(5);

        // Indestructible
        this.addAbility(IndestructibleAbility.getInstance());
        // As long as your devotion to black and red is less than seven, Mogis isn't a creature.
        Effect effect = new LoseCreatureTypeSourceEffect(new DevotionCount(ColoredManaSymbol.B, ColoredManaSymbol.R), 7);
        effect.setText("As long as your devotion to black and red is less than seven, Mogis isn't a creature");
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect));

        // At the beginning of each opponent's upkeep, Mogis deals 2 damage to that player unless he or she sacrifices a creature.
        effect = new DoUnlessTargetPaysCost(new DamageTargetEffect(2, true, "that player"),
                new SacrificeTargetCost(new TargetControlledCreaturePermanent(FILTER_CONTROLLED_CREATURE_SHORT_TEXT)),
                "Sacrifice a creature? (otherwise you get 2 damage)");
        effect.setText("Mogis deals 2 damage to that player unless he or she sacrifices a creature");
        Ability ability = new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, effect, TargetController.OPPONENT, false, true);
        this.addAbility(ability);
    }

    public MogisGodOfSlaughter(final MogisGodOfSlaughter card) {
        super(card);
    }

    @Override
    public MogisGodOfSlaughter copy() {
        return new MogisGodOfSlaughter(this);
    }
}

class DoUnlessTargetPaysCost extends OneShotEffect {

    private final OneShotEffect executingEffect;
    private final Cost cost;
    private final String userMessage;

    public DoUnlessTargetPaysCost(OneShotEffect effect, Cost cost) {
        this(effect, cost, null);
    }

    public DoUnlessTargetPaysCost(OneShotEffect effect, Cost cost, String userMessage) {
        super(Outcome.Benefit);
        this.executingEffect = effect;
        this.cost = cost;
        this.userMessage = userMessage;
    }

    public DoUnlessTargetPaysCost(final DoUnlessTargetPaysCost effect) {
        super(effect);
        this.executingEffect = (OneShotEffect) effect.executingEffect.copy();
        this.cost = effect.cost.copy();
        this.userMessage = effect.userMessage;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(targetPointer.getFirst(game, source));
        MageObject mageObject = game.getObject(source.getSourceId());
        if (player != null && mageObject != null) {
            String message = userMessage;
            if (message == null) {
                message = getCostText() + " to prevent " + executingEffect.getText(source.getModes().getMode()) + '?';
            }
            message = CardUtil.replaceSourceName(message, mageObject.getLogName());
            cost.clearPaid();
            if (cost.canPay(source, source.getSourceId(), player.getId(), game) && player.chooseUse(executingEffect.getOutcome(), message, source, game)) {
                cost.pay(source, game, source.getSourceId(), player.getId(), false, null);
            }
            if (!cost.isPaid()) {
                executingEffect.setTargetPointer(this.targetPointer);
                return executingEffect.apply(game, source);
            }
            return true;
        }
        return false;
    }

    @Override
    public String getText(Mode mode) {
        if (!staticText.isEmpty()) {
            return staticText;
        }
        StringBuilder sb = new StringBuilder(executingEffect.getText(mode));
        sb.append("unless he or she");
        sb.append(getCostText());
        return sb.toString();
    }

    private String getCostText() {
        StringBuilder sb = new StringBuilder();
        String costText = cost.getText();
        if (costText != null
                && !costText.toLowerCase(Locale.ENGLISH).startsWith("discard")
                && !costText.toLowerCase(Locale.ENGLISH).startsWith("sacrifice")
                && !costText.toLowerCase(Locale.ENGLISH).startsWith("remove")) {
            sb.append("pay ");
        }
        return sb.append(costText).toString();
    }

    @Override
    public DoUnlessTargetPaysCost copy() {
        return new DoUnlessTargetPaysCost(this);
    }
}
