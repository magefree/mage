
package mage.cards.p;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.costs.common.ExileSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.ExileZone;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author spjspj
 */
public final class PlanarGuide extends CardImpl {

    public PlanarGuide(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {3}{W}, Exile Planar Guide: Exile all creatures. At the beginning of the next end step, return those cards to the battlefield under their owners' control.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new PlanarGuideExileEffect(), new ManaCostsImpl<>("{3}{W}"));
        ability.addCost(new ExileSourceCost());
        this.addAbility(ability);
    }

    private PlanarGuide(final PlanarGuide card) {
        super(card);
    }

    @Override
    public PlanarGuide copy() {
        return new PlanarGuide(this);
    }
}

class PlanarGuideExileEffect extends OneShotEffect {

    public PlanarGuideExileEffect() {
        super(Outcome.Detriment);
        staticText = "Exile all creatures. At the beginning of the next end step, return those cards to the battlefield under their owners' control";
    }

    public PlanarGuideExileEffect(final PlanarGuideExileEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        MageObject sourceObject = game.getObject(source);
        Player controller = game.getPlayer(source.getControllerId());
        if (sourceObject != null && controller != null) {
            Set<Card> toExile = new HashSet<>();
            toExile.addAll(game.getBattlefield().getActivePermanents(StaticFilters.FILTER_PERMANENT_CREATURE, source.getControllerId(), source, game));
            controller.moveCardsToExile(toExile, source, game, true, source.getSourceId(), sourceObject.getIdName());
            ExileZone exile = game.getExile().getExileZone(source.getSourceId());
            if (exile != null && !exile.isEmpty()) {
                // Create delayed triggered ability
                AtTheBeginOfNextEndStepDelayedTriggeredAbility delayedAbility = new AtTheBeginOfNextEndStepDelayedTriggeredAbility(new PlanarGuideReturnFromExileEffect());
                game.addDelayedTriggeredAbility(delayedAbility, source);
                return true;
            }
            return true;
        }
        return false;
    }

    @Override
    public PlanarGuideExileEffect copy() {
        return new PlanarGuideExileEffect(this);
    }
}

class PlanarGuideReturnFromExileEffect extends OneShotEffect {

    public PlanarGuideReturnFromExileEffect() {
        super(Outcome.PutCardInPlay);
        staticText = "At the beginning of the next end step, return those cards to the battlefield under their owners' control";
    }

    public PlanarGuideReturnFromExileEffect(final PlanarGuideReturnFromExileEffect effect) {
        super(effect);
    }

    @Override
    public PlanarGuideReturnFromExileEffect copy() {
        return new PlanarGuideReturnFromExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            ExileZone exile = game.getExile().getExileZone(source.getSourceId());
            if (exile != null) {
                controller.moveCards(exile.getCards(game), Zone.BATTLEFIELD, source, game, false, false, true, null);
            }
            return true;
        }
        return false;
    }

}
