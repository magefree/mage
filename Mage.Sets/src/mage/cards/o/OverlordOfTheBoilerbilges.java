package mage.cards.o;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldOrAttacksSourceTriggeredAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.ImpendingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class OverlordOfTheBoilerbilges extends CardImpl {

    public OverlordOfTheBoilerbilges(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "{4}{R}{R}");

        this.subtype.add(SubType.AVATAR);
        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Impending 4--{2}{R}{R}
        this.addAbility(new ImpendingAbility("{2}{R}{R}"));

        // Whenever Overlord of the Boilerbilges enters or attacks, it deals 4 damage to any target.
        Ability ability = new EntersBattlefieldOrAttacksSourceTriggeredAbility(new DamageTargetEffect(4));
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    private OverlordOfTheBoilerbilges(final OverlordOfTheBoilerbilges card) {
        super(card);
    }

    @Override
    public OverlordOfTheBoilerbilges copy() {
        return new OverlordOfTheBoilerbilges(this);
    }
}
