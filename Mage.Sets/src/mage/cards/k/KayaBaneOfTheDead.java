package mage.cards.k;

import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KayaBaneOfTheDead extends CardImpl {

    public KayaBaneOfTheDead(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{3}{W/B}{W/B}{W/B}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.KAYA);
        this.setStartingLoyalty(7);

        // Your opponents and permanents your opponents control with hexproof can be the target of spells and abilities you control as though they didn't have hexproof.
        this.addAbility(new SimpleStaticAbility(new KayaBaneOfTheDeadEffect()));

        // -3: Exile target creature.
        Ability ability = new LoyaltyAbility(new ExileTargetEffect(), -3);
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private KayaBaneOfTheDead(final KayaBaneOfTheDead card) {
        super(card);
    }

    @Override
    public KayaBaneOfTheDead copy() {
        return new KayaBaneOfTheDead(this);
    }
}

class KayaBaneOfTheDeadEffect extends AsThoughEffectImpl {

    KayaBaneOfTheDeadEffect() {
        super(AsThoughEffectType.HEXPROOF, Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "your opponents and permanents your opponents control with hexproof " +
                "can be the targets of spells and abilities you control as though they didn't have hexproof";
    }

    private KayaBaneOfTheDeadEffect(final KayaBaneOfTheDeadEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public KayaBaneOfTheDeadEffect copy() {
        return new KayaBaneOfTheDeadEffect(this);
    }

    @Override
    public boolean applies(UUID sourceId, Ability source, UUID affectedControllerId, Game game) {
        if (affectedControllerId.equals(source.getControllerId())) {
            if (game.getOpponents(source.getControllerId()).contains(sourceId)) {
                return true;
            }
            Permanent creature = game.getPermanent(sourceId);
            if (creature != null
                    && game.getOpponents(source.getControllerId()).contains(creature.getControllerId())) {
                return true;
            }
        }
        return false;
    }
}
