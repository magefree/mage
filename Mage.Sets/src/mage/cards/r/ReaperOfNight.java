package mage.cards.r;

import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.AdventureCard;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ReaperOfNight extends AdventureCard {

    public ReaperOfNight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.SPECTER}, "{5}{B}{B}",
                "Harvest Fear",
                new CardType[]{CardType.SORCERY}, "{3}{B}");

        // Reaper of Night
        this.getLeftHalfCard().setPT(4, 5);

        // Whenever Reaper of Night attacks, if defending player has two or fewer cards in hand, it gains flying until end of turn.
        this.getLeftHalfCard().addAbility(new AttacksTriggeredAbility(new GainAbilitySourceEffect(
                FlyingAbility.getInstance(), Duration.EndOfTurn
        ).setText("it gains flying until end of turn")).withInterveningIf(ReaperOfNightCondition.instance));

        // Harvest Fear
        // Target opponent discards two cards.
        this.getRightHalfCard().getSpellAbility().addEffect(new DiscardTargetEffect(2));
        this.getRightHalfCard().getSpellAbility().addTarget(new TargetOpponent());

        finalizeCard();
    }

    private ReaperOfNight(final ReaperOfNight card) {
        super(card);
    }

    @Override
    public ReaperOfNight copy() {
        return new ReaperOfNight(this);
    }
}

enum ReaperOfNightCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(game.getCombat().getDefendingPlayerId(source.getSourceId(), game));
        return player != null && player.getHand().size() <= 2;
    }

    @Override
    public String toString() {
        return "defending player has two or fewer cards in hand";
    }
}
