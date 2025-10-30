package mage.cards.u;

import mage.abilities.Ability;
import mage.abilities.common.UnlockThisDoorTriggeredAbility;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.CardSetInfo;
import mage.cards.RoomCard;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SpellAbilityType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.game.permanent.token.Demon66Token;
import mage.players.Player;

import java.util.UUID;

/**
 * @author PurpleCrowbar
 */
public final class UnholyAnnexRitualChamber extends RoomCard {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("Demon");

    static {
        filter.add(SubType.DEMON.getPredicate());
    }

    public UnholyAnnexRitualChamber(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{B}", "{3}{B}{B}", SpellAbilityType.SPLIT);
        this.subtype.add(SubType.ROOM);

        // Unholy Annex: At the beginning of your end step, draw a card. If you control a Demon, each opponent loses 2 life and you gain 2 life. Otherwise, you lose 2 life.
        Ability left = new BeginningOfEndStepTriggeredAbility(new DrawCardSourceControllerEffect(1));
        left.addEffect(new ConditionalOneShotEffect(
                new UnholyAnnexDrainEffect(), new LoseLifeSourceControllerEffect(2),
                new PermanentsOnTheBattlefieldCondition(filter), "If you control a Demon, each opponent loses 2 life and you gain 2 life. Otherwise, you lose 2 life"
        ));
        left.addHint(new ConditionHint(new PermanentsOnTheBattlefieldCondition(filter), "You control a Demon"));

        // Ritual Chamber: When you unlock this door, create a 6/6 black Demon creature token with flying.
        Ability right = new UnlockThisDoorTriggeredAbility(new CreateTokenEffect(new Demon66Token()), false, false);

        this.addRoomAbilities(left, right);
    }

    private UnholyAnnexRitualChamber(final UnholyAnnexRitualChamber card) {
        super(card);
    }

    @Override
    public UnholyAnnexRitualChamber copy() {
        return new UnholyAnnexRitualChamber(this);
    }
}

class UnholyAnnexDrainEffect extends OneShotEffect {

    UnholyAnnexDrainEffect() {
        super(Outcome.GainLife);
        this.staticText = "each opponent loses 2 life and you gain 2 life";
    }

    private UnholyAnnexDrainEffect(final UnholyAnnexDrainEffect effect) {
        super(effect);
    }

    @Override
    public UnholyAnnexDrainEffect copy() {
        return new UnholyAnnexDrainEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (UUID opponentId : game.getOpponents(source.getControllerId())) {
            Player player = game.getPlayer(opponentId);
            if (player != null) {
                player.loseLife(2, game, source, false);
            }
        }
        game.getPlayer(source.getControllerId()).gainLife(2, game, source);
        return true;
    }
}
