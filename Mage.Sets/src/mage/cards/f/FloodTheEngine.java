package mage.cards.f;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.DontUntapInControllersUntapStepEnchantedEffect;
import mage.abilities.effects.common.TapEnchantedEffect;
import mage.abilities.effects.common.continuous.LoseAllAbilitiesAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FloodTheEngine extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("creature or Vehicle");

    static {
        filter.add(Predicates.or(
                CardType.CREATURE.getPredicate(),
                SubType.VEHICLE.getPredicate()
        ));
    }

    public FloodTheEngine(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{U}");

        this.subtype.add(SubType.AURA);

        // Enchant creature or Vehicle
        TargetPermanent auraTarget = new TargetPermanent(filter);
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        this.addAbility(new EnchantAbility(auraTarget));

        // When this Aura enters, tap enchanted permanent.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new TapEnchantedEffect("permanent")));

        // Enchanted permanent loses all abilities and doesn't untap during its controller's untap step.
        Ability ability = new SimpleStaticAbility(new LoseAllAbilitiesAttachedEffect(AttachmentType.AURA)
                .setText("enchanted permanent loses all abilities"));
        ability.addEffect(new DontUntapInControllersUntapStepEnchantedEffect()
                .setText("and doesn't untap during its controller's untap step"));
        this.addAbility(ability);
    }

    private FloodTheEngine(final FloodTheEngine card) {
        super(card);
    }

    @Override
    public FloodTheEngine copy() {
        return new FloodTheEngine(this);
    }
}
