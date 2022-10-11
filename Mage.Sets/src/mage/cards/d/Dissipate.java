package mage.cards.d;

import java.util.UUID;
import mage.abilities.effects.common.CounterTargetWithReplacementEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.PutCards;
import mage.target.TargetSpell;

/**
 * @author nantuko
 */
public final class Dissipate extends CardImpl {

    public Dissipate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{U}{U}");


        // Counter target spell. If that spell is countered this way, exile it instead of putting it into its owner's graveyard.
        this.getSpellAbility().addEffect(new CounterTargetWithReplacementEffect(PutCards.EXILED));
        this.getSpellAbility().addTarget(new TargetSpell());
    }

    private Dissipate(final Dissipate card) {
        super(card);
    }

    @Override
    public Dissipate copy() {
        return new Dissipate(this);
    }
}
