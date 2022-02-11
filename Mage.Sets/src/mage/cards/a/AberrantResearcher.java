
package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class AberrantResearcher extends CardImpl {

    public AberrantResearcher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.INSECT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        this.secondSideCardClazz = mage.cards.p.PerfectedForm.class;

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // At the beginning of your upkeep, put the top card of your library into your graveyard. If it's an instant or sorcery card, transform Aberrant Researcher.
        this.addAbility(new TransformAbility());
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, new AberrantResearcherEffect(), TargetController.YOU, false));
    }

    private AberrantResearcher(final AberrantResearcher card) {
        super(card);
    }

    @Override
    public AberrantResearcher copy() {
        return new AberrantResearcher(this);
    }
}

class AberrantResearcherEffect extends OneShotEffect {

    public AberrantResearcherEffect() {
        super(Outcome.Benefit);
        staticText = "mill a card. If an instant or sorcery card was milled this way, transform {this}";
    }

    public AberrantResearcherEffect(final AberrantResearcherEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null
                || controller
                .millCards(1, source, game)
                .getCards(game)
                .stream()
                .noneMatch(card -> card.isInstantOrSorcery(game))) {
            return false;
        }
        new TransformSourceEffect().apply(game, source);
        return true;
    }

    @Override
    public AberrantResearcherEffect copy() {
        return new AberrantResearcherEffect(this);
    }
}
