package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToBattlefieldEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MalakirRebirth extends CardImpl {

    public MalakirRebirth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{B}");

        this.modalDFC = true;
        this.secondSideCardClazz = mage.cards.m.MalakirMire.class;

        // Choose target creature. You lose 2 life. Until end of turn, that creature gains "When this creature dies, return it to the battlefield tapped under its owner's control."
        this.getSpellAbility().addEffect(new MalakirRebirthEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private MalakirRebirth(final MalakirRebirth card) {
        super(card);
    }

    @Override
    public MalakirRebirth copy() {
        return new MalakirRebirth(this);
    }
}

class MalakirRebirthEffect extends OneShotEffect {

    MalakirRebirthEffect() {
        super(Outcome.Benefit);
        staticText = "Choose target creature. You lose 2 life. Until end of turn, " +
                "that creature gains \"When this creature dies, return it to the battlefield tapped under its owner's control.\"";
    }

    private MalakirRebirthEffect(final MalakirRebirthEffect effect) {
        super(effect);
    }

    @Override
    public MalakirRebirthEffect copy() {
        return new MalakirRebirthEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        game.addEffect(new GainAbilityTargetEffect(new DiesSourceTriggeredAbility(
                new ReturnSourceFromGraveyardToBattlefieldEffect(true, true), false
        ), Duration.EndOfTurn), source);
        Player player = game.getPlayer(source.getControllerId());
        return player != null && player.loseLife(2, game, false) > 0;
    }
}
