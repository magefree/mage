package mage.cards.c;

import mage.abilities.effects.common.DamageTargetAndAllControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.common.TargetPlayerOrPlaneswalker;

import java.util.UUID;

/**
 *
 * @author North
 */
public final class ChandrasFury extends CardImpl {

    public ChandrasFury(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{4}{R}");

        // Chandra's Fury deals 4 damage to target player and 1 damage to each creature that player controls.
        this.getSpellAbility().addEffect(new DamageTargetAndAllControlledEffect(4, 1,
                StaticFilters.FILTER_PERMANENT_CREATURE));
        this.getSpellAbility().addTarget(new TargetPlayerOrPlaneswalker());
    }

    private ChandrasFury(final ChandrasFury card) {
        super(card);
    }

    @Override
    public ChandrasFury copy() {
        return new ChandrasFury(this);
    }
}
