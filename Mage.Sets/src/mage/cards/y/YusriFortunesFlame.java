package mage.cards.y;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.CastFromHandWithoutPayingManaCostEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class YusriFortunesFlame extends CardImpl {

    public YusriFortunesFlame(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.EFREET);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever Yusri, Fortune's Flame attacks, choose a number between 1 and 5. Flip that many coins. For each flip you win, draw a card. For each flip you lose, Yursi deals 2 damage to you. You you won five flips this way, you may cast spells from your hand this turn without paying their mana costs.
        this.addAbility(new AttacksTriggeredAbility(new YusriFortunesFlameEffect(), false));
    }

    private YusriFortunesFlame(final YusriFortunesFlame card) {
        super(card);
    }

    @Override
    public YusriFortunesFlame copy() {
        return new YusriFortunesFlame(this);
    }
}

class YusriFortunesFlameEffect extends OneShotEffect {

    YusriFortunesFlameEffect() {
        super(Outcome.Benefit);
        staticText = "choose a number between 1 and 5. Flip that many coins. For each flip you win, draw a card. " +
                "For each flip you lose, {this} deals 2 damage to you. If you won five flips this way, " +
                "you may cast spells from your hand this turn without paying their mana costs";
    }

    private YusriFortunesFlameEffect(final YusriFortunesFlameEffect effect) {
        super(effect);
    }

    @Override
    public YusriFortunesFlameEffect copy() {
        return new YusriFortunesFlameEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        int flips = player.getAmount(1, 5, "Choose a number between 1 and 5", game);
        int wins = 0;
        int losses = 0;
        for (int i = 0; i < flips; i++) {
            if (player.flipCoin(source, game, true)) {
                wins++;
            } else {
                losses++;
            }
        }
        player.drawCards(wins, source, game);
        player.damage(2 * losses, source.getSourceId(), source, game);
        if (wins >= 5) {
            ContinuousEffect effect = new CastFromHandWithoutPayingManaCostEffect();
            effect.setDuration(Duration.EndOfTurn);
            game.addEffect(effect, source);
        }
        return true;
    }
}
