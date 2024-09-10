package mage.cards.e;

import mage.abilities.Ability;
import mage.abilities.common.AttachedToCreatureSourceTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

/**
 * @author Addictiveme
 */
public final class EnormousEnergyBlade extends CardImpl {

    public EnormousEnergyBlade(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}{B}");

        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +4/+0
        this.addAbility(new SimpleStaticAbility(new BoostEquippedEffect(4, 0)));

        // Whenever Enormous Energy Blade becomes attached to a creature, tap that creature.
        this.addAbility(new AttachedToCreatureSourceTriggeredAbility(new EnormousEnergyBladeEffect(), false));

        // Equip {2}
        this.addAbility(new EquipAbility(2));
    }

    private EnormousEnergyBlade(final EnormousEnergyBlade card) {
        super(card);
    }

    @Override
    public EnormousEnergyBlade copy() {
        return new EnormousEnergyBlade(this);
    }
}

class EnormousEnergyBladeEffect extends OneShotEffect {

    EnormousEnergyBladeEffect() {
        super(Outcome.Benefit);
        staticText = "tap that creature";
    }

    private EnormousEnergyBladeEffect(final EnormousEnergyBladeEffect effect) {
        super(effect);
    }

    @Override
    public EnormousEnergyBladeEffect copy() {
        return new EnormousEnergyBladeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return Optional
                .ofNullable(getValue("attachedPermanent"))
                .map(Permanent.class::cast)
                .filter(Objects::nonNull)
                .map(permanent -> permanent.tap(source, game))
                .orElse(null);
    }
}
