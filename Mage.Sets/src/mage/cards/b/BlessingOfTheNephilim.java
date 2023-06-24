
package mage.cards.b;

import java.util.UUID;
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
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Quercitron
 */
public final class BlessingOfTheNephilim extends CardImpl {

    public BlessingOfTheNephilim(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{W}");
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // Enchanted creature gets +1/+1 for each of its colors.
        DynamicValue numberOfColors = new EnchantedCreatureColorsCount();
        Effect effect = new BoostEnchantedEffect(numberOfColors, numberOfColors, Duration.WhileOnBattlefield);
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect));
    }

    private BlessingOfTheNephilim(final BlessingOfTheNephilim card) {
        super(card);
    }

    @Override
    public BlessingOfTheNephilim copy() {
        return new BlessingOfTheNephilim(this);
    }
}

class EnchantedCreatureColorsCount implements DynamicValue {

    public EnchantedCreatureColorsCount() {
    }

    public EnchantedCreatureColorsCount(final EnchantedCreatureColorsCount dynamicValue) {
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        int count = 0;
        Permanent aura = game.getPermanent(sourceAbility.getSourceId());
        if (aura != null) {
            Permanent permanent = game.getPermanent(aura.getAttachedTo());
            if (permanent != null) {
                count = permanent.getColor(game).getColorCount();
            }
        }
        return count;
    }

    @Override
    public EnchantedCreatureColorsCount copy() {
        return new EnchantedCreatureColorsCount(this);
    }

    @Override
    public String toString() {
        return "1";
    }

    @Override
    public String getMessage() {
        return "of its colors";
    }

}
