package mage.cards.j;

import java.util.UUID;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageMultiEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledLandPermanent;
import mage.target.common.TargetAnyTargetAmount;

/**
 *
 * @author jeffwadsworth
 */
public final class JawsOfStone extends CardImpl {

    static final private FilterControlledLandPermanent filter = new FilterControlledLandPermanent("mountains you control");

    static {
        filter.add(SubType.MOUNTAIN.getPredicate());
    }

    static final private String rule = "{this} deals X damage divided as you choose among any number of targets, where X is the number of Mountains you control as you cast this spell";

    public JawsOfStone(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{5}{R}");

        // Jaws of Stone deals X damage divided as you choose among any number of targets, where X is the number of Mountains you control as you cast this spell.
        PermanentsOnBattlefieldCount mountains = new PermanentsOnBattlefieldCount(filter, null);
        Effect effect = new DamageMultiEffect(mountains);
        effect.setText(rule);
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetAnyTargetAmount(mountains));
    }

    private JawsOfStone(final JawsOfStone card) {
        super(card);
    }

    @Override
    public JawsOfStone copy() {
        return new JawsOfStone(this);
    }
}
