package mage.cards.h;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.MillCardsEachPlayerEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.TargetController;
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
public final class HaloChargedSkaab extends CardImpl {

    public HaloChargedSkaab(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{U}");

        this.subtype.add(SubType.ZOMBIE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // When Halo-Charged Skaab enters the battlefield, each player mills two cards. Then you may put an instant, sorcery, or battle card from your graveyard on top of your library.
        Ability ability = new EntersBattlefieldTriggeredAbility(
                new MillCardsEachPlayerEffect(2, TargetController.EACH_PLAYER)
        );
        ability.addEffect(new HaloChargedSkaabEffect());
        this.addAbility(ability);
    }

    private HaloChargedSkaab(final HaloChargedSkaab card) {
        super(card);
    }

    @Override
    public HaloChargedSkaab copy() {
        return new HaloChargedSkaab(this);
    }
}

class HaloChargedSkaabEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCard("instant, sorcery, or battle card from your graveyard");

    static {
        filter.add(Predicates.or(
                CardType.INSTANT.getPredicate(),
                CardType.SORCERY.getPredicate(),
                CardType.BATTLE.getPredicate()
        ));
    }

    HaloChargedSkaabEffect() {
        super(Outcome.Benefit);
        staticText = "Then you may put an instant, sorcery, or battle card from your graveyard on top of your library";
    }

    private HaloChargedSkaabEffect(final HaloChargedSkaabEffect effect) {
        super(effect);
    }

    @Override
    public HaloChargedSkaabEffect copy() {
        return new HaloChargedSkaabEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        TargetCard target = new TargetCardInYourGraveyard(0, 1, filter, true);
        player.choose(outcome, target, source, game);
        Card card = game.getCard(target.getFirstTarget());
        return card != null && player.putCardsOnTopOfLibrary(card, game, source, false);
    }
}
