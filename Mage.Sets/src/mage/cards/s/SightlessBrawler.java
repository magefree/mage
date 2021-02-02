
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.combat.CantAttackAloneAttachedEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.keyword.BestowAbility;
import mage.abilities.keyword.CantAttackAloneAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;

/**
 *
 * @author LevelX2
 */
public final class SightlessBrawler extends CardImpl {

    public SightlessBrawler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT,CardType.CREATURE},"{1}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Bestow 4W (If you cast this card for its bestow cost, it's an Aura spell with enchant creature. It becomes a creature again if it's not attached to a creature.)
        this.addAbility(new BestowAbility(this, "{4}{W}"));
        // Sightless Brawler can't attack alone.
        this.addAbility(new CantAttackAloneAbility());
        // Enchanted creature gets +3/+2 and can't attack alone.
        Effect effect = new BoostEnchantedEffect(3, 2, Duration.WhileOnBattlefield);
        effect.setText("Enchanted creature gets +3/+2");
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, effect);
        effect = new CantAttackAloneAttachedEffect(AttachmentType.AURA);
        effect.setText("and can't attack alone");
        ability.addEffect(effect);
        this.addAbility(ability);

    }

    private SightlessBrawler(final SightlessBrawler card) {
        super(card);
    }

    @Override
    public SightlessBrawler copy() {
        return new SightlessBrawler(this);
    }
}
