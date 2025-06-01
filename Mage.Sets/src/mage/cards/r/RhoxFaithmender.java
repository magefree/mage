package mage.cards.r;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.replacement.GainDoubleLifeReplacementEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author noxx and jeffwadsworth
 */
public final class RhoxFaithmender extends CardImpl {

    public RhoxFaithmender(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");
        this.subtype.add(SubType.RHINO);
        this.subtype.add(SubType.MONK);

        this.power = new MageInt(1);
        this.toughness = new MageInt(5);

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // If you would gain life, you gain twice that much life instead.
        this.addAbility(new SimpleStaticAbility(new GainDoubleLifeReplacementEffect()));
    }

    private RhoxFaithmender(final RhoxFaithmender card) {
        super(card);
    }

    @Override
    public RhoxFaithmender copy() {
        return new RhoxFaithmender(this);
    }
}
