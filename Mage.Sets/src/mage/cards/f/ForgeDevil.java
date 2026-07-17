package mage.cards.f;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.DamageTargetAndYouEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author xenohedron
 */
public final class ForgeDevil extends CardImpl {

    public ForgeDevil(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{R}");
        this.subtype.add(SubType.DEVIL);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // When Forge Devil enters the battlefield, it deals 1 damage to target creature and 1 damage to you.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DamageTargetAndYouEffect(1));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private ForgeDevil(final ForgeDevil card) {
        super(card);
    }

    @Override
    public ForgeDevil copy() {
        return new ForgeDevil(this);
    }
}
