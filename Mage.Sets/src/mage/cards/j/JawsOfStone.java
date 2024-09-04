package mage.cards.j;

import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageMultiEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledLandPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.target.common.TargetAnyTargetAmount;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class JawsOfStone extends CardImpl {

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(new FilterControlledPermanent(SubType.MOUNTAIN));
    private static final Hint hint = new ValueHint("Mountains you control", xValue);

    static final private FilterControlledLandPermanent filter = new FilterControlledLandPermanent("mountains you control");

    static {
        filter.add(SubType.MOUNTAIN.getPredicate());
    }

    static final private String rule = "{this} deals X damage divided as you choose among any number of targets, where X is the number of Mountains you control as you cast this spell";

    public JawsOfStone(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{5}{R}");

        // Jaws of Stone deals X damage divided as you choose among any number of targets, where X is the number of Mountains you control as you cast this spell.
        PermanentsOnBattlefieldCount mountains = new PermanentsOnBattlefieldCount(filter, null);
        Effect effect = new DamageMultiEffect(mountains);
        effect.setText(rule);
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetAnyTargetAmount(mountains));
        this.getSpellAbility().addHint(hint);
    }

    private JawsOfStone(final JawsOfStone card) {
        super(card);
    }

    @Override
    public JawsOfStone copy() {
        return new JawsOfStone(this);
    }
}
