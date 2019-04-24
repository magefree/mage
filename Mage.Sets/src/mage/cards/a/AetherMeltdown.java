
package mage.cards.a;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.effects.common.counter.GetEnergyCountersControllerEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.target.TargetPermanent;

/**
 *
 * @author LevelX2
 */
public final class AetherMeltdown extends CardImpl {

    private final static FilterPermanent filter = new FilterPermanent("creature or vehicle");

    static {
        filter.add(Predicates.or(new CardTypePredicate(CardType.CREATURE), new SubtypePredicate(SubType.VEHICLE)));
    }

    public AetherMeltdown(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{U}");
        this.subtype.add(SubType.AURA);

        // Flash
        this.addAbility(FlashAbility.getInstance());
        // Enchant creature or vehicle.
        TargetPermanent auraTarget = new TargetPermanent(filter);
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Detriment));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);

        // When Aether Meltdown enters the battlefield, you get {E}{E}.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new GetEnergyCountersControllerEffect(2)));
        // Enchanted permanent gets -4/-0.
        Effect effect = new BoostEnchantedEffect(-4, 0, Duration.WhileOnBattlefield);
        effect.setText("Enchanted permanent gets -4/-0");
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect));
    }

    public AetherMeltdown(final AetherMeltdown card) {
        super(card);
    }

    @Override
    public AetherMeltdown copy() {
        return new AetherMeltdown(this);
    }
}
