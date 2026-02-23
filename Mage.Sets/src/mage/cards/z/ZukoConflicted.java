package mage.cards.z;

import mage.MageInt;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.mana.BasicManaEffect;
import mage.abilities.triggers.BeginningOfFirstMainTriggeredAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ZukoConflicted extends CardImpl {

    public ZukoConflicted(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // At the beginning of your first main phase, choose one that hasn't been chosen and you lose 2 life --
        // * Draw a card.
        Ability ability = new BeginningOfFirstMainTriggeredAbility(new DrawCardSourceControllerEffect(1).setText("draw"));
        ability.addEffect(new LoseLifeSourceControllerEffect(2).setText(" a card"));
        ability.getModes().setChooseText("choose one that hasn't been chosen and you lose 2 life &mdash;");
        ability.getModes().setLimitUsageByOnce(false);

        // * Put a +1/+1 counter on Zuko.
        ability.addMode(new Mode(new AddCountersSourceEffect(CounterType.P1P1.createInstance()).setText("put a +1/+1 counter"))
                .addEffect(new LoseLifeSourceControllerEffect(2).setText(" on {this}")));

        // * Add {R}.
        ability.addMode(new Mode(new BasicManaEffect(Mana.RedMana(1)).setText("add"))
                .addEffect(new LoseLifeSourceControllerEffect(2).setText(" {R}")));

        // * Exile Zuko, then return him to the battlefield under an opponent's control.
        ability.addMode(new Mode(new ZukoConflictedEffect())
                .addEffect(new LoseLifeSourceControllerEffect(2).setText(" control")));
        this.addAbility(ability);
    }

    private ZukoConflicted(final ZukoConflicted card) {
        super(card);
    }

    @Override
    public ZukoConflicted copy() {
        return new ZukoConflicted(this);
    }
}

class ZukoConflictedEffect extends OneShotEffect {

    ZukoConflictedEffect() {
        super(Outcome.Benefit);
        staticText = "exile {this}, then return him to the battlefield under an opponent's";
    }

    private ZukoConflictedEffect(final ZukoConflictedEffect effect) {
        super(effect);
    }

    @Override
    public ZukoConflictedEffect copy() {
        return new ZukoConflictedEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        if (player == null || permanent == null) {
            return false;
        }
        Card card = permanent.getMainCard();
        player.moveCards(permanent, Zone.EXILED, source, game);
        TargetPlayer target = new TargetOpponent(true);
        player.choose(outcome, target, source, game);
        Player opponent = game.getPlayer(target.getFirstTarget());
        if (opponent != null) {
            opponent.moveCards(card, Zone.BATTLEFIELD, source, game);
        }
        return true;
    }
}
