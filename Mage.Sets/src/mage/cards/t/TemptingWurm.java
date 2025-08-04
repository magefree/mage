package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetCard;

import java.util.UUID;

/**
 * @author Eirkei
 */
public final class TemptingWurm extends CardImpl {

    public TemptingWurm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");
        this.subtype.add(SubType.WURM);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // When Tempting Wurm enters the battlefield, each opponent may put any number of artifact, creature, enchantment, and/or land cards from their hand onto the battlefield.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new TemptingWurmEffect()));
    }

    private TemptingWurm(final TemptingWurm card) {
        super(card);
    }

    @Override
    public TemptingWurm copy() {
        return new TemptingWurm(this);
    }
}

class TemptingWurmEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCard("any number of artifact, creature, enchantment, and/or land cards");

    static {
        filter.add(Predicates.or(
                CardType.ARTIFACT.getPredicate(),
                CardType.CREATURE.getPredicate(),
                CardType.ENCHANTMENT.getPredicate(),
                CardType.LAND.getPredicate()
        ));
    }

    TemptingWurmEffect() {
        super(Outcome.Detriment);
        this.staticText = "each opponent may put any number of artifact, creature, enchantment, and/or land cards from their hand onto the battlefield.";
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());

        if (controller != null) {
            Cards cards = new CardsImpl();

            for (UUID playerId : game.getOpponents(controller.getId())) {
                Player opponent = game.getPlayer(playerId);
                if (opponent != null) {
                    Target target = new TargetCard(0, Integer.MAX_VALUE, Zone.HAND, filter).withChooseHint("put from hand to battlefield");
                    if (target.chooseTarget(Outcome.PutCardInPlay, opponent.getId(), source, game)) {
                        for (UUID cardId : target.getTargets()) {
                            Card card = game.getCard(cardId);
                            if (card != null) {
                                cards.add(card);
                            }
                        }
                    }
                }
            }

            controller.moveCards(cards.getCards(game), Zone.BATTLEFIELD, source, game, false, false, true, null);

            return true;
        }

        return false;
    }

    private TemptingWurmEffect(final TemptingWurmEffect effect) {
        super(effect);
    }

    @Override
    public TemptingWurmEffect copy() {
        return new TemptingWurmEffect(this);
    }
}
