package mage.cards.r;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ResoluteStrike extends CardImpl {

    public ResoluteStrike(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{W}");

        // Target creature gets +2/+2 until end of turn. If it's a Warrior, you may attach an Equipment you control to it.
        this.getSpellAbility().addEffect(new BoostTargetEffect(2, 2, Duration.EndOfTurn));
        this.getSpellAbility().addEffect(new ResoluteStrikeEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private ResoluteStrike(final ResoluteStrike card) {
        super(card);
    }

    @Override
    public ResoluteStrike copy() {
        return new ResoluteStrike(this);
    }
}

class ResoluteStrikeEffect extends OneShotEffect {

    private static final FilterPermanent filter
            = new FilterControlledPermanent(SubType.EQUIPMENT, "Equipment you control");

    ResoluteStrikeEffect() {
        super(Outcome.Benefit);
        staticText = "If it's a Warrior, you may attach an Equipment you control to it";
    }

    private ResoluteStrikeEffect(final ResoluteStrikeEffect effect) {
        super(effect);
    }

    @Override
    public ResoluteStrikeEffect copy() {
        return new ResoluteStrikeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (player == null
                || permanent == null
                || !permanent.hasSubtype(SubType.WARRIOR, game)
                || game.getBattlefield().count(filter, source.getControllerId(), source, game) == 0
                || !player.chooseUse(outcome, "Attach an equipment you control?", source, game)) {
            return false;
        }
        TargetPermanent targetPermanent = new TargetPermanent(0, 1, filter, true);
        player.choose(outcome, targetPermanent, source, game);
        return permanent.addAttachment(targetPermanent.getFirstTarget(), source, game);
    }
}
