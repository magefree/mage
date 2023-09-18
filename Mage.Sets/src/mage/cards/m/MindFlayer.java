package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
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
public final class MindFlayer extends CardImpl {

    public MindFlayer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}{U}");

        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Dominate Monster — When Mind Flayer enters the battlefield, gain control of target creature for as long as you control Mind Flayer.
        Ability ability = new EntersBattlefieldTriggeredAbility(new GainControlTargetEffect(Duration.WhileControlled));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability.withFlavorWord("Dominate Monster"));
    }

    private MindFlayer(final MindFlayer card) {
        super(card);
    }

    @Override
    public MindFlayer copy() {
        return new MindFlayer(this);
    }
}
