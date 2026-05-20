package mage.cards.q;

import java.util.UUID;

import mage.abilities.Mode;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CounterUnlessPaysEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.TargetSpell;
import mage.target.common.TargetCreaturePermanent;
import mage.target.common.TargetEnchantmentPermanent;

/**
 *
 * @author balazskristof
 */
public final class QuandrixCharm extends CardImpl {

    public QuandrixCharm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{G}{U}");

        // Choose one --
        // * Counter target spell unless its controller pays {2}.
        this.getSpellAbility().addTarget(new TargetSpell());
        this.getSpellAbility().addEffect(new CounterUnlessPaysEffect(new GenericManaCost(2)));
        // * Destroy target enchantment.
        Mode mode = new Mode(new DestroyTargetEffect());
        mode.addTarget(new TargetEnchantmentPermanent());
        this.getSpellAbility().addMode(mode);
        // * Target creature has base power and toughness 5/5 until end of turn.
        Mode mode2 = new Mode(new SetBasePowerToughnessTargetEffect(5, 5, Duration.EndOfTurn));
        mode2.addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addMode(mode2);
    }

    private QuandrixCharm(final QuandrixCharm card) {
        super(card);
    }

    @Override
    public QuandrixCharm copy() {
        return new QuandrixCharm(this);
    }
}
