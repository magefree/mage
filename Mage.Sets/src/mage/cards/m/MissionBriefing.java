package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.MayCastTargetThenExileEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.replacement.ThatSpellGraveyardExileReplacementEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author TheElk801, xenohedron
 */
public final class MissionBriefing extends CardImpl {

    public MissionBriefing(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{U}{U}");

        // Surveil 2, then choose an instant or sorcery card in your graveyard. You may cast that card this turn. If that card would be put into your graveyard this turn, exile it instead.
        this.getSpellAbility().addEffect(new MissionBriefingEffect());
    }

    private MissionBriefing(final MissionBriefing card) {
        super(card);
    }

    @Override
    public MissionBriefing copy() {
        return new MissionBriefing(this);
    }
}

class MissionBriefingEffect extends OneShotEffect {

    MissionBriefingEffect() {
        super(Outcome.Benefit);
        this.staticText = "Surveil 2, then choose an instant or sorcery card "
                + "in your graveyard. You may cast it this turn. "
                + ThatSpellGraveyardExileReplacementEffect.RULE_YOUR;
    }

    private MissionBriefingEffect(final MissionBriefingEffect effect) {
        super(effect);
    }

    @Override
    public MissionBriefingEffect copy() {
        return new MissionBriefingEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        player.surveil(2, source, game);
        Target target = new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_INSTANT_OR_SORCERY_FROM_YOUR_GRAVEYARD);
        player.choose(outcome, target, source, game);
        Effect effect = new MayCastTargetThenExileEffect(Duration.EndOfTurn);
        effect.setTargetPointer(new FixedTarget(target.getFirstTarget(), game));
        effect.apply(game, source);
        return true;
    }
}
