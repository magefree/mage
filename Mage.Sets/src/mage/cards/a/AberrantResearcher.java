
package mage.cards.a;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class AberrantResearcher extends TransformingDoubleFacedCard {

    public AberrantResearcher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN, SubType.INSECT}, "{3}{U}",
                "Perfected Form",
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.INSECT, SubType.HORROR}, "U");

        this.getLeftHalfCard().setPT(3, 2);
        this.getRightHalfCard().setPT(5, 4);

        // Flying
        this.getLeftHalfCard().addAbility(FlyingAbility.getInstance());

        // At the beginning of your upkeep, put the top card of your library into your graveyard. If it's an instant or sorcery card, transform Aberrant Researcher.
        this.getLeftHalfCard().addAbility(new BeginningOfUpkeepTriggeredAbility(new AberrantResearcherEffect()));

        // Perfected Form
        // Flying
        this.getRightHalfCard().addAbility(FlyingAbility.getInstance());
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

    AberrantResearcherEffect() {
        super(Outcome.Benefit);
        staticText = "mill a card. If an instant or sorcery card was milled this way, transform {this}";
    }

    private AberrantResearcherEffect(final AberrantResearcherEffect effect) {
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
