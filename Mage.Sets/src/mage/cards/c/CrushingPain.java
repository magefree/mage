package mage.cards.c;

import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author LevelX
 */
public final class CrushingPain extends CardImpl {

    public CrushingPain(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{R}");
        this.subtype.add(SubType.ARCANE);

        // Crushing Pain deals 6 damage to target creature that was dealt damage this turn.
        this.getSpellAbility().addEffect(new DamageTargetEffect(6));
        this.getSpellAbility().addTarget(new TargetPermanent(StaticFilters.FILTER_CREATURE_DAMAGED_THIS_TURN));
    }

    private CrushingPain(final CrushingPain card) {
        super(card);
    }

    @Override
    public CrushingPain copy() {
        return new CrushingPain(this);
    }
}
