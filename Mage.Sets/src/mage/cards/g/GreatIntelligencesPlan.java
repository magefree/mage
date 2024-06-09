package mage.cards.g;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.choices.FaceVillainousChoice;
import mage.choices.VillainousChoice;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetOpponent;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GreatIntelligencesPlan extends CardImpl {

    public GreatIntelligencesPlan(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{U}{B}");

        // Draw three cards. Then target opponent faces a villainous choice -- They discard three cards, or you may cast a spell from your hand without paying its mana cost.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(3));
        this.getSpellAbility().addEffect(new GreatIntelligencesPlanEffect());
        this.getSpellAbility().addTarget(new TargetOpponent());
    }

    private GreatIntelligencesPlan(final GreatIntelligencesPlan card) {
        super(card);
    }

    @Override
    public GreatIntelligencesPlan copy() {
        return new GreatIntelligencesPlan(this);
    }
}

class GreatIntelligencesPlanEffect extends OneShotEffect {

    private static final FaceVillainousChoice choice = new FaceVillainousChoice(
            Outcome.Discard, new GreatIntelligencesPlanFirstChoice(), new GreatIntelligencesPlanSecondChoice()
    );

    GreatIntelligencesPlanEffect() {
        super(Outcome.Benefit);
        staticText = "Then target opponent " + choice.generateRule();
    }

    private GreatIntelligencesPlanEffect(final GreatIntelligencesPlanEffect effect) {
        super(effect);
    }

    @Override
    public GreatIntelligencesPlanEffect copy() {
        return new GreatIntelligencesPlanEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(getTargetPointer().getFirst(game, source));
        return player != null && choice.faceChoice(player, game, source);
    }
}

class GreatIntelligencesPlanFirstChoice extends VillainousChoice {
    GreatIntelligencesPlanFirstChoice() {
        super("They discard three cards", "You discard three cards");
    }

    @Override
    public boolean doChoice(Player player, Game game, Ability source) {
        return !player.discard(3, false, false, source, game).isEmpty();
    }
}

class GreatIntelligencesPlanSecondChoice extends VillainousChoice {
    GreatIntelligencesPlanSecondChoice() {
        super("you may cast a spell from your hand without paying its mana cost", "{controller} may cast a free spell from their hand");
    }

    @Override
    public boolean doChoice(Player player, Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        return controller != null && !controller.getHand().isEmpty()
                && CardUtil.castSpellWithAttributesForFree(
                controller, source, game, new CardsImpl(controller.getHand()), StaticFilters.FILTER_CARD
        );
    }
}
