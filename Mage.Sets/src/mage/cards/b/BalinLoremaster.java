package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldThisOrAnotherTriggeredAbility;
import mage.abilities.condition.common.EnduringStoryCondition;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.effects.common.discard.DiscardHandDrawSameNumberSourceEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.keyword.StoriedAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 *
 * @author miesma
 */
public final class BalinLoremaster extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent(SubType.DWARF, "Dwarf");

    public BalinLoremaster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.DWARF);
        this.subtype.add(SubType.BARD);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Storied
        this.addAbility(new StoriedAbility());

        // Whenever Balin or another Dwarf you control enters, you may discard your hand.
        // Draw X cards, where X is the number of cards discarded this way.
        // If you have an enduring story, Balin deals X damage to each opponent.
        this.addAbility(new EntersBattlefieldThisOrAnotherTriggeredAbility(
                new BalinLoremasterEffect(), filter, true, true
        ).addHint(new ConditionHint(EnduringStoryCondition.instance)));
    }

    private BalinLoremaster(final BalinLoremaster card) {
        super(card);
    }

    @Override
    public BalinLoremaster copy() {
        return new BalinLoremaster(this);
    }
}

class BalinLoremasterEffect extends OneShotEffect {

    BalinLoremasterEffect() {
        super(Outcome.Benefit);
        staticText = "you may discard your hand. Draw X cards, " +
                "where X is the number of cards discarded this way. " +
                "If you have an enduring story, Balin deals X damage to each opponent.";
    }

    private BalinLoremasterEffect(final BalinLoremasterEffect effect) {
        super(effect);
    }

    @Override
    public BalinLoremasterEffect copy() {
        return new BalinLoremasterEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        int amount = player.getHand().getCards(game).size();
        //Discard your hand.
        //Draw X cards, where X is the number of cards discarded this way.
        Effect discardEffect = new DiscardHandDrawSameNumberSourceEffect();
        discardEffect.apply(game,source);

        // If you have an enduring story, Balin deals X damage to each opponent.
        if (EnduringStoryCondition.instance.apply(game,source)) {
            Effect damageEffect = new DamagePlayersEffect(amount, TargetController.OPPONENT);
            damageEffect.apply(game,source);
        }
        return true;
    }
}
