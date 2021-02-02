package mage.cards.d;

import java.util.UUID;

import mage.abilities.effects.common.SacrificeOpponentsEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterControlledCreaturePermanent;

/**
 *
 * @author NinthWorld
 */
public final class Despair extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("creature");

    public Despair(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{B}{B}");
        

        // Each opponent sacrifices a creature.
        this.getSpellAbility().addEffect(new SacrificeOpponentsEffect(filter));
    }

    private Despair(final Despair card) {
        super(card);
    }

    @Override
    public Despair copy() {
        return new Despair(this);
    }
}
