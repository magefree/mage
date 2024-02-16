package mage.cards.s;

import mage.abilities.Mode;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CounterUnlessPaysEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetSpell;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SoulRead extends CardImpl {

    public SoulRead(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{U}");

        // Choose one --
        // * Counter target spell unless its controller pays {4}.
        this.getSpellAbility().addEffect(new CounterUnlessPaysEffect(new GenericManaCost(4)));
        this.getSpellAbility().addTarget(new TargetSpell());

        // * Draw two cards.
        this.getSpellAbility().addMode(new Mode(new DrawCardSourceControllerEffect(2)));
    }

    private SoulRead(final SoulRead card) {
        super(card);
    }

    @Override
    public SoulRead copy() {
        return new SoulRead(this);
    }
}
