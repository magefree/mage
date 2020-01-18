package mage.cards.u;

import mage.abilities.effects.common.DamageAllEffect;
import mage.abilities.effects.common.replacement.DealtDamageToCreatureBySourceDies;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.common.FilterCreatureOrPlaneswalkerPermanent;
import mage.watchers.common.DamagedByWatcher;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class UnderworldFires extends CardImpl {

    public UnderworldFires(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{R}");

        // Underworld Fires deals 1 damage to each creature and each planeswalker.
        this.getSpellAbility().addEffect(new DamageAllEffect(1,
                new FilterCreatureOrPlaneswalkerPermanent("creature and each planeswalker")));

        // If a permanent dealt damage this way would die this turn, exile it instead.
        this.getSpellAbility().addEffect(new DealtDamageToCreatureBySourceDies(this, Duration.EndOfTurn));
        this.getSpellAbility().addWatcher(new DamagedByWatcher(false));
    }

    private UnderworldFires(final UnderworldFires card) {
        super(card);
    }

    @Override
    public UnderworldFires copy() {
        return new UnderworldFires(this);
    }
}
