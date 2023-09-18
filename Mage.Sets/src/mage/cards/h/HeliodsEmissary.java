
package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksAttachedTriggeredAbility;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.keyword.BestowAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.target.Target;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class HeliodsEmissary extends CardImpl {

    public HeliodsEmissary(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT,CardType.CREATURE},"{3}{W}");
        this.subtype.add(SubType.ELK);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Bestow {6}{W}
        this.addAbility(new BestowAbility(this, "{6}{W}"));
        // Whenever Heliod's Emissary or enchanted creature attacks, tap target creature an opponent controls.
        Ability ability = new AttacksTriggeredAbility(new TapTargetEffect(), false);
        Target target = new TargetCreaturePermanent(StaticFilters.FILTER_OPPONENTS_PERMANENT_CREATURE);
        ability.addTarget(target);
        this.addAbility(ability);
        ability = new AttacksAttachedTriggeredAbility(new TapTargetEffect(), AttachmentType.AURA, false);
        target = new TargetCreaturePermanent(StaticFilters.FILTER_OPPONENTS_PERMANENT_CREATURE);
        ability.addTarget(target);
        this.addAbility(ability);
        // Enchanted creature gets +3/+3.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEnchantedEffect(3,3, Duration.WhileOnBattlefield)));
    }

    private HeliodsEmissary(final HeliodsEmissary card) {
        super(card);
    }

    @Override
    public HeliodsEmissary copy() {
        return new HeliodsEmissary(this);
    }
}
