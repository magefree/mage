
package mage.cards.h;

import java.util.UUID;
import mage.abilities.effects.common.CounterTargetWithReplacementEffect;
import mage.abilities.keyword.DevoidAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.PutCards;
import mage.filter.common.FilterCreatureSpell;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.target.TargetSpell;

/**
 *
 * @author LevelX2
 */
public final class HorriblyAwry extends CardImpl {

    private static final FilterCreatureSpell filter = new FilterCreatureSpell("creature spell with mana value 4 or less");

    static {
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, 5));
    }

    public HorriblyAwry(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}");

        // Devoid (This card has no color.)
        this.addAbility(new DevoidAbility(this.color));

        // Counter target creature spell with converted mana cost 4 or less. If that spell is countered this way, exile it instead of putting it into its owner's graveyard.
        this.getSpellAbility().addEffect(new CounterTargetWithReplacementEffect(PutCards.EXILED));
        this.getSpellAbility().addTarget(new TargetSpell(filter));
    }

    private HorriblyAwry(final HorriblyAwry card) {
        super(card);
    }

    @Override
    public HorriblyAwry copy() {
        return new HorriblyAwry(this);
    }
}
