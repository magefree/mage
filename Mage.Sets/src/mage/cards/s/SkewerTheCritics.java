package mage.cards.s;

import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.SpectacleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SkewerTheCritics extends CardImpl {

    public SkewerTheCritics(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{R}");

        // Skewer the Critics deals 3 damage to any target.
        this.getSpellAbility().addEffect(new DamageTargetEffect(3));
        this.getSpellAbility().addTarget(new TargetAnyTarget());

        // Spectacle {R}
        this.addAbility(new SpectacleAbility(this, new ManaCostsImpl<>("{R}")));
    }

    private SkewerTheCritics(final SkewerTheCritics card) {
        super(card);
    }

    @Override
    public SkewerTheCritics copy() {
        return new SkewerTheCritics(this);
    }
}
