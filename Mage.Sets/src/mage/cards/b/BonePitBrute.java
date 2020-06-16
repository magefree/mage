package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.keyword.MenaceAbility;
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
public final class BonePitBrute extends CardImpl {

    public BonePitBrute(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}{R}");

        this.subtype.add(SubType.CYCLOPS);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // Menace
        this.addAbility(new MenaceAbility());

        // When Bone Pit Brute enters the battlefield, target creature gets +4/+0 until end of turn.
        Ability ability = new EntersBattlefieldTriggeredAbility(
                new BoostTargetEffect(4, 0, Duration.EndOfTurn)
        );
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private BonePitBrute(final BonePitBrute card) {
        super(card);
    }

    @Override
    public BonePitBrute copy() {
        return new BonePitBrute(this);
    }
}
