package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.combat.CantBlockTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.target.Target;
import mage.target.common.TargetCreaturePermanent;

import java.util.Collection;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BlowYourHouseDown extends CardImpl {

    public BlowYourHouseDown(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{R}");

        // Up to three target creatures can't block this turn. Destroy any of them that are Walls.
        this.getSpellAbility().addEffect(new CantBlockTargetEffect(Duration.EndOfTurn));
        this.getSpellAbility().addEffect(new BlowYourHouseDownEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(0, 3));
    }

    private BlowYourHouseDown(final BlowYourHouseDown card) {
        super(card);
    }

    @Override
    public BlowYourHouseDown copy() {
        return new BlowYourHouseDown(this);
    }
}

class BlowYourHouseDownEffect extends OneShotEffect {

    BlowYourHouseDownEffect() {
        super(Outcome.Benefit);
        staticText = "Destroy any of them that are Walls";
    }

    private BlowYourHouseDownEffect(final BlowYourHouseDownEffect effect) {
        super(effect);
    }

    @Override
    public BlowYourHouseDownEffect copy() {
        return new BlowYourHouseDownEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        source.getTargets()
                .stream()
                .map(Target::getTargets)
                .flatMap(Collection::stream)
                .map(game::getPermanent)
                .filter(permanent -> permanent != null && permanent.hasSubtype(SubType.WALL, game))
                .forEach(permanent -> permanent.destroy(source, game, false));
        return true;
    }
}