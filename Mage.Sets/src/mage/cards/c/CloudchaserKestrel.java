
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.continuous.BecomesColorTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.target.TargetPermanent;
import mage.target.common.TargetEnchantmentPermanent;

/**
 *
 * @author LoneFox
 */
public final class CloudchaserKestrel extends CardImpl {

    public CloudchaserKestrel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{W}{W}");
        this.subtype.add(SubType.BIRD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // When Cloudchaser Kestrel enters the battlefield, destroy target enchantment.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DestroyTargetEffect(), false);
        ability.addTarget(new TargetEnchantmentPermanent());
        this.addAbility(ability);
        // {W}: Target permanent becomes white until end of turn.
        ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new BecomesColorTargetEffect(ObjectColor.WHITE, Duration.EndOfTurn), new ManaCostsImpl<>("{W}"));
        ability.addTarget(new TargetPermanent());
        this.addAbility(ability);
    }

    private CloudchaserKestrel(final CloudchaserKestrel card) {
        super(card);
    }

    @Override
    public CloudchaserKestrel copy() {
        return new CloudchaserKestrel(this);
    }
}
