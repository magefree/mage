package mage.cards.p;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.condition.common.RaidCondition;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.hint.common.RaidHint;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetPermanent;
import mage.watchers.common.PlayerAttackedWatcher;

import java.util.UUID;

/**
 *
 * @author ciaccona007
 */
public final class PerforatingArtist extends CardImpl {

    public PerforatingArtist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}{R}");
        
        this.subtype.add(SubType.DEVIL);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // Raid -- At the beginning of your end step, if you attacked this turn, each opponent loses 3 life unless that player sacrifices a nonland permanent of their choice or discards a card.
        Ability ability = new BeginningOfEndStepTriggeredAbility(
                new PerforatingArtistEffect()
        ).withInterveningIf(RaidCondition.instance);
        this.addAbility(ability.setAbilityWord(AbilityWord.RAID).addHint(RaidHint.instance), new PlayerAttackedWatcher());
    }

    private PerforatingArtist(final PerforatingArtist card) {
        super(card);
    }

    @Override
    public PerforatingArtist copy() {
        return new PerforatingArtist(this);
    }
}

class PerforatingArtistEffect extends OneShotEffect {

    PerforatingArtistEffect() {
        super(Outcome.Damage);
        staticText = "each opponent loses 3 life unless that player sacrifices a nonland permanent or discards a card";
    }

    private PerforatingArtistEffect(final PerforatingArtistEffect effect) {
        super(effect);
    }

    @Override
    public PerforatingArtistEffect copy() {
        return new PerforatingArtistEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            for (UUID opponentId : game.getOpponents(controller.getId())) {
                Player opponent = game.getPlayer(opponentId);
                if (opponent != null) {
                    int permanents = game.getBattlefield().countAll(StaticFilters.FILTER_PERMANENT_NON_LAND, opponent.getId(), game);
                    if (permanents > 0 && opponent.chooseUse(outcome, "Sacrifices a nonland permanent?",
                            "Otherwise you have to discard a card or lose 3 life.", "Sacrifice", "Discard or life loss", source, game)) {
                        Target target = new TargetPermanent(StaticFilters.FILTER_CONTROLLED_PERMANENT_NON_LAND);
                        if (opponent.choose(outcome, target, source, game)) {
                            Permanent permanent = game.getPermanent(target.getFirstTarget());
                            if (permanent != null) {
                                permanent.sacrifice(source, game);
                                return true;
                            }
                        }
                    }
                    if (!opponent.getHand().isEmpty() && opponent.chooseUse(outcome, "Discard a card?",
                            "Otherwise you lose 3 life.", "Discard", "Lose 3 life", source, game)) {
                        opponent.discardOne(false, false, source, game);
                        return true;
                    }
                    opponent.loseLife(3, game, source, false);

                }
            }
            return true;
        }
        return false;
    }
}