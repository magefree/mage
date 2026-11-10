package mage.cards.p;

import mage.abilities.condition.common.MorbidCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;
import mage.watchers.common.CreaturesDiedWatcher;

import java.util.UUID;

/**
 * @author muz
 */
public final class PlanetaryBombardment extends CardImpl {

    public PlanetaryBombardment(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{R}");

        // Planetary Bombardment deals 2 damage to target creature. Planetary Bombardment deals 4 damage to that creature instead if a creature died this turn.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
            new DamageTargetEffect(4),
            new DamageTargetEffect(2),
            MorbidCondition.instance,
            "{this} deals 2 damage to target creature. {this} deals 4 damage to that creature instead if a creature died this turn"
        ));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addWatcher(new CreaturesDiedWatcher());
    }

    private PlanetaryBombardment(final PlanetaryBombardment card) {
        super(card);
    }

    @Override
    public PlanetaryBombardment copy() {
        return new PlanetaryBombardment(this);
    }
}
