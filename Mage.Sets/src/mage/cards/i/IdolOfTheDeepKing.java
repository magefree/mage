package mage.cards.i;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.CraftAbility;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class IdolOfTheDeepKing extends CardImpl {

    public IdolOfTheDeepKing(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}{R}");
        this.secondSideCardClazz = mage.cards.s.SovereignsMacuahuitl.class;

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // When Idol of the Deep King enters the battlefield, it deals 2 damage to any target.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DamageTargetEffect(2));
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);

        // Craft with artifact {2}{R}
        this.addAbility(new CraftAbility("{2}{R}"));
    }

    private IdolOfTheDeepKing(final IdolOfTheDeepKing card) {
        super(card);
    }

    @Override
    public IdolOfTheDeepKing copy() {
        return new IdolOfTheDeepKing(this);
    }
}
