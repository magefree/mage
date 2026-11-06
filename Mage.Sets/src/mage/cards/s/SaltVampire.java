package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.LifelinkAbility;
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
import mage.target.TargetCard;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author muz
 */
public final class SaltVampire extends CardImpl {

    public SaltVampire(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.SHAPESHIFTER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // When this creature enters, look at target opponent's hand. You may choose a nonland card from it. If you do, that player exiles that card, then draws a card.
        Ability ability = new EntersBattlefieldTriggeredAbility(new SaltVampireEffect());
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    private SaltVampire(final SaltVampire card) {
        super(card);
    }

    @Override
    public SaltVampire copy() {
        return new SaltVampire(this);
    }
}

class SaltVampireEffect extends OneShotEffect {

    SaltVampireEffect() {
        super(Outcome.Discard);
        staticText = "look at target opponent's hand. You may choose a nonland card from it. "
            + "If you do, that player exiles that card, then draws a card";
    }

    private SaltVampireEffect(final SaltVampireEffect effect) {
        super(effect);
    }

    @Override
    public SaltVampireEffect copy() {
        return new SaltVampireEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player opponent = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (controller == null || opponent == null) {
            return false;
        }
        controller.lookAtCards(source, "Hand of " + opponent.getName(), opponent.getHand(), game);
        TargetCard target = new TargetCard(0, 1, Zone.HAND, StaticFilters.FILTER_CARD_NON_LAND);
        if (controller.choose(outcome, opponent.getHand(), target, source, game)) {
            Card card = game.getCard(target.getFirstTarget());
            if (card != null) {
                opponent.moveCards(card, Zone.EXILED, source, game);
                opponent.drawCards(1, source, game);
            }
        }
        return true;
    }
}
