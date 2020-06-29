package mage.cards.f;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FootlightFiend extends CardImpl {

    public FootlightFiend(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B/R}");

        this.subtype.add(SubType.DEVIL);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // When Footlight Fiend dies, it deals 1 damage to any target.
        Ability ability = new DiesSourceTriggeredAbility(new DamageTargetEffect(1, "it"));
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    private FootlightFiend(final FootlightFiend card) {
        super(card);
    }

    @Override
    public FootlightFiend copy() {
        return new FootlightFiend(this);
    }
}
