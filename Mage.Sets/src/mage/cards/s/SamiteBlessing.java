package mage.cards.s;

import java.util.UUID;
import mage.constants.SubType;
import mage.target.common.TargetCreaturePermanent;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.PreventNextDamageFromChosenSourceToTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.constants.Outcome;
import mage.target.TargetPermanent;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;

/**
 *
 * @author jeffwadsworth
 */
public final class SamiteBlessing extends CardImpl {

    private static final String rule = "Enchanted creature has \"{T}: The next time a source of your choice would deal damage to target creature this turn, prevent that damage.\"";

    public SamiteBlessing(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{W}");

        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // Enchanted creature has "{tap}: The next time a source of your choice would deal damage to target creature this turn, prevent that damage."
        Ability ability2 = new SimpleActivatedAbility(Zone.BATTLEFIELD, new PreventNextDamageFromChosenSourceToTargetEffect(Duration.EndOfTurn), new TapSourceCost());
        ability2.addTarget(new TargetCreaturePermanent());
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityAttachedEffect(ability2, AttachmentType.AURA, Duration.WhileOnBattlefield, rule)));

    }

    private SamiteBlessing(final SamiteBlessing card) {
        super(card);
    }

    @Override
    public SamiteBlessing copy() {
        return new SamiteBlessing(this);
    }
}
