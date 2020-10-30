package mage.cards.a;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 * @author TheElk801
 */
public final class AngelheartProtector extends CardImpl {

    public AngelheartProtector(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // When Angelheart Protector enters the battlefield, target creature you control gains indestructible until end of turn.
        Ability ability = new EntersBattlefieldTriggeredAbility(
                new GainAbilityTargetEffect(IndestructibleAbility.getInstance(), Duration.EndOfTurn)
        );
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);
    }

    private AngelheartProtector(final AngelheartProtector card) {
        super(card);
    }

    @Override
    public AngelheartProtector copy() {
        return new AngelheartProtector(this);
    }
}
