package mage.cards.i;

import java.util.UUID;

import mage.abilities.common.AttacksAttachedTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.PhyrexianMiteToken;

/**
 * @author TheElk801
 */
public final class InfestedFleshcutter extends CardImpl {

    public InfestedFleshcutter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}{W}");

        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +2/+0.
        this.addAbility(new SimpleStaticAbility(new BoostEquippedEffect(2, 0)));

        // Whenever equipped creature attacks, create a 1/1 colorless Phyrexian Mite artifact creature token with toxic 1 and "This creature can't block."
        this.addAbility(new AttacksAttachedTriggeredAbility(new CreateTokenEffect(new PhyrexianMiteToken())));

        // Equip {2}{W}
        this.addAbility(new EquipAbility(Outcome.BoostCreature, new ManaCostsImpl<>("{2}{W}"), false));
    }

    private InfestedFleshcutter(final InfestedFleshcutter card) {
        super(card);
    }

    @Override
    public InfestedFleshcutter copy() {
        return new InfestedFleshcutter(this);
    }
}
