package mage.cards.b;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BecomesBlockedAllTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.BlockingCreatureCount;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.EnchantedPredicate;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author awjackson
 */
public final class BeastmastersMagemark extends CardImpl {

    private static final FilterCreaturePermanent filterCreatures = new FilterCreaturePermanent("creatures you control that are enchanted");
    private static final FilterCreaturePermanent filterACreature = new FilterCreaturePermanent("a creature you control that's enchanted");

    static {
        filterCreatures.add(EnchantedPredicate.instance);
        filterACreature.add(EnchantedPredicate.instance);
        filterCreatures.add(TargetController.YOU.getControllerPredicate());
        filterACreature.add(TargetController.YOU.getControllerPredicate());
    }

    public BeastmastersMagemark(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{G}");
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.AddAbility));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // Creatures you control that are enchanted get +1/+1.
        ability = new SimpleStaticAbility(new BoostAllEffect(1, 1, Duration.WhileOnBattlefield, filterCreatures, false));
        this.addAbility(ability);

        // Whenever a creature you control that's enchanted becomes blocked, it gets +1/+1 until end of turn for each creature blocking it.
        this.addAbility(new BecomesBlockedAllTriggeredAbility(
                new BoostTargetEffect(BlockingCreatureCount.TARGET, BlockingCreatureCount.TARGET, Duration.EndOfTurn),
                false, filterACreature, true
        ));
    }

    private BeastmastersMagemark(final BeastmastersMagemark card) {
        super(card);
    }

    @Override
    public BeastmastersMagemark copy() {
        return new BeastmastersMagemark(this);
    }
}
