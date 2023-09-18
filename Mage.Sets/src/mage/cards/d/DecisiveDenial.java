package mage.cards.d;

import mage.abilities.Mode;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CounterUnlessPaysEffect;
import mage.abilities.effects.common.FightTargetsEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.TargetSpell;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DecisiveDenial extends CardImpl {

    public DecisiveDenial(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{G}{U}");

        // Choose one —
        // • Target creature you control fights target creature you don't control.
        this.getSpellAbility().addEffect(new FightTargetsEffect()
                .setText("target creature you control fights target creature you don't control. " +
                        "<i>(Each deals damage equal to its power to the other.)</i>"));
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(StaticFilters.FILTER_CREATURE_YOU_DONT_CONTROL));

        // • Counter target noncreature spell unless its controller pays {3}.
        Mode mode = new Mode(new CounterUnlessPaysEffect(new GenericManaCost(3)));
        mode.addTarget(new TargetSpell(StaticFilters.FILTER_SPELL_NON_CREATURE));
        this.getSpellAbility().addMode(mode);
    }

    private DecisiveDenial(final DecisiveDenial card) {
        super(card);
    }

    @Override
    public DecisiveDenial copy() {
        return new DecisiveDenial(this);
    }
}
