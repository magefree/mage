package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.MillCardsControllerEffect;
import mage.abilities.keyword.CrewAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CarrionCruiser extends CardImpl {

    public CarrionCruiser(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}{B}");

        this.subtype.add(SubType.VEHICLE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // When this Vehicle enters, mill two cards. Then return a creature or Vehicle card from your graveyard to your hand.
        Ability ability = new EntersBattlefieldTriggeredAbility(new MillCardsControllerEffect(2));
        ability.addEffect(new CarrionCruiserEffect());
        this.addAbility(ability);

        // Crew 1
        this.addAbility(new CrewAbility(1));
    }

    private CarrionCruiser(final CarrionCruiser card) {
        super(card);
    }

    @Override
    public CarrionCruiser copy() {
        return new CarrionCruiser(this);
    }
}

class CarrionCruiserEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCard("creature or Vehicle card");

    static {
        filter.add(Predicates.or(
                CardType.CREATURE.getPredicate(),
                SubType.VEHICLE.getPredicate()
        ));
    }

    CarrionCruiserEffect() {
        super(Outcome.Benefit);
        staticText = "Then return a creature or Vehicle card from your graveyard to your hand";
    }

    private CarrionCruiserEffect(final CarrionCruiserEffect effect) {
        super(effect);
    }

    @Override
    public CarrionCruiserEffect copy() {
        return new CarrionCruiserEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null || player.getGraveyard().count(filter, game) < 1) {
            return false;
        }
        TargetCard target = new TargetCardInYourGraveyard(filter);
        target.withNotTarget(true);
        player.choose(outcome, player.getGraveyard(), target, source, game);
        Card card = game.getCard(target.getFirstTarget());
        return card != null && player.moveCards(card, Zone.HAND, source, game);
    }
}
