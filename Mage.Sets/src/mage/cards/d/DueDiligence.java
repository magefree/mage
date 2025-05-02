package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.AnotherEnchantedPredicate;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DueDiligence extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledCreaturePermanent("creature you control other than enchanted creature");

    static {
        filter.add(AnotherEnchantedPredicate.instance);
    }

    public DueDiligence(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}");

        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        this.addAbility(new EnchantAbility(auraTarget));

        // When Due Diligence enters the battlefield, target creature you control other than enchanted creature gets +2/+2 and gains vigilance until end of turn.
        Ability ability = new EntersBattlefieldTriggeredAbility(new BoostTargetEffect(2, 2)
                .setText("target creature you control other than enchanted creature gets +2/+2"));
        ability.addEffect(new GainAbilityTargetEffect(VigilanceAbility.getInstance())
                .setText("and gains vigilance until end of turn"));
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);

        // Enchanted creature gets +2/+2 and has vigilance.
        ability = new SimpleStaticAbility(new BoostEnchantedEffect(2, 2));
        ability.addEffect(new GainAbilityAttachedEffect(
                VigilanceAbility.getInstance(), AttachmentType.AURA
        ).setText("and has vigilance"));
        this.addAbility(ability);
    }

    private DueDiligence(final DueDiligence card) {
        super(card);
    }

    @Override
    public DueDiligence copy() {
        return new DueDiligence(this);
    }
}
