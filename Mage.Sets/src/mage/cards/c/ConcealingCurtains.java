package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.TransformIntoSourceTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.Card;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ConcealingCurtains extends TransformingDoubleFacedCard {

    public ConcealingCurtains(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.WALL}, "{B}",
                "Revealing Eye",
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.EYE, SubType.HORROR}, "B");

        // Concealing Curtains
        this.getLeftHalfCard().setPT(0, 4);

        // Defender
        this.getLeftHalfCard().addAbility(DefenderAbility.getInstance());

        // {2}{B}: Transform Concealing Curtains. Activate only as a sorcery.
        this.getLeftHalfCard().addAbility(new ActivateAsSorceryActivatedAbility(
                new TransformSourceEffect(), new ManaCostsImpl<>("{2}{B}")
        ));

        // Revealing Eye
        this.getRightHalfCard().setPT(3, 4);

        // Menace
        this.getRightHalfCard().addAbility(new MenaceAbility(false));

        // When this creature transforms into Revealing Eye, target opponent reveals their hand. You may choose a nonland card from it. If you do, that player discards that card, then draws a card.
        Ability ability = new TransformIntoSourceTriggeredAbility(new RevealingEyeEffect());
        ability.addTarget(new TargetOpponent());
        this.getRightHalfCard().addAbility(ability);
    }

    private ConcealingCurtains(final ConcealingCurtains card) {
        super(card);
    }

    @Override
    public ConcealingCurtains copy() {
        return new ConcealingCurtains(this);
    }
}

class RevealingEyeEffect extends OneShotEffect {

    RevealingEyeEffect() {
        super(Outcome.Discard);
        staticText = "target opponent reveals their hand. You may choose a nonland card from it. " +
                "If you do, that player discards that card, then draws a card";
    }

    private RevealingEyeEffect(final RevealingEyeEffect effect) {
        super(effect);
    }

    @Override
    public RevealingEyeEffect copy() {
        return new RevealingEyeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player opponent = game.getPlayer(source.getFirstTarget());
        if (controller == null || opponent == null) {
            return false;
        }
        opponent.revealCards(source, opponent.getHand(), game);
        if (opponent.getHand().count(StaticFilters.FILTER_CARD_NON_LAND, game) < 1) {
            return true;
        }
        TargetCard target = new TargetCard(0, 1, Zone.HAND, StaticFilters.FILTER_CARD_NON_LAND);
        controller.choose(outcome, opponent.getHand(), target, source, game);
        Card card = game.getCard(target.getFirstTarget());
        if (card == null) {
            return true;
        }
        opponent.discard(card, false, source, game);
        opponent.drawCards(1, source, game);
        return true;
    }
}
