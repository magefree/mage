package mage.cards.m;

import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CounterUnlessPaysEffect;
import mage.abilities.keyword.CasualtyAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetSpell;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MakeDisappear extends CardImpl {

    public MakeDisappear(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}");

        // Casualty 1
        this.addAbility(new CasualtyAbility(1));

        // Counter target spell unless its controller pays {2}.
        this.getSpellAbility().addEffect(new CounterUnlessPaysEffect(new GenericManaCost(2)));
        this.getSpellAbility().addTarget(new TargetSpell());
    }

    private MakeDisappear(final MakeDisappear card) {
        super(card);
    }

    @Override
    public MakeDisappear copy() {
        return new MakeDisappear(this);
    }
}
