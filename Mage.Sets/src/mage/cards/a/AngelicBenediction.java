package mage.cards.a;

import mage.abilities.Ability;
import mage.abilities.common.AttacksAloneControlledTriggeredAbility;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.keyword.ExaltedAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author North
 */
public final class AngelicBenediction extends CardImpl {

    public AngelicBenediction(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{W}");

        // Exalted
        this.addAbility(new ExaltedAbility());

        // Whenever a creature you control attacks alone, you may tap target creature.
        Ability ability = new AttacksAloneControlledTriggeredAbility(
                new TapTargetEffect(), false, true
        );
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private AngelicBenediction(final AngelicBenediction card) {
        super(card);
    }

    @Override
    public AngelicBenediction copy() {
        return new AngelicBenediction(this);
    }
}
