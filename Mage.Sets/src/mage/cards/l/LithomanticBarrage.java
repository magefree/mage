package mage.cards.l;

import mage.abilities.Ability;
import mage.abilities.common.CantBeCounteredSourceAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreatureOrPlaneswalker;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LithomanticBarrage extends CardImpl {

    public LithomanticBarrage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{R}");

        // This spell can't be countered.
        this.addAbility(new CantBeCounteredSourceAbility());

        // Lithomantic Barrage deals 1 damage to target creature or planeswalker. It deals 5 damage instead if that target is white and/or blue.
        this.getSpellAbility().addEffect(new LithomanticBarrageEffect());
        this.getSpellAbility().addTarget(new TargetCreatureOrPlaneswalker());
    }

    private LithomanticBarrage(final LithomanticBarrage card) {
        super(card);
    }

    @Override
    public LithomanticBarrage copy() {
        return new LithomanticBarrage(this);
    }
}

class LithomanticBarrageEffect extends OneShotEffect {

    LithomanticBarrageEffect() {
        super(Outcome.Benefit);
        staticText = "{this} deals 1 damage to target creature or planeswalker. " +
                "It deals 5 damage instead if that target is white and/or blue";
    }

    private LithomanticBarrageEffect(final LithomanticBarrageEffect effect) {
        super(effect);
    }

    @Override
    public LithomanticBarrageEffect copy() {
        return new LithomanticBarrageEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (permanent == null) {
            return false;
        }
        if (permanent.getColor(game).isWhite() || permanent.getColor(game).isBlue()) {
            return permanent.damage(5, source, game) > 0;
        }
        return permanent.damage(1, source, game) > 0;
    }
}
