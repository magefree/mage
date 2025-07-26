package mage.cards.e;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldOrAttacksSourceTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.ForetellAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInHand;
import mage.watchers.common.ForetoldWatcher;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class EtherealValkyrie extends CardImpl {

    public EtherealValkyrie(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{W}{U}");

        this.subtype.add(SubType.SPIRIT);
        this.subtype.add(SubType.ANGEL);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever Ethereal Valkyrie enters the battlefield or attacks, draw a card, then exile a card from your hand face down. It becomes foretold. Its foretell cost is its mana cost reduced by {2}.
        this.addAbility(new EntersBattlefieldOrAttacksSourceTriggeredAbility(new EtherealValkyrieEffect()), new ForetoldWatcher());
    }

    private EtherealValkyrie(final EtherealValkyrie card) {
        super(card);
    }

    @Override
    public EtherealValkyrie copy() {
        return new EtherealValkyrie(this);
    }
}

class EtherealValkyrieEffect extends OneShotEffect {

    EtherealValkyrieEffect() {
        super(Outcome.Benefit);
        this.staticText = "draw a card, then exile a card from your hand face down. " +
                "It becomes foretold. " +
                "Its foretell cost is its mana cost reduced by {2}";
    }

    private EtherealValkyrieEffect(final EtherealValkyrieEffect effect) {
        super(effect);
    }

    @Override
    public EtherealValkyrieEffect copy() {
        return new EtherealValkyrieEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        controller.drawCards(1, source, game);
        TargetCardInHand targetCard = new TargetCardInHand(StaticFilters.FILTER_CARD).withChooseHint("to exile face down; it becomes foretold");
        if (!controller.chooseTarget(Outcome.Benefit, targetCard, source, game)) {
            return false;
        }
        Card card = game.getCard(targetCard.getFirstTarget());
        if (card == null) {
            return false;
        }
        return ForetellAbility.doExileBecomesForetold(card, game, source, 2);
    }
}
