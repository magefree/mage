
package mage.cards.c;

import java.util.UUID;
import mage.abilities.Mode;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.SacrificeEffect;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.TargetPlayer;
import mage.target.TargetSpell;

/**
 *
 * @author Styxo
 */
public final class CrueltyOfTheSith extends CardImpl {

    public CrueltyOfTheSith(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{U}{B}{R}");

        // Choose one - Counter target noncreature spell.
        this.getSpellAbility().addEffect(new CounterTargetEffect());
        this.getSpellAbility().addTarget(new TargetSpell(StaticFilters.FILTER_SPELL_NON_CREATURE));

        // Target player sacrifices a creture.
        Mode mode = new Mode();
        mode.addEffect(new SacrificeEffect(StaticFilters.FILTER_PERMANENT_CREATURE, 1, "Target player"));
        mode.addTarget(new TargetPlayer());
        this.getSpellAbility().addMode(mode);

        // Cruelty of the Sith deals 3 damage to target player. That player discards a card.
        mode = new Mode();
        mode.addEffect(new DamageTargetEffect(3));
        mode.addEffect(new DiscardTargetEffect(1));
        mode.addTarget(new TargetPlayer());
        this.getSpellAbility().addMode(mode);

    }

    private CrueltyOfTheSith(final CrueltyOfTheSith card) {
        super(card);
    }

    @Override
    public CrueltyOfTheSith copy() {
        return new CrueltyOfTheSith(this);
    }
}
