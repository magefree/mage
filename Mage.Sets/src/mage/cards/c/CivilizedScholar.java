package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.InvertCondition;
import mage.abilities.condition.common.AttackedThisTurnSourceCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.TapSourceEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.Card;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.players.Player;

import java.util.Optional;
import java.util.UUID;

/**
 * @author nantuko
 */
public final class CivilizedScholar extends TransformingDoubleFacedCard {

    private static final Condition condition = new InvertCondition(
            AttackedThisTurnSourceCondition.instance, "{this} didn't attack this turn"
    );

    public CivilizedScholar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN, SubType.ADVISOR}, "{2}{U}",
                "Homicidal Brute",
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN, SubType.MUTANT}, "R");

        // Civilized Scholar
        this.getLeftHalfCard().setPT(0, 1);

        // {T}: Draw a card, then discard a card. If a creature card is discarded this way, untap Civilized Scholar, then transform it.
        Ability ability = new SimpleActivatedAbility(new DrawCardSourceControllerEffect(1), new TapSourceCost());
        ability.addEffect(new CivilizedScholarEffect());
        this.getLeftHalfCard().addAbility(ability);

        // Homicidal Brute
        this.getRightHalfCard().setPT(5, 1);

        // At the beginning of your end step, if Homicidal Brute didn't attack this turn, tap Homicidal Brute, then transform it.
        TriggeredAbility bruteAbility = new BeginningOfEndStepTriggeredAbility(new TapSourceEffect());
        bruteAbility.addEffect(new TransformSourceEffect().setText(", then transform it"));
        this.getRightHalfCard().addAbility(bruteAbility.withInterveningIf(condition));
    }

    private CivilizedScholar(final CivilizedScholar card) {
        super(card);
    }

    @Override
    public CivilizedScholar copy() {
        return new CivilizedScholar(this);
    }
}

class CivilizedScholarEffect extends OneShotEffect {

    CivilizedScholarEffect() {
        super(Outcome.DrawCard);
        staticText = ", then discard a card. If a creature card is discarded this way, untap {this}, then transform it";
    }

    private CivilizedScholarEffect(final CivilizedScholarEffect effect) {
        super(effect);
    }

    @Override
    public CivilizedScholarEffect copy() {
        return new CivilizedScholarEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Card card = player.discardOne(false, false, source, game);
        if (card == null || !card.isCreature(game)) {
            return true;
        }
        Optional.ofNullable(source.getSourcePermanentIfItStillExists(game))
                .ifPresent(permanent -> {
                    permanent.untap(game);
                    permanent.transform(source, game);
                });
        return true;
    }
}
