
package mage.cards.b;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeAttachedCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.DamageAllEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.target.TargetPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author L_J
 */
public final class BloodfireInfusion extends CardImpl {

    public BloodfireInfusion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{R}");
        this.subtype.add(SubType.AURA);

        // Enchant creature you control
        TargetPermanent auraTarget = new TargetControlledCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);

        // {R}, Sacrifice enchanted creature: Bloodfire Infusion deals damage equal to the sacrificed creature's power to each creature.
        Effect effect = new DamageAllEffect(new AttachedPermanentPowerCount(), new FilterCreaturePermanent());
        effect.setText("{this} deals damage equal to the sacrificed creature's power to each creature");
        Ability ability2 = new SimpleActivatedAbility(Zone.BATTLEFIELD, effect, new ManaCostsImpl<>("{R}"));
        ability2.addCost(new SacrificeAttachedCost());
        this.addAbility(ability2);

    }

    private BloodfireInfusion(final BloodfireInfusion card) {
        super(card);
    }

    @Override
    public BloodfireInfusion copy() {
        return new BloodfireInfusion(this);
    }
}

class AttachedPermanentPowerCount implements DynamicValue {

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        Permanent attachment = game.getPermanentOrLKIBattlefield(sourceAbility.getSourceId());
        if (attachment == null) {
            return 0;
        }
        Permanent permanent = game.getPermanentOrLKIBattlefield(attachment.getAttachedTo());
        if (permanent != null && (permanent.getPower().getValue() >= 0)) {
            return permanent.getPower().getValue();
        }
        return 0;
    }

    @Override
    public AttachedPermanentPowerCount copy() {
        return new AttachedPermanentPowerCount();
    }

    @Override
    public String toString() {
        return "X";
    }

    @Override
    public String getMessage() {
        return "enchanted creature's power";
    }
}
