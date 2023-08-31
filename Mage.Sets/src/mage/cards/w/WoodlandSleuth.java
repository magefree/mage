package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.MorbidCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.hint.common.MorbidHint;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.util.RandomUtil;

/**
 *
 * @author North
 */
public final class WoodlandSleuth extends CardImpl {

    private static final String staticText = "<i>Morbid</i> &mdash; When {this} enters the battlefield, if a creature died this turn, return a creature card at random from your graveyard to your hand.";

    public WoodlandSleuth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SCOUT);

        this.color.setGreen(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // <i>Morbid</i> &mdash; When Woodland Sleuth enters the battlefield, if a creature died this turn, return a creature card at random from your graveyard to your hand.
        TriggeredAbility ability = new EntersBattlefieldTriggeredAbility(new WoodlandSleuthEffect());
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(ability, MorbidCondition.instance, staticText).addHint(MorbidHint.instance));
    }

    private WoodlandSleuth(final WoodlandSleuth card) {
        super(card);
    }

    @Override
    public WoodlandSleuth copy() {
        return new WoodlandSleuth(this);
    }
}

class WoodlandSleuthEffect extends OneShotEffect {

    public WoodlandSleuthEffect() {
        super(Outcome.ReturnToHand);
        this.staticText = "return a creature card at random from your graveyard to your hand";
    }

    private WoodlandSleuthEffect(final WoodlandSleuthEffect effect) {
        super(effect);
    }

    @Override
    public WoodlandSleuthEffect copy() {
        return new WoodlandSleuthEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            Card[] cards = player.getGraveyard().getCards(StaticFilters.FILTER_CARD_CREATURE, game).toArray(new Card[0]);
            if (cards.length > 0) {
                Card card = cards[RandomUtil.nextInt(cards.length)];
                if (player.moveCards(card, Zone.HAND, source, game)) {
                    game.informPlayers(card.getName() + " returned to the hand of " + player.getLogName());
                    return true;
                }
            }
        }
        return false;
    }
}
