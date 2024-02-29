package mage.cards.n;

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
public final class NoMoreLies extends CardImpl {

    public NoMoreLies(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{W}{U}");

        // Counter target spell unless its controller pays {3}. If that spell is countered this way, exile it instead of putting it into its owner's graveyard.
        this.getSpellAbility().addEffect(new CounterUnlessPaysEffect(new GenericManaCost(3), true));
        this.getSpellAbility().addTarget(new TargetSpell());
    }

    private NoMoreLies(final NoMoreLies card) {
        super(card);
    }

    @Override
    public NoMoreLies copy() {
        return new NoMoreLies(this);
    }
}
