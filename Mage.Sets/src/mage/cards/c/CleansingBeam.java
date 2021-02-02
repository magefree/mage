package mage.cards.c;

import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author fenhl
 */
public final class CleansingBeam extends CardImpl {

    public CleansingBeam(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{4}{R}");

        // Radiance — Cleansing Beam deals 2 damage to target creature and each other creature that shares a color with it.
        this.getSpellAbility().addEffect(new CleansingBeamEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().setAbilityWord(AbilityWord.RADIANCE);
    }

    private CleansingBeam(final CleansingBeam card) {
        super(card);
    }

    @Override
    public CleansingBeam copy() {
        return new CleansingBeam(this);
    }
}

class CleansingBeamEffect extends OneShotEffect {

    static final FilterPermanent filter = new FilterPermanent("creature");

    static {
        filter.add(CardType.CREATURE.getPredicate());
    }

    CleansingBeamEffect() {
        super(Outcome.Damage);
        staticText = "{this} deals 2 damage to target creature and each other creature that shares a color with it";
    }

    CleansingBeamEffect(final CleansingBeamEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent target = game.getPermanent(targetPointer.getFirst(game, source));
        if (target != null) {
            ObjectColor color = target.getColor(game);
            target.damage(2, source.getSourceId(), source, game);
            for (Permanent p : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), game)) {
                if (!target.getId().equals(p.getId()) && p.getColor(game).shares(color)) {
                    p.damage(2, source.getSourceId(), source, game, false, true);
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public CleansingBeamEffect copy() {
        return new CleansingBeamEffect(this);
    }
}
