package mage.cards.c;

import java.util.UUID;
import mage.abilities.effects.common.SacrificeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterAttackingOrBlockingCreature;
import mage.target.TargetPlayer;

/**
 *
 * @author jeffwadsworth
 */
public final class CelestialFlare extends CardImpl {

    private static final FilterAttackingOrBlockingCreature filter = new FilterAttackingOrBlockingCreature("an attacking or blocking creature");

    public CelestialFlare(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{W}{W}");


        // Target player sacrifices an attacking or blocking creature.
        this.getSpellAbility().addEffect(new SacrificeEffect(filter, 1, "Target player"));
        this.getSpellAbility().addTarget(new TargetPlayer());

    }

    private CelestialFlare(final CelestialFlare card) {
        super(card);
    }

    @Override
    public CelestialFlare copy() {
        return new CelestialFlare(this);
    }
}
