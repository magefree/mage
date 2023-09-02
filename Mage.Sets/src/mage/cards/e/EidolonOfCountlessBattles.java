
package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.BestowAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;

/**
 *
 * @author LevelX2
 */
public final class EidolonOfCountlessBattles extends CardImpl {
    
    private static final FilterControlledPermanent filter = new FilterControlledPermanent();
    static {
        filter.add(Predicates.or(
                CardType.CREATURE.getPredicate(),
                SubType.AURA.getPredicate()));
    }
    
    public EidolonOfCountlessBattles(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT,CardType.CREATURE},"{1}{W}{W}");
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Bestow {2}{W}{W}
        this.addAbility(new BestowAbility(this, "{2}{W}{W}"));
        // Eidolon of Countless Battles and enchanted creature get +1/+1 for each creature you control and +1/+1 for each Aura you control.        
        PermanentsOnBattlefieldCount amount = new PermanentsOnBattlefieldCount(filter, 1);
        Effect effect = new BoostSourceEffect(amount, amount, Duration.WhileOnBattlefield);
        effect.setText("{this} and enchanted creature each get +1/+1 for each creature you control");
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, effect);
        effect = new BoostEnchantedEffect(amount, amount, Duration.WhileOnBattlefield);
        effect.setText("and +1/+1 for each Aura you control");
        ability.addEffect(effect);
        this.addAbility(ability); 
        
    }

    private EidolonOfCountlessBattles(final EidolonOfCountlessBattles card) {
        super(card);
    }

    @Override
    public EidolonOfCountlessBattles copy() {
        return new EidolonOfCountlessBattles(this);
    }
}
