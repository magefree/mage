package mage.cards.g;

import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.StormAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author Plopman
 */
public final class Grapeshot extends CardImpl {

    public Grapeshot(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{R}");

        // Grapeshot deals 1 damage to any target.
        this.getSpellAbility().addTarget(new TargetAnyTarget());
        this.getSpellAbility().addEffect(new DamageTargetEffect(1));

        // Storm
        this.addAbility(new StormAbility());
    }

    private Grapeshot(final Grapeshot card) {
        super(card);
    }

    @Override
    public Grapeshot copy() {
        return new Grapeshot(this);
    }
}
