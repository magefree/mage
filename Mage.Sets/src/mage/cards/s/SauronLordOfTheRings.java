package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CastSourceTriggeredAbility;
import mage.abilities.effects.common.MillCardsControllerEffect;
import mage.abilities.effects.keyword.AmassEffect;
import mage.abilities.effects.keyword.TheRingTemptsYouEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.predicate.mageobject.CommanderPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SauronLordOfTheRings extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("a commander an opponent controls");

    static {
        filter.add(CommanderPredicate.instance);
        filter.add(TargetController.OPPONENT.getControllerPredicate());
    }

    public SauronLordOfTheRings(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{U}{B}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.AVATAR);
        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(9);
        this.toughness = new MageInt(9);

        // When you cast this spell, amass Orcs 5, mill five cards, then return a creature card from your graveyard to the battlefield.
        Ability ability = new CastSourceTriggeredAbility(
                new AmassEffect(5, SubType.ORC).setText("amass Orcs 5")
        );
        ability.addEffect(new MillCardsControllerEffect(5).concatBy(","));
        ability.addEffect(new SauronLordOfTheRingsEffect());
        this.addAbility(ability);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Whenever a commander an opponent controls dies, the Ring tempts you.
        this.addAbility(new DiesCreatureTriggeredAbility(new TheRingTemptsYouEffect(), false, filter));
    }

    private SauronLordOfTheRings(final SauronLordOfTheRings card) {
        super(card);
    }

    @Override
    public SauronLordOfTheRings copy() {
        return new SauronLordOfTheRings(this);
    }
}

class SauronLordOfTheRingsEffect extends OneShotEffect {

    SauronLordOfTheRingsEffect() {
        super(Outcome.Benefit);
        staticText = ", then return a creature card from your graveyard to the battlefield";
    }

    private SauronLordOfTheRingsEffect(final SauronLordOfTheRingsEffect effect) {
        super(effect);
    }

    @Override
    public SauronLordOfTheRingsEffect copy() {
        return new SauronLordOfTheRingsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null || player.getGraveyard().count(StaticFilters.FILTER_CARD_CREATURE, game) < 1) {
            return false;
        }
        TargetCard target = new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD);
        target.setNotTarget(true);
        player.choose(outcome, target, source, game);
        Card card = game.getCard(target.getFirstTarget());
        return card != null && player.moveCards(card, Zone.BATTLEFIELD, source, game);
    }
}
