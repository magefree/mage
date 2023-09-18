
package mage.cards.i;

import java.util.UUID;
import mage.abilities.Mode;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CounterUnlessPaysEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.TargetSpell;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class IzzetCharm extends CardImpl {

    public IzzetCharm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{U}{R}");

        // Choose one — Counter target noncreature spell unless its controller pays {2};
        this.getSpellAbility().addEffect(new CounterUnlessPaysEffect(new GenericManaCost(2)));
        this.getSpellAbility().getTargets().add(new TargetSpell(StaticFilters.FILTER_SPELL_NON_CREATURE));

        // or Izzet Charm deals 2 damage to target creature;
        Mode mode = new Mode(new DamageTargetEffect(2));
        mode.addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addMode(mode);

        //  or draw two cards, then discard two cards.
        mode = new Mode(new DrawDiscardControllerEffect(2, 2));
        this.getSpellAbility().addMode(mode);
    }

    private IzzetCharm(final IzzetCharm card) {
        super(card);
    }

    @Override
    public IzzetCharm copy() {
        return new IzzetCharm(this);
    }
}