package mage.cards.e;

import mage.abilities.Ability;
import mage.abilities.common.DealsDamageToAPlayerAttachedTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.game.Game;

import java.util.Optional;
import java.util.UUID;

/**
 * @author BetaSteward
 */
public final class ElbrusTheBindingBlade extends CardImpl {

    public ElbrusTheBindingBlade(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{7}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.EQUIPMENT);

        this.secondSideCardClazz = mage.cards.w.WithengarUnbound.class;
        this.addAbility(new TransformAbility());

        // Equipped creature gets +1/+0.
        this.addAbility(new SimpleStaticAbility(new BoostEquippedEffect(1, 0)));

        // When equipped creature deals combat damage to a player, unattach Elbrus, the Binding Blade, then transform it.
        Ability ability = new DealsDamageToAPlayerAttachedTriggeredAbility(
                new ElbrusTheBindingBladeEffect(), "equipped", false
        );
        ability.addEffect(new TransformSourceEffect(true).concatBy(", then"));
        this.addAbility(ability);

        // Equip {1}
        this.addAbility(new EquipAbility(1, false));
    }

    private ElbrusTheBindingBlade(final ElbrusTheBindingBlade card) {
        super(card);
    }

    @Override
    public ElbrusTheBindingBlade copy() {
        return new ElbrusTheBindingBlade(this);
    }
}

class ElbrusTheBindingBladeEffect extends OneShotEffect {
    ElbrusTheBindingBladeEffect() {
        super(Outcome.BecomeCreature);
        staticText = "unattach {this}, then transform it";
    }

    private ElbrusTheBindingBladeEffect(final ElbrusTheBindingBladeEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Optional.ofNullable(source.getSourcePermanentIfItStillExists(game)).ifPresent(permanent -> permanent.unattach(game));
        return true;
    }

    @Override
    public ElbrusTheBindingBladeEffect copy() {
        return new ElbrusTheBindingBladeEffect(this);
    }
}
