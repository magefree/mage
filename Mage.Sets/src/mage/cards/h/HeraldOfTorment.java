
package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.BestowAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.constants.Zone;

/**
 *
 * @author Quercitron
 */
public final class HeraldOfTorment extends CardImpl {

    public HeraldOfTorment(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT,CardType.CREATURE},"{1}{B}{B}");
        this.subtype.add(SubType.DEMON);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Bestow {3}{B}{B}
        this.addAbility(new BestowAbility(this, "{3}{B}{B}"));

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // At the beginning of your upkeep, you lose 1 life.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new LoseLifeSourceControllerEffect(1), TargetController.YOU, false));

        // Enchanted creature gets +3/+3 and has flying.
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEnchantedEffect(3, 3));
        Effect effect = new GainAbilityAttachedEffect(FlyingAbility.getInstance(), AttachmentType.AURA);
        effect.setText("and has flying");
        ability.addEffect(effect);
        this.addAbility(ability);
    }

    private HeraldOfTorment(final HeraldOfTorment card) {
        super(card);
    }

    @Override
    public HeraldOfTorment copy() {
        return new HeraldOfTorment(this);
    }
}
