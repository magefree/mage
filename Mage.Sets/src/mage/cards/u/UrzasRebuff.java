package mage.cards.u;

import mage.abilities.Mode;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetSpell;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class UrzasRebuff extends CardImpl {

    public UrzasRebuff(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}{U}");

        // Choose one --
        // * Counter target spell.
        this.getSpellAbility().addEffect(new CounterTargetEffect());
        this.getSpellAbility().addTarget(new TargetSpell());

        // * Tap up to two target creatures.
        this.getSpellAbility().addMode(new Mode(new TapTargetEffect()).addTarget(new TargetCreaturePermanent(0, 2)));
    }

    private UrzasRebuff(final UrzasRebuff card) {
        super(card);
    }

    @Override
    public UrzasRebuff copy() {
        return new UrzasRebuff(this);
    }
}
