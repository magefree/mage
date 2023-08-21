package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.asthought.PlayFromNotOwnHandZoneTargetEffect;
import mage.abilities.effects.common.replacement.ThatSpellGraveyardExileReplacementEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.common.FilterInstantOrSorceryCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 *
 * @author TheElk801
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

    public MissionBriefingEffect() {
        super(Outcome.Benefit);
        this.staticText = "Surveil 2, then choose an instant or sorcery card "
                + "in your graveyard. You may cast it this turn. "
                + ThatSpellGraveyardExileReplacementEffect.RULE_YOUR;
    }

    public MissionBriefingEffect(final MissionBriefingEffect effect) {
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
        Target target = new TargetCardInYourGraveyard(
                new FilterInstantOrSorceryCard("instant or sorcery card from your graveyard"));
        if (!player.choose(outcome, target, source, game)) {
            return true;
        }
        Card card = game.getCard(target.getFirstTarget());
        if (card != null) {
            ContinuousEffect effect = new PlayFromNotOwnHandZoneTargetEffect();
            effect.setTargetPointer(new FixedTarget(card, game));
            game.addEffect(effect, source);
            ContinuousEffect effect2 = new ThatSpellGraveyardExileReplacementEffect();
            effect2.setTargetPointer(new FixedTarget(card, game));
            game.addEffect(effect2, source);
            return true;
        }
        return false;
    }
}
