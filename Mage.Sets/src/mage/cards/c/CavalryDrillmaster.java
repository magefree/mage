package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author TheElk801
 */
public final class CavalryDrillmaster extends CardImpl {

    public CavalryDrillmaster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // When Cavalry Drillmaster enters the battlefield, target creature +2/+0 and gains first strike until end of turn.
        Ability ability = new EntersBattlefieldTriggeredAbility(
                new BoostTargetEffect(2, 0, Duration.EndOfTurn)
                        .setText("target creature gets +2/+0")
        );
        ability.addEffect(new GainAbilityTargetEffect(
                FirstStrikeAbility.getInstance(),
                Duration.EndOfTurn
        ).setText("and gains first strike until end of turn"));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private CavalryDrillmaster(final CavalryDrillmaster card) {
        super(card);
    }

    @Override
    public CavalryDrillmaster copy() {
        return new CavalryDrillmaster(this);
    }
}
