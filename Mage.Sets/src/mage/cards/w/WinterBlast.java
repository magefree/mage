
package mage.cards.w;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetadjustment.TargetAdjuster;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WinterBlast extends CardImpl {

    public WinterBlast(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{G}");

        // Tap X target creatures. Winter Blast deals 2 damage to each of those creatures with flying.
        this.getSpellAbility().addEffect(new WinterBlastEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().setTargetAdjuster(WinterBlastAdjuster.instance);
    }

    private WinterBlast(final WinterBlast card) {
        super(card);
    }

    @Override
    public WinterBlast copy() {
        return new WinterBlast(this);
    }
}

enum WinterBlastAdjuster implements TargetAdjuster {
    instance;

    @Override
    public void adjustTargets(Ability ability, Game game) {
        ability.getTargets().clear();
        ability.addTarget(new TargetCreaturePermanent(ability.getManaCostsToPay().getX()));
    }
}

class WinterBlastEffect extends OneShotEffect {

    WinterBlastEffect() {
        super(Outcome.Benefit);
        this.staticText = "Tap X target creatures. {this} deals 2 damage to each of those creatures with flying.";
    }

    private WinterBlastEffect(final WinterBlastEffect effect) {
        super(effect);
    }

    @Override
    public WinterBlastEffect copy() {
        return new WinterBlastEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int affectedTargets = 0;
        for (UUID permanentId : targetPointer.getTargets(game, source)) {
            Permanent permanent = game.getPermanent(permanentId);
            if (permanent != null) {
                permanent.tap(source, game);
                if (permanent.getAbilities().contains(FlyingAbility.getInstance())) {
                    permanent.damage(2, source.getSourceId(), source, game, false, true);
                }
                affectedTargets++;
            }
        }
        return affectedTargets > 0;
    }
}
