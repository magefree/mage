package mage.cards.d;

import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CounterUnlessPaysEffect;
import mage.abilities.effects.keyword.SurveilEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetSpell;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DontMakeASound extends CardImpl {

    public DontMakeASound(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}");

        // Counter target spell unless its controller pays {2}. If they do, surveil 2.
        this.getSpellAbility().addEffect(
                new CounterUnlessPaysEffect(new GenericManaCost(2))
                        .withIfTheyDo(new SurveilEffect(2))
        );
        this.getSpellAbility().addTarget(new TargetSpell());
    }

    private DontMakeASound(final DontMakeASound card) {
        super(card);
    }

    @Override
    public DontMakeASound copy() {
        return new DontMakeASound(this);
    }
}