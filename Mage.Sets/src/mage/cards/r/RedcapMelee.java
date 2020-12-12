package mage.cards.r;

import mage.abilities.Ability;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.SacrificeControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreatureOrPlaneswalker;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RedcapMelee extends CardImpl {

    public RedcapMelee(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{R}");

        // Redcap Melee deals 4 damage to target creature or planeswalker. If a nonred permanent is dealt damage this way, you sacrifice a land.
        this.getSpellAbility().addEffect(new RedcapMeleeEffect());
        this.getSpellAbility().addTarget(new TargetCreatureOrPlaneswalker());
    }

    private RedcapMelee(final RedcapMelee card) {
        super(card);
    }

    @Override
    public RedcapMelee copy() {
        return new RedcapMelee(this);
    }
}

class RedcapMeleeEffect extends OneShotEffect {

    private static final Effect effect = new SacrificeControllerEffect(StaticFilters.FILTER_LAND, 1, "");

    RedcapMeleeEffect() {
        super(Outcome.Damage);
        staticText = "{this} deals 4 damage to target creature or planeswalker. " +
                "If a nonred permanent is dealt damage this way, you sacrifice a land.";
    }

    private RedcapMeleeEffect(final RedcapMeleeEffect effect) {
        super(effect);
    }

    @Override
    public RedcapMeleeEffect copy() {
        return new RedcapMeleeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent == null) {
            return false;
        }
        boolean isRed = permanent.getColor(game).isRed();
        if (permanent.damage(4, source.getSourceId(), source, game) > 0 && !isRed) {
            return effect.apply(game, source);
        }
        return true;
    }
}