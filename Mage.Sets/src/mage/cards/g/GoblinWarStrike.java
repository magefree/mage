package mage.cards.g;

import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPermanent;
import mage.target.common.TargetPlayerOrPlaneswalker;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class GoblinWarStrike extends CardImpl {

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(
            new FilterControlledPermanent(SubType.GOBLIN, "Goblins you control"), null
    );
    private static final Hint hint = new ValueHint("Goblins you control", xValue);

    public GoblinWarStrike(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{R}");

        // Goblin War Strike deals damage equal to the number of Goblins you control to target player.
        this.getSpellAbility().addEffect(new DamageTargetEffect(xValue));
        this.getSpellAbility().addTarget(new TargetPlayerOrPlaneswalker());
        this.getSpellAbility().addHint(hint);
    }

    private GoblinWarStrike(final GoblinWarStrike card) {
        super(card);
    }

    @Override
    public GoblinWarStrike copy() {
        return new GoblinWarStrike(this);
    }
}
