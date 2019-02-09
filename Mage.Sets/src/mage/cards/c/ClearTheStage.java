package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.condition.common.FerociousCondition;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.hint.common.FerociousHint;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ClearTheStage extends CardImpl {

    private static final FilterCard filter = new FilterCreatureCard("creature card from your graveyard");

    public ClearTheStage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{4}{B}");

        // Target creature gets -3/-3 until end of turn. If you control a creature with power 4 or greater, you may return up to one target creature card from your graveyard to your hand.
        this.getSpellAbility().addEffect(new ClearTheStageEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(0, 1, filter));
        this.getSpellAbility().addHint(FerociousHint.instance);
    }

    private ClearTheStage(final ClearTheStage card) {
        super(card);
    }

    @Override
    public ClearTheStage copy() {
        return new ClearTheStage(this);
    }
}

class ClearTheStageEffect extends OneShotEffect {

    ClearTheStageEffect() {
        super(Outcome.Benefit);
        staticText = "Target creature gets -3/-3 until end of turn. If you control a creature with power 4 or greater, " +
                "you may return up to one target creature card from your graveyard to your hand.";
    }

    private ClearTheStageEffect(final ClearTheStageEffect effect) {
        super(effect);
    }

    @Override
    public ClearTheStageEffect copy() {
        return new ClearTheStageEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        game.addEffect(new BoostTargetEffect(-3, -3), source);
        if (!FerociousCondition.instance.apply(game, source)) {
            return true;
        }
        Player player = game.getPlayer(source.getControllerId());
        if (player == null || !player.chooseUse(Outcome.Benefit, "Return a creature card from your graveyard to your hand?", source, game)) {
            return false;
        }
        Card card = game.getCard(source.getTargets().get(1).getFirstTarget());
        if (card == null) {
            return false;
        }
        return player.moveCards(card, Zone.HAND, source, game);
    }
}