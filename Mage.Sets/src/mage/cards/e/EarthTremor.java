package mage.cards.e;

import mage.abilities.dynamicvalue.common.LandsYouControlCount;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.hint.common.LandsYouControlHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreatureOrPlaneswalker;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EarthTremor extends CardImpl {

    public EarthTremor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{R}");

        // Earth Tremor deals damage to target creature or planeswalker equal to the number of lands you control.
        this.getSpellAbility().addEffect(new DamageTargetEffect(LandsYouControlCount.instance));
        this.getSpellAbility().addTarget(new TargetCreatureOrPlaneswalker());
        this.getSpellAbility().addHint(LandsYouControlHint.instance);
    }

    private EarthTremor(final EarthTremor card) {
        super(card);
    }

    @Override
    public EarthTremor copy() {
        return new EarthTremor(this);
    }
}
