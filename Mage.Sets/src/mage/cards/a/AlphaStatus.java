
package mage.cards.a;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author Eirkei
 */
public final class AlphaStatus extends CardImpl {

    public AlphaStatus(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{G}");
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // Enchanted creature gets +2/+2 for each other creature on the battlefield that shares a creature type with it.
        DynamicValue dynamicValue = new AlphaStatusDynamicValue();
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEnchantedEffect(dynamicValue, dynamicValue, Duration.WhileOnBattlefield)));
    }

    private AlphaStatus(final AlphaStatus card) {
        super(card);
    }

    @Override
    public AlphaStatus copy() {
        return new AlphaStatus(this);
    }
}

class AlphaStatusDynamicValue implements DynamicValue {

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        Permanent enchantment = game.getPermanent(sourceAbility.getSourceId());
        int xValue = 0;
        if (enchantment != null && enchantment.getAttachedTo() != null) {
            Permanent enchanted = game.getPermanent(enchantment.getAttachedTo());
            if (enchanted != null) {
                for (Permanent permanent : game.getBattlefield().getAllActivePermanents(StaticFilters.FILTER_PERMANENT_CREATURE, game)) {
                    if (!permanent.getId().equals(enchanted.getId())) {
                        if (enchanted.shareCreatureTypes(game, permanent)) {
                            xValue += 2;
                        }
                    }
                }
            }
        }
        return xValue;
    }

    @Override
    public AlphaStatusDynamicValue copy() {
        return new AlphaStatusDynamicValue();
    }

    @Override
    public String toString() {
        return "2";
    }

    @Override
    public String getMessage() {
        return "other creature on the battlefield that shares a creature type with it";
    }
}
