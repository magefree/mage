package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class CrushUnderfoot extends CardImpl {

    public CrushUnderfoot(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.KINDRED, CardType.INSTANT}, "{1}{R}");
        this.subtype.add(SubType.GIANT);

        // Choose a Giant creature you control. It deals damage equal to its power to target creature.
        this.getSpellAbility().addEffect(new CrushUnderfootEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent().withChooseHint("to deal damage to"));
    }

    private CrushUnderfoot(final CrushUnderfoot card) {
        super(card);
    }

    @Override
    public CrushUnderfoot copy() {
        return new CrushUnderfoot(this);
    }
}

class CrushUnderfootEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent(SubType.GIANT, "Giant creature you control");

    public CrushUnderfootEffect() {
        super(Outcome.Damage);
        this.staticText = "Choose a Giant creature you control. It deals damage equal to its power to target creature";
    }

    private CrushUnderfootEffect(final CrushUnderfootEffect effect) {
        super(effect);
    }

    @Override
    public CrushUnderfootEffect copy() {
        return new CrushUnderfootEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        // Choose a Giant creature you control (not targeted, happens during effect resolving )
        Target target = new TargetPermanent(filter);
        target.withNotTarget(true);
        if (!target.canChoose(controller.getId(), source, game)
                || !controller.chooseTarget(outcome, target, source, game)) {
            return false;
        }
        Permanent giant = game.getPermanent(target.getFirstTarget());
        if (giant == null) {
            return false;
        }
        game.informPlayers("Crush Underfoot: Chosen Giant is " + giant.getName());
        Permanent targetCreature = game.getPermanent(this.getTargetPointer().getFirst(game, source));
        return targetCreature != null && targetCreature.damage(
                giant.getPower().getValue(), source.getSourceId(), source, game, false, true
        ) > 0;
    }
}
