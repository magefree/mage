package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.WardAbility;
import mage.cards.Card;
import mage.cards.CardSetInfo;
import mage.cards.ModalDoubleFacesCard;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreatureOrPlaneswalkerPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.TargetPermanent;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

import static mage.constants.Outcome.Benefit;

/**
 * @author TheElk801
 */
public final class TorrentSculptor extends ModalDoubleFacesCard {

    public TorrentSculptor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.MERFOLK, SubType.WIZARD}, "{2}{U}{U}",
                "Flamethrower Sonata", new CardType[]{CardType.SORCERY}, new SubType[]{}, "{1}{R}");

        // 1.
        // Torrent Sculptor
        // Creature - Merfolk Wizard
        this.getLeftHalfCard().setPT(2, 2);

        // Ward {2}
        this.getLeftHalfCard().addAbility(new WardAbility(new ManaCostsImpl<>("{2}")));

        // When Torrent Sculptor enters the battlefield, exile an instant or sorcery card from your graveyard. Put a number of +1/+1 counters on Torrent Sculptor equal to half that card's mana value, rounded up.
        this.getLeftHalfCard().addAbility(new EntersBattlefieldTriggeredAbility(new TorrentSculptorEffect()));

        // 2.
        // Flamethrower Sonata
        // Sorcery
        // Discard a card, then draw a card. When you discard an instant or sorcery card this way, Flamethrower Sonata deals damage equal to that card's mana value to target creature or planeswalker you don't control.
        this.getRightHalfCard().getSpellAbility().addEffect(new FlamethrowerSonataEffect());
    }

    private TorrentSculptor(final TorrentSculptor card) {
        super(card);
    }

    @Override
    public TorrentSculptor copy() {
        return new TorrentSculptor(this);
    }
}

class TorrentSculptorEffect extends OneShotEffect {

    TorrentSculptorEffect() {
        super(Benefit);
        staticText = "exile an instant or sorcery card from your graveyard. " +
                "Put a number of +1/+1 counters on {this} equal to half that card's mana value, rounded up.";
    }

    private TorrentSculptorEffect(final TorrentSculptorEffect effect) {
        super(effect);
    }

    @Override
    public TorrentSculptorEffect copy() {
        return new TorrentSculptorEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null || player.getGraveyard().count(StaticFilters.FILTER_CARD_INSTANT_OR_SORCERY, game) < 1) {
            return false;
        }
        TargetCard targetCard = new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_INSTANT_OR_SORCERY);
        targetCard.setNotTarget(true);
        player.choose(Outcome.Exile, targetCard, source, game);
        Card card = game.getCard(targetCard.getFirstTarget());
        if (card == null) {
            return false;
        }
        int counters = card.getManaValue();
        counters = Math.floorDiv(counters, 2) + counters % 2;
        player.moveCards(card, Zone.EXILED, source, game);
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        if (permanent != null) {
            permanent.addCounters(CounterType.P1P1.createInstance(counters), player.getId(), source, game);
        }
        return true;
    }
}

class FlamethrowerSonataEffect extends OneShotEffect {

    private static final FilterPermanent filter
            = new FilterCreatureOrPlaneswalkerPermanent("creature or planeswalker you don't control");

    static {
        filter.add(TargetController.NOT_YOU.getControllerPredicate());
    }

    FlamethrowerSonataEffect() {
        super(Outcome.Benefit);
        staticText = "Discard a card, then draw a card. When you discard an instant or sorcery card this way, " +
                "{this} deals damage equal to that card's mana value to target creature or planeswalker you don't control.";
    }

    private FlamethrowerSonataEffect(final FlamethrowerSonataEffect effect) {
        super(effect);
    }

    @Override
    public FlamethrowerSonataEffect copy() {
        return new FlamethrowerSonataEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Card card = player.discardOne(false, false, source, game);
        player.drawCards(1, source, game);
        if (card == null || !card.isInstantOrSorcery(game)) {
            return true;
        }
        ReflexiveTriggeredAbility ability = new ReflexiveTriggeredAbility(
                new DamageTargetEffect(card.getManaValue()),
                false, "{this} deals damage equal to that card's mana value " +
                "to target creature or planeswalker you don't control"
        );
        ability.addTarget(new TargetPermanent(filter));
        game.fireReflexiveTriggeredAbility(ability, source);
        return true;
    }
}
