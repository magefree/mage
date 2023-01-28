package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.counter.ProliferateEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AdaptiveSporesinger extends CardImpl {

    public AdaptiveSporesinger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // When Adaptive Sporesinger enters the battlefield, choose one--
        // * Target creature gets +2/+2 and gains vigilance until end of turn.
        Ability ability = new EntersBattlefieldTriggeredAbility(
                new BoostTargetEffect(2, 2)
                        .setText("target creature gets +2/+2")
        );
        ability.addEffect(new GainAbilityTargetEffect(VigilanceAbility.getInstance())
                .setText("and gains vigilance until end of turn"));
        ability.addTarget(new TargetCreaturePermanent());

        // * Proliferate.
        ability.addMode(new Mode(new ProliferateEffect()));
        this.addAbility(ability);
    }

    private AdaptiveSporesinger(final AdaptiveSporesinger card) {
        super(card);
    }

    @Override
    public AdaptiveSporesinger copy() {
        return new AdaptiveSporesinger(this);
    }
}
