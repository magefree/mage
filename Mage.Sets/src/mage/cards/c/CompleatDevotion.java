package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.keyword.ToxicAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

public class CompleatDevotion extends CardImpl {
    public CompleatDevotion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{W}");

        //Target creature you control gets +2/+2 until end of turn. If that creature has toxic, draw a card.
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
        this.getSpellAbility().addEffect(new CompleatDevotionEffect());
        this.getSpellAbility().addEffect(new BoostTargetEffect(2, 2, Duration.EndOfTurn)
                .setText("Target creature you control gets +2/+2 until end of turn. If that creature has toxic, draw a card."));
    }

    private CompleatDevotion(final CompleatDevotion card) {
        super(card);
    }

    @Override
    public CompleatDevotion copy() {
        return new CompleatDevotion(this);
    }
}

class CompleatDevotionEffect extends OneShotEffect {

    public CompleatDevotionEffect() {
        super(Outcome.Benefit);
        staticText = "";
    }

    public CompleatDevotionEffect(final CompleatDevotionEffect effect) {
        super(effect);
    }

    @Override
    public CompleatDevotionEffect copy() {
        return new CompleatDevotionEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (permanent != null) {
            if (permanent.getAbilities().stream().anyMatch(ability -> (ability instanceof ToxicAbility))) {
                Player controller = game.getPlayer(permanent.getControllerId());
                if (controller != null) {
                    controller.drawCards(1, source, game);
                }
            }
            return true;
        }
        return false;
    }
}
