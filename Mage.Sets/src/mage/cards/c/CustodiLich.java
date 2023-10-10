
package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BecomesMonarchSourceControllerTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.BecomesMonarchSourceEffect;
import mage.abilities.effects.common.SacrificeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public final class CustodiLich extends CardImpl {

    public CustodiLich(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}{B}");

        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(4);
        this.toughness = new MageInt(2);

        // When Custodi Lich enters the battlefield, you become the monarch.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new BecomesMonarchSourceEffect(), false));

        // Whenever you become the monarch, target player sacrifices a creature.
        Ability ability = new BecomesMonarchSourceControllerTriggeredAbility(new SacrificeEffect(new FilterControlledCreaturePermanent("creature"), 1, "target player"));
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);

    }

    private CustodiLich(final CustodiLich card) {
        super(card);
    }

    @Override
    public CustodiLich copy() {
        return new CustodiLich(this);
    }
}