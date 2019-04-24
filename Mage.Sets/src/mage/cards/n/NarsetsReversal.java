package mage.cards.n;

import mage.abilities.effects.common.CopyTargetSpellEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.TargetSpell;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class NarsetsReversal extends CardImpl {

    public NarsetsReversal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{U}{U}");

        // Copy target instant or sorcery spell, then return it to its owner's hand. You may choose new targets for the copy.
        this.getSpellAbility().addEffect(new CopyTargetSpellEffect()
                .setText("Copy target instant or sorcery spell,"));
        this.getSpellAbility().addEffect(new ReturnToHandTargetEffect()
                .setText("then return it to its owner's hand. You may choose new targets for the copy."));
        this.getSpellAbility().addTarget(new TargetSpell(StaticFilters.FILTER_SPELL_INSTANT_OR_SORCERY));
    }

    private NarsetsReversal(final NarsetsReversal card) {
        super(card);
    }

    @Override
    public NarsetsReversal copy() {
        return new NarsetsReversal(this);
    }
}
