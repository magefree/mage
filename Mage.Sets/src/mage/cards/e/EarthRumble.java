package mage.cards.e;

import mage.abilities.Ability;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.FightTargetsEffect;
import mage.abilities.effects.keyword.EarthbendTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetControlledLandPermanent;
import mage.target.common.TargetOpponentsCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EarthRumble extends CardImpl {

    public EarthRumble(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{G}");

        // Earthbend 2. When you do, up to one target creature you control fights target creature an opponent controls.
        this.getSpellAbility().addEffect(new EarthbendTargetEffect(2));
        this.getSpellAbility().addTarget(new TargetControlledLandPermanent());
        this.getSpellAbility().addEffect(new EarthRumbleEffect());
    }

    private EarthRumble(final EarthRumble card) {
        super(card);
    }

    @Override
    public EarthRumble copy() {
        return new EarthRumble(this);
    }
}

class EarthRumbleEffect extends OneShotEffect {

    EarthRumbleEffect() {
        super(Outcome.Benefit);
        staticText = "When you do, up to one target creature you control fights target creature an opponent controls";
    }

    private EarthRumbleEffect(final EarthRumbleEffect effect) {
        super(effect);
    }

    @Override
    public EarthRumbleEffect copy() {
        return new EarthRumbleEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        ReflexiveTriggeredAbility ability = new ReflexiveTriggeredAbility(new FightTargetsEffect(), false);
        ability.addTarget(new TargetControlledCreaturePermanent(0, 1));
        ability.addTarget(new TargetOpponentsCreaturePermanent());
        game.fireReflexiveTriggeredAbility(ability, source);
        return true;
    }
}
