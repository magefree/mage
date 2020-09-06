package mage.cards.k;

import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCreatureOrPlaneswalker;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KabiraTakedown extends CardImpl {

    private static final DynamicValue xValue
            = new PermanentsOnBattlefieldCount(StaticFilters.FILTER_CONTROLLED_CREATURES);

    public KabiraTakedown(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{W}");

        this.modalDFC = true;
        this.secondSideCardClazz = mage.cards.k.KabiraPlateau.class;

        // Kabira Takedown deals damage equal to the number of creatures you control to target creature or planeswalker.
        this.getSpellAbility().addEffect(new DamageTargetEffect(xValue).setText("{this} deals damage equal to the number of creatures you control to target creature or planeswalker"));
        this.getSpellAbility().addTarget(new TargetCreatureOrPlaneswalker());
    }

    private KabiraTakedown(final KabiraTakedown card) {
        super(card);
    }

    @Override
    public KabiraTakedown copy() {
        return new KabiraTakedown(this);
    }
}
