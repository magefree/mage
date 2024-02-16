package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.DesertControlledOrGraveyardCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author spjspj
 */
public final class SandStrangler extends CardImpl {

    public SandStrangler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");

        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When Sand Strangler enters the battlefield, if you control a Desert or there is a Desert card in your graveyard, you may have Sand Strangler deal 3 damage to target creature.
        Ability ability = new ConditionalInterveningIfTriggeredAbility(
                new EntersBattlefieldTriggeredAbility(new DamageTargetEffect(3), true),
                DesertControlledOrGraveyardCondition.instance, "When {this} enters the battlefield, " +
                "if you control a Desert or there is a Desert card in your graveyard, " +
                "you may have {this} deal 3 damage to target creature.");
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability.addHint(DesertControlledOrGraveyardCondition.getHint()));
    }

    private SandStrangler(final SandStrangler card) {
        super(card);
    }

    @Override
    public SandStrangler copy() {
        return new SandStrangler(this);
    }
}
