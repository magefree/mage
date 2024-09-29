package mage.cards.d;

import java.util.UUID;

import mage.ObjectColor;
import mage.abilities.common.DiesAttachedTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.effects.common.LoseLifeControllerAttachedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.CumulativeUpkeepAbility;
import mage.constants.*;
import mage.abilities.effects.common.AttachEffect;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.target.TargetPermanent;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.target.common.TargetCreaturePermanent;

/**
 * @author Cguy7777
 */
public final class Decomposition extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("black creature");

    static {
        filter.add(new ColorPredicate(ObjectColor.BLACK));
    }

    public Decomposition(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{G}");

        this.subtype.add(SubType.AURA);

        // Enchant black creature
        TargetPermanent auraTarget = new TargetCreaturePermanent(filter);
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Detriment));
        this.addAbility(new EnchantAbility(auraTarget));

        // Enchanted creature has "Cumulative upkeep-Pay 1 life."
        this.addAbility(new SimpleStaticAbility(new GainAbilityAttachedEffect(
                new CumulativeUpkeepAbility(new PayLifeCost(1)), AttachmentType.AURA, Duration.WhileOnBattlefield)));

        // When enchanted creature dies, its controller loses 2 life.
        this.addAbility(new DiesAttachedTriggeredAbility(new LoseLifeControllerAttachedEffect(2), "enchanted creature"));
    }

    private Decomposition(final Decomposition card) {
        super(card);
    }

    @Override
    public Decomposition copy() {
        return new Decomposition(this);
    }
}
