package mage.cards.k;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KarfellKennelMaster extends CardImpl {

    public KarfellKennelMaster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}");

        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.BERSERKER);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // When Karfell Kennel-Master enters the battlefield, up to two target creatures each get +1/+0 and gain indestructible until end of turn.
        Ability ability = new EntersBattlefieldTriggeredAbility(
                new BoostTargetEffect(1, 0)
                        .setText("up to two target creatures each get +1/+0")
        );
        ability.addEffect(new GainAbilityTargetEffect(
                IndestructibleAbility.getInstance(), Duration.EndOfTurn
        ).setText("and gain indestructible until end of turn"));
        ability.addTarget(new TargetCreaturePermanent(0, 2));
        this.addAbility(ability);
    }

    private KarfellKennelMaster(final KarfellKennelMaster card) {
        super(card);
    }

    @Override
    public KarfellKennelMaster copy() {
        return new KarfellKennelMaster(this);
    }
}
