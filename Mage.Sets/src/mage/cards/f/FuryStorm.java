package mage.cards.f;

import java.util.UUID;
import mage.abilities.effects.common.CopyTargetSpellEffect;
import mage.abilities.keyword.CommanderStormAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.TargetSpell;

/**
 *
 * @author TheElk801
 */
public final class FuryStorm extends CardImpl {

    public FuryStorm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{R}{R}");

        // When you cast this spell, copy it for each time you've cast your commander from the command zone this game. You may choose new targets for the copies.
        this.addAbility(new CommanderStormAbility());

        // Copy target instant or sorcery spell. You may choose new targets for the copy.
        this.getSpellAbility().addEffect(new CopyTargetSpellEffect());
        this.getSpellAbility().addTarget(new TargetSpell(StaticFilters.FILTER_SPELL_INSTANT_OR_SORCERY));
    }

    private FuryStorm(final FuryStorm card) {
        super(card);
    }

    @Override
    public FuryStorm copy() {
        return new FuryStorm(this);
    }
}
