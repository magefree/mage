package mage.cards.c;

import mage.abilities.condition.common.HellbentCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author JotaPeRL
 */
public final class CacklingFlames extends CardImpl {

    public CacklingFlames(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{R}");

        // Cackling Flames deals 3 damage to any target.
        // Hellbent - Cackling Flames deals 5 damage to that creature or player instead if you have no cards in hand.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new DamageTargetEffect(5), new DamageTargetEffect(3), HellbentCondition.instance,
                "{this} deals 3 damage to any target.<br><i>Hellbent</i> " +
                        "&mdash; {this} deals 5 damage instead if you have no cards in hand."
        ));
        this.getSpellAbility().addTarget(new TargetAnyTarget());
    }

    private CacklingFlames(final CacklingFlames card) {
        super(card);
    }

    @Override
    public CacklingFlames copy() {
        return new CacklingFlames(this);
    }
}
