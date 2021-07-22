
package mage.cards.b;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BecomesBlockedAllTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.BlockedCreatureCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
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
 * @author Markedagain
 */
public final class BeastmastersMagemark extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Creatures you control that are enchanted");

    static {
        filter.add(EnchantedPredicate.instance);
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    public BeastmastersMagemark(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{G}");
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.AddAbility));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);
        // Creatures you control that are enchanted get +1/+1.
        ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostAllEffect(1, 1, Duration.WhileOnBattlefield, filter, false));
        this.addAbility(ability);
        // Whenever a creature you control that's enchanted becomes blocked, it gets +1/+1 until end of turn for each creature blocking it.
        BlockedCreatureCount value = BlockedCreatureCount.ALL;
        Effect effect = new BoostSourceEffect(value, value, Duration.EndOfTurn, true);
        effect.setText("it gets +1/+1 until end of turn for each creature blocking it");
        this.addAbility(new BecomesBlockedAllTriggeredAbility(effect, false, filter, false));
    }

    private BeastmastersMagemark(final BeastmastersMagemark card) {
        super(card);
    }

    @Override
    public BeastmastersMagemark copy() {
        return new BeastmastersMagemark(this);
    }
}
