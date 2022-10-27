package mage.cards.d;

import java.util.UUID;
import mage.abilities.effects.common.CounterTargetWithReplacementEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.PutCards;
import mage.filter.common.FilterCreatureSpell;
import mage.target.TargetSpell;

/**
 *
 * @author LevelX2
 */
public final class DenyExistence extends CardImpl {

    public DenyExistence(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{U}");

        // Counter target creature spell. If that spell is countered this way, exile it instead of putting it into its owner's graveyard.
        this.getSpellAbility().addEffect(new CounterTargetWithReplacementEffect(PutCards.EXILED));
        this.getSpellAbility().addTarget(new TargetSpell(new FilterCreatureSpell()));
    }

    private DenyExistence(final DenyExistence card) {
        super(card);
    }

    @Override
    public DenyExistence copy() {
        return new DenyExistence(this);
    }
}
