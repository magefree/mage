package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldOrAttacksSourceTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardAndOrCardInGraveyard;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class SludgeTitan extends CardImpl {

    public SludgeTitan(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B/G}{B/G}");

        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.GIANT);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Whenever Sludge Titan enters the battlefield or attacks, mill five cards. You may put a creature card and/or a land card from among them into your hand.
        this.addAbility(new EntersBattlefieldOrAttacksSourceTriggeredAbility(new SludgeTitanEffect()));
    }

    private SludgeTitan(final SludgeTitan card) {
        super(card);
    }

    @Override
    public SludgeTitan copy() {
        return new SludgeTitan(this);
    }
}

class SludgeTitanEffect extends OneShotEffect {

    SludgeTitanEffect() {
        super(Outcome.Benefit);
        this.staticText = "mill five cards. "
                + "You may put a creature card and/or a land card from among them into your hand";
    }

    private SludgeTitanEffect(final SludgeTitanEffect effect) {
        super(effect);
    }

    @Override
    public SludgeTitanEffect copy() {
        return new SludgeTitanEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Cards cards = controller.millCards(5, source, game);
        game.getState().processAction(game);
        if (!cards.isEmpty()) {
            TargetCard target = new TargetCardAndOrCardInGraveyard(CardType.CREATURE, CardType.LAND);
            controller.choose(Outcome.DrawCard, cards, target, source, game);
            Cards toHand = new CardsImpl();
            toHand.addAll(target.getTargets());
            controller.moveCards(toHand, Zone.HAND, source, game);
        }
        return true;
    }
}
