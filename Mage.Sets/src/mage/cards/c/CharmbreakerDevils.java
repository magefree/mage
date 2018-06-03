
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.OnEventTriggeredAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.FilterSpell;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.game.events.GameEvent.EventType;
import mage.players.Player;
import mage.util.RandomUtil;

/**
 *
 * @author North
 */
public final class CharmbreakerDevils extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("instant or sorcery card");

    static {
        filter.add(Predicates.or(
                new CardTypePredicate(CardType.INSTANT),
                new CardTypePredicate(CardType.SORCERY)));
    }

    public CharmbreakerDevils(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{R}");
        this.subtype.add(SubType.DEVIL);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // At the beginning of your upkeep, return an instant or sorcery card at random from your graveyard to your hand.
        this.addAbility(new OnEventTriggeredAbility(EventType.UPKEEP_STEP_PRE, "beginning of your upkeep", new CharmbreakerDevilsEffect(), false));
        // Whenever you cast an instant or sorcery spell, Charmbreaker Devils gets +4/+0 until end of turn.
        this.addAbility(new SpellCastControllerTriggeredAbility(new BoostSourceEffect(4, 0, Duration.EndOfTurn), filter, false));
    }

    public CharmbreakerDevils(final CharmbreakerDevils card) {
        super(card);
    }

    @Override
    public CharmbreakerDevils copy() {
        return new CharmbreakerDevils(this);
    }
}

class CharmbreakerDevilsEffect extends OneShotEffect {

    public CharmbreakerDevilsEffect() {
        super(Outcome.ReturnToHand);
        this.staticText = "return an instant or sorcery card at random from your graveyard to your hand";
    }

    public CharmbreakerDevilsEffect(final CharmbreakerDevilsEffect effect) {
        super(effect);
    }

    @Override
    public CharmbreakerDevilsEffect copy() {
        return new CharmbreakerDevilsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            FilterCard filter = new FilterCard("instant or sorcery card");
            filter.add(Predicates.or(
                    new CardTypePredicate(CardType.INSTANT),
                    new CardTypePredicate(CardType.SORCERY)));
            Card[] cards = player.getGraveyard().getCards(filter, game).toArray(new Card[0]);
            if (cards.length > 0) {
                Card card = cards[RandomUtil.nextInt(cards.length)];
                card.moveToZone(Zone.HAND, source.getSourceId(), game, true);
                game.informPlayers(new StringBuilder("Charmbreaker Devils: ").append(card.getName()).append(" returned to the hand of ").append(player.getLogName()).toString());
                return true;
            }
        }
        return false;
    }
}
