package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.dynamicvalue.common.CardsInAllGraveyardsCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CopyEffect;
import mage.cards.*;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInGraveyard;

/**
 *
 * @author emerald000
 */
public final class TheMimeoplasm extends CardImpl {

    public TheMimeoplasm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{G}{U}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.OOZE);

        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // As The Mimeoplasm enters the battlefield, you may exile two creature cards from graveyards. If you do, it enters the battlefield as a copy of one of those cards with a number of additional +1/+1 counters on it equal to the power of the other card.
        this.addAbility(new AsEntersBattlefieldAbility(new TheMimeoplasmEffect(), "you may exile two creature cards from graveyards. If you do, it enters the battlefield as a copy of one of those cards with a number of additional +1/+1 counters on it equal to the power of the other card"));
    }

    private TheMimeoplasm(final TheMimeoplasm card) {
        super(card);
    }

    @Override
    public TheMimeoplasm copy() {
        return new TheMimeoplasm(this);
    }
}

class TheMimeoplasmEffect extends OneShotEffect {

    TheMimeoplasmEffect() {
        super(Outcome.Copy);
    }

    TheMimeoplasmEffect(final TheMimeoplasmEffect effect) {
        super(effect);
    }

    @Override
    public TheMimeoplasmEffect copy() {
        return new TheMimeoplasmEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanentEntering(source.getSourceId());
        if (controller != null && permanent != null) {
            if (new CardsInAllGraveyardsCount(StaticFilters.FILTER_CARD_CREATURE).calculate(game, source, this) >= 2) {
                if (controller.chooseUse(Outcome.Benefit, "Do you want to exile two creature cards from graveyards?", source, game)) {
                    TargetCardInGraveyard targetCopy = new TargetCardInGraveyard(new FilterCreatureCard("creature card to become a copy of"));
                    targetCopy.setNotTarget(true);
                    if (controller.choose(Outcome.Copy, targetCopy, source, game)) {
                        Card cardToCopy = game.getCard(targetCopy.getFirstTarget());
                        if (cardToCopy != null) {
                            FilterCreatureCard filter = new FilterCreatureCard("creature card to determine amount of additional +1/+1 counters");
                            filter.add(Predicates.not(new CardIdPredicate(cardToCopy.getId())));
                            TargetCardInGraveyard targetCounters = new TargetCardInGraveyard(filter);
                            targetCounters.setNotTarget(true);
                            if (controller.choose(Outcome.Copy, targetCounters, source, game)) {
                                Card cardForCounters = game.getCard(targetCounters.getFirstTarget());
                                if (cardForCounters != null) {
                                    Cards cardsToExile = new CardsImpl();
                                    cardsToExile.add(cardToCopy);
                                    cardsToExile.add(cardForCounters);
                                    controller.moveCards(cardsToExile, Zone.EXILED, source, game);
                                    CopyEffect copyEffect = new CopyEffect(Duration.Custom, cardToCopy, source.getSourceId());
                                    game.addEffect(copyEffect, source);
                                    permanent.addCounters(CounterType.P1P1.createInstance(cardForCounters.getPower().getValue()), source.getControllerId(), source, game);
                                }
                            }
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }
}
