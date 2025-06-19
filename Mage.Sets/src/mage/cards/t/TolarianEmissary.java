package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.KickedCondition;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetEnchantmentPermanent;

import java.util.UUID;

/**
 * @author LoneFox
 */
public final class TolarianEmissary extends CardImpl {

    public TolarianEmissary(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Kicker {1}{W}
        this.addAbility(new KickerAbility("{1}{W}"));

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Tolarian Emissary enters the battlefield, if it was kicked, destroy target enchantment.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DestroyTargetEffect()).withInterveningIf(KickedCondition.ONCE);
        ability.addTarget(new TargetEnchantmentPermanent());
        this.addAbility(ability);
    }

    private TolarianEmissary(final TolarianEmissary card) {
        super(card);
    }

    @Override
    public TolarianEmissary copy() {
        return new TolarianEmissary(this);
    }
}
