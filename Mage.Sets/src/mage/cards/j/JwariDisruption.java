package mage.cards.j;

import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CounterUnlessPaysEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetSpell;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class JwariDisruption extends CardImpl {

    public JwariDisruption(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}");

        this.modalDFC = true;
        this.secondSideCardClazz = mage.cards.j.JwariDisruption.class;

        // Counter target spell unless its controller pays {1}.
        this.getSpellAbility().addEffect(new CounterUnlessPaysEffect(new GenericManaCost(1)));
        this.getSpellAbility().addTarget(new TargetSpell());
    }

    private JwariDisruption(final JwariDisruption card) {
        super(card);
    }

    @Override
    public JwariDisruption copy() {
        return new JwariDisruption(this);
    }
}
