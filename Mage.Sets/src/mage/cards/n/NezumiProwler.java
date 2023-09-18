package mage.cards.n;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.keyword.NinjutsuAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class NezumiProwler extends CardImpl {

    public NezumiProwler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{1}{B}");

        this.subtype.add(SubType.RAT);
        this.subtype.add(SubType.NINJA);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // Ninjutsu {1}{B}
        this.addAbility(new NinjutsuAbility("{1}{B}"));

        // When Nezumi Prowler enters the battlefield, target creature you control gains deathtouch and lifelink until end of turn.
        Ability ability = new EntersBattlefieldTriggeredAbility(
                new GainAbilityTargetEffect(DeathtouchAbility.getInstance())
                        .setText("target creature you control gains deathtouch")
        );
        ability.addEffect(new GainAbilityTargetEffect(LifelinkAbility.getInstance())
                .setText("and lifelink until end of turn"));
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);
    }

    private NezumiProwler(final NezumiProwler card) {
        super(card);
    }

    @Override
    public NezumiProwler copy() {
        return new NezumiProwler(this);
    }
}
