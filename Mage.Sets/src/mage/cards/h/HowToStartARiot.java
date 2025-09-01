package mage.cards.h;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.SecondTargetPointer;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HowToStartARiot extends CardImpl {

    public HowToStartARiot(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{R}");

        this.subtype.add(SubType.LESSON);

        // Target creature gains menace until end of turn.
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(new MenaceAbility(false)));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());

        // Creatures target player controls get +2/+0 until end of turn.
        this.getSpellAbility().addEffect(new HowToStartARiotEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());
    }

    private HowToStartARiot(final HowToStartARiot card) {
        super(card);
    }

    @Override
    public HowToStartARiot copy() {
        return new HowToStartARiot(this);
    }
}

class HowToStartARiotEffect extends OneShotEffect {

    HowToStartARiotEffect() {
        super(Outcome.Benefit);
        this.setTargetPointer(new SecondTargetPointer());
        staticText = "creatures target player controls get +2/+0 until end of turn";
        this.concatBy("<br>");
    }

    private HowToStartARiotEffect(final HowToStartARiotEffect effect) {
        super(effect);
    }

    @Override
    public HowToStartARiotEffect copy() {
        return new HowToStartARiotEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (player == null) {
            return false;
        }
        FilterCreaturePermanent filter = new FilterCreaturePermanent();
        filter.add(new ControllerIdPredicate(player.getId()));
        game.addEffect(new BoostAllEffect(
                2, 0, Duration.EndOfTurn, filter, false
        ), source);
        return true;
    }
}
