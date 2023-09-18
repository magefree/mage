package mage.cards.d;

import java.util.UUID;
import mage.constants.SubType;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.constants.Outcome;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author jeffwadsworth
 */
public final class DizzyingGaze extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("with flying");

    static {
        filter.add(new AbilityPredicate(FlyingAbility.class));
    }

    public DizzyingGaze(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{R}");

        this.subtype.add(SubType.AURA);

        // Enchant creature you control
        TargetPermanent auraTarget = new TargetControlledCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // {R}: Enchanted creature deals 1 damage to target creature with flying.
        Ability ability2 = new SimpleActivatedAbility(new DamageTargetEffect(1), new ManaCostsImpl<>("{R}"));
        ability2.addTarget(new TargetCreaturePermanent(filter));
        this.addAbility(new SimpleStaticAbility(
                Zone.BATTLEFIELD,
                new GainAbilityAttachedEffect(
                        ability2,
                        AttachmentType.AURA,
                        Duration.WhileOnBattlefield)));

    }

    private DizzyingGaze(final DizzyingGaze card) {
        super(card);
    }

    @Override
    public DizzyingGaze copy() {
        return new DizzyingGaze(this);
    }
}
