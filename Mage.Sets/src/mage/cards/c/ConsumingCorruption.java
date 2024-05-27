package mage.cards.c;

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
import mage.filter.common.FilterControlledPermanent;
import mage.target.common.TargetCreatureOrPlaneswalker;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ConsumingCorruption extends CardImpl {

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(new FilterControlledPermanent(SubType.SWAMP));
    private static final Hint hint = new ValueHint("Swamps you control", xValue);

    public ConsumingCorruption(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{B}{B}");

        // Consuming Corruption deals X damage to target creature or planeswalker and you gain X life, where X is the number of Swamps you control.
        this.getSpellAbility().addEffect(new DamageTargetEffect(xValue).setText("deals X damage to target creature or planeswalker"));
        this.getSpellAbility().addEffect(new GainLifeEffect(xValue).setText("and you gain X life, where X is the number of Swamps you control"));
        this.getSpellAbility().addTarget(new TargetCreatureOrPlaneswalker());
        this.getSpellAbility().addHint(hint);
    }

    private ConsumingCorruption(final ConsumingCorruption card) {
        super(card);
    }

    @Override
    public ConsumingCorruption copy() {
        return new ConsumingCorruption(this);
    }
}
