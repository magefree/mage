package mage.cards.w;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterLandCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author DominionSpy
 */
public final class WorldsoulsRage extends CardImpl {

    public WorldsoulsRage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{R}{G}");

        // Worldsoul's Rage deals X damage to any target. Put up to X land cards from your hand and/or graveyard onto the battlefield tapped.
        this.getSpellAbility().addEffect(new DamageTargetEffect(ManacostVariableValue.REGULAR));
        this.getSpellAbility().addEffect(new WorldsoulsRageEffect());
        this.getSpellAbility().addTarget(new TargetAnyTarget());
    }

    private WorldsoulsRage(final WorldsoulsRage card) {
        super(card);
    }

    @Override
    public WorldsoulsRage copy() {
        return new WorldsoulsRage(this);
    }
}

class WorldsoulsRageEffect extends OneShotEffect {

    WorldsoulsRageEffect() {
        super(Outcome.PutLandInPlay);
        staticText = "put up to X land cards from your hand and/or graveyard onto the battlefield tapped.";
    }

    private WorldsoulsRageEffect(final WorldsoulsRageEffect effect) {
        super(effect);
    }

    @Override
    public WorldsoulsRageEffect copy() {
        return new WorldsoulsRageEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }

        FilterCard filter = new FilterLandCard();

        Cards landCards = new CardsImpl();
        landCards.addAllCards(controller.getHand().getCards(filter, source.getControllerId(), source, game));
        landCards.addAllCards(controller.getGraveyard().getCards(filter, source.getControllerId(), source, game));
        if (landCards.isEmpty()) {
            return false;
        }

        int maxTargets = source.getManaCostsToPay().getX();
        if (maxTargets == 0) {
            return false;
        }

        TargetCard target = new TargetCard(0, maxTargets, Zone.ALL, filter);
        target.withNotTarget(true);
        controller.chooseTarget(outcome, landCards, target, source, game);

        Set<Card> chosenCards = target.getTargets()
                .stream()
                .map(game::getCard)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
        if (chosenCards.isEmpty()) {
            return false;
        }

        controller.moveCards(chosenCards, Zone.BATTLEFIELD, source, game, true, false, false, null);
        return true;
    }
}
