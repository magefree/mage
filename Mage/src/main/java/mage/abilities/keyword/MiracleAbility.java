package mage.abilities.keyword;

import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;
import mage.watchers.common.MiracleWatcher;

/**
 * 702.92. Miracle
 * <p>
 * 702.92a Miracle is a static ability linked to a triggered ability (see rule
 * 603.10). "Miracle [cost]" means "You may reveal this card from your hand as
 * you draw it if it's the first card you've drawn this turn. When you reveal
 * this card this way, you may cast it by paying [cost] rather than its mana
 * cost."
 * <p>
 * 702.92b If a player chooses to reveal a card using its miracle ability, they
 * play with that card revealed until that card leaves their hand, that ability
 * resolves, or that ability otherwise leaves the stack.
 * <p>
 * You can cast a card for its miracle cost only as the miracle triggered
 * ability resolves. If you don't want to cast it at that time (or you can't
 * cast it, perhaps because there are no legal targets available), you won't be
 * able to cast it later for the miracle cost.
 * <p>
 * RULINGS: You still draw the card, whether you use the miracle ability or not.
 * Any ability that triggers whenever you draw a card, for example, will
 * trigger. If you don't cast the card using its miracle ability, it will remain
 * in your hand.
 * <p>
 * You can reveal and cast a card with miracle on any turn, not just your own,
 * if it's the first card you've drawn that turn.
 * <p>
 * You don't have to reveal a drawn card with miracle if you don't wish to cast
 * it at that time.
 * <p>
 * You can cast a card for its miracle cost only as the miracle triggered
 * ability resolves. If you don't want to cast it at that time (or you can't
 * cast it, perhaps because there are no legal targets available), you won't be
 * able to cast it later for the miracle cost.
 * <p>
 * You cast the card with miracle during the resolution of the triggered
 * ability. Ignore any timing restrictions based on the card's type.
 * <p>
 * It's important to reveal a card with miracle before it is mixed with the
 * other cards in your hand.
 * <p>
 * Multiple card draws are always treated as a sequence of individual card
 * draws. For example, if you haven't drawn any cards yet during a turn and cast
 * a spell that instructs you to draw three cards, you'll draw them one at a
 * time. Only the first card drawn this way may be revealed and cast using its
 * miracle ability.
 * <p>
 * If the card with miracle leaves your hand before the triggered ability
 * resolves, you won't be able to cast it using its miracle ability.
 * <p>
 * You draw your opening hand before any turn begins. Cards you draw for your
 * opening hand can't be cast using miracle.
 *
 * @author noxx, LevelX2
 */
public class MiracleAbility extends TriggeredAbilityImpl {

    private final String miracleCost;

    public String getMiracleCost() {
        return miracleCost;
    }

    public MiracleAbility(String miracleCost) {
        super(Zone.HAND, new MiracleEffect(miracleCost), true);
        this.miracleCost = miracleCost;
        this.addWatcher(new MiracleWatcher());
    }

    private MiracleAbility(final MiracleAbility ability) {
        super(ability);
        this.miracleCost = ability.miracleCost;
    }

    @Override
    public MiracleAbility copy() {
        return new MiracleAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.MIRACLE_CARD_REVEALED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!event.getTargetId().equals(this.getSourceId()) || !("" + this.getId()).equals(event.getData())) {
            return false;
        }
        // Refer to the card at the zone it is now (hand)
        this.getEffects().setTargetPointer(new FixedTarget(game.getCard(event.getTargetId()), game));
        return true;
    }

    @Override
    public String getRule() {
        return "Miracle " + miracleCost + " <i>(You may cast this card for its miracle cost when you draw it if it's the first card you drew this turn.)</i>";
    }
}

class MiracleEffect extends OneShotEffect {

    private final String miracleCost;

    public MiracleEffect(String miracleCost) {
        super(Outcome.Benefit);
        this.miracleCost = miracleCost;
    }

    protected MiracleEffect(final MiracleEffect effect) {
        super(effect);
        this.miracleCost = effect.miracleCost;
    }

    @Override
    public MiracleEffect copy() {
        return new MiracleEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Card card = game.getCard(getTargetPointer().getFirst(game, source));
        return player != null && card != null && !card.isLand(game)
                && CardUtil.castSingle(player, source, game, card, new ManaCostsImpl<>(miracleCost));
    }
}
