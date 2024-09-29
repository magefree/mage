package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
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
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, new CardType[]{CardType.SORCERY}, "{5}{B}{B}", "Harvest Fear", "{3}{B}");

        this.subtype.add(SubType.SPECTER);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // Whenever Reaper of Night attacks, if defending player has two or fewer cards in hand, it gains flying until end of turn.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new AttacksTriggeredAbility(new GainAbilitySourceEffect(
                        FlyingAbility.getInstance(), Duration.EndOfTurn
                ), false), ReaperOfNightCondition.instance, "Whenever {this} attacks, " +
                "if defending player has two or fewer cards in hand, it gains flying until end of turn."
        ));

        // Harvest Fear
        // Target opponent discards two cards.
        this.getSpellCard().getSpellAbility().addEffect(new DiscardTargetEffect(2));
        this.getSpellCard().getSpellAbility().addTarget(new TargetOpponent());

        this.finalizeAdventure();
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
}