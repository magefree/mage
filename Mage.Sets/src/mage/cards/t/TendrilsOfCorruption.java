package mage.cards.t;

import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author Loki
 */
public final class TendrilsOfCorruption extends CardImpl {

    private static final DynamicValue xValue2 = new PermanentsOnBattlefieldCount(new FilterControlledPermanent(SubType.SWAMP));
    private static final Hint hint = new ValueHint("Swamps you control", xValue2);

    private static final FilterPermanent filter = new FilterControlledPermanent();

    static {
        filter.add(SubType.SWAMP.getPredicate());
    }

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(filter);

    public TendrilsOfCorruption(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{B}");

        this.getSpellAbility().addEffect(new DamageTargetEffect(xValue)
                .setText("{this} deals X damage to target creature"));
        this.getSpellAbility().addEffect(new GainLifeEffect(xValue)
                .setText("and you gain X life, where X is the number of Swamps you control"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addHint(hint);
    }

    private TendrilsOfCorruption(final TendrilsOfCorruption card) {
        super(card);
    }

    @Override
    public TendrilsOfCorruption copy() {
        return new TendrilsOfCorruption(this);
    }
}
