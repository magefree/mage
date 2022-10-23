
package mage.cards.i;

import java.util.UUID;
import mage.constants.SubType;
import mage.target.common.TargetCreaturePermanent;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.AddCardSubtypeAttachedEffect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.constants.Outcome;
import mage.target.TargetPermanent;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;

/**
 *
 * @author TheElk801
 */
public final class InnerDemon extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("all non-Demon creatures");

    static {
        filter.add(Predicates.not(SubType.DEMON.getPredicate()));
    }

    public InnerDemon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{B}{B}");

        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // Enchanted creature gets +2/+2, has flying, and is a Demon in addition to its other types.
        ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEnchantedEffect(2, 2, Duration.WhileOnBattlefield));
        Effect effect = new GainAbilityAttachedEffect(FlyingAbility.getInstance(), AttachmentType.AURA);
        effect.setText(", has flying");
        ability.addEffect(effect);
        effect = new AddCardSubtypeAttachedEffect(SubType.DEMON, Duration.WhileOnBattlefield, AttachmentType.AURA);
        effect.setText(", and is a Demon in addition to its other types");
        ability.addEffect(effect);
        this.addAbility(ability);

        // When Inner Demon enters the battlefield, all non-Demon creatures get -2/-2 until end of turn.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new BoostAllEffect(-2, -2, Duration.EndOfTurn, filter, false)));
    }

    private InnerDemon(final InnerDemon card) {
        super(card);
    }

    @Override
    public InnerDemon copy() {
        return new InnerDemon(this);
    }
}
