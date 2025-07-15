package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.common.CreaturesYouControlCount;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.hint.common.CreaturesYouControlHint;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TreeguardDuo extends CardImpl {

    public TreeguardDuo(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");

        this.subtype.add(SubType.FROG);
        this.subtype.add(SubType.RABBIT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // When Treeguard Duo enters, until end of turn, target creature you control gains vigilance and gets +X/+X, where X is the number of creatures you control.
        Ability ability = new EntersBattlefieldTriggeredAbility(
                new GainAbilityTargetEffect(VigilanceAbility.getInstance())
                        .setText("until end of turn, target creature you control gains vigilance")
        );
        ability.addEffect(new BoostTargetEffect(
                CreaturesYouControlCount.PLURAL, CreaturesYouControlCount.PLURAL
        ).setText("and gets +X/+X, where X is the number of creatures you control"));
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability.addHint(CreaturesYouControlHint.instance));
    }

    private TreeguardDuo(final TreeguardDuo card) {
        super(card);
    }

    @Override
    public TreeguardDuo copy() {
        return new TreeguardDuo(this);
    }
}
