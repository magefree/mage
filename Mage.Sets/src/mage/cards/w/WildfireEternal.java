package mage.cards.w;

import java.util.UUID;
import mage.ApprovingObject;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksAndIsNotBlockedTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.AfflictAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.common.FilterInstantOrSorceryCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInHand;

/**
 *
 * @author spjspj
 */
public final class WildfireEternal extends CardImpl {

    public WildfireEternal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");

        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.JACKAL);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // Afflict 4
        addAbility(new AfflictAbility(4));

        // Whenever Wildfire Eternal attacks and isn't blocked, you may cast an instant or sorcery card from your hand without paying its mana cost.
        this.addAbility(new AttacksAndIsNotBlockedTriggeredAbility(new WildfireEternalCastEffect(), false, true));
    }

    public WildfireEternal(final WildfireEternal card) {
        super(card);
    }

    @Override
    public WildfireEternal copy() {
        return new WildfireEternal(this);
    }
}

class WildfireEternalCastEffect extends OneShotEffect {

    public WildfireEternalCastEffect() {
        super(Outcome.Benefit);
        this.staticText = "you may cast an instant or sorcery card "
                + "from your hand without paying its mana cost";
    }

    public WildfireEternalCastEffect(final WildfireEternalCastEffect effect) {
        super(effect);
    }

    @Override
    public WildfireEternalCastEffect copy() {
        return new WildfireEternalCastEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            FilterCard filter = new FilterInstantOrSorceryCard();
            int cardsToCast = controller.getHand().count(filter, source.getControllerId(), source.getSourceId(), game);
            if (cardsToCast > 0
                    && controller.chooseUse(outcome, "Cast an instant or sorcery card from your "
                            + "hand without paying its mana cost?", source, game)) {
                TargetCardInHand target = new TargetCardInHand(filter);
                controller.chooseTarget(outcome, target, source, game);
                Card card = game.getCard(target.getFirstTarget());
                if (card != null) {
                    game.getState().setValue("PlayFromNotOwnHandZone" + card.getId(), Boolean.TRUE);
                    controller.cast(controller.chooseAbilityForCast(card, game, true),
                            game, true, new ApprovingObject(source, game));
                    game.getState().setValue("PlayFromNotOwnHandZone" + card.getId(), null);
                }
            }
            return true;
        }
        return false;
    }
}
