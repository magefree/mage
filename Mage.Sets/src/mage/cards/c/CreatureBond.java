package mage.cards.c;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.DiesAttachedTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.DamageAttachedControllerEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author MTGfan
 */
public final class CreatureBond extends CardImpl {

    public CreatureBond(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{U}");

        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Detriment));
        this.addAbility(new EnchantAbility(auraTarget.getTargetName()));

        // When enchanted creature dies, Creature Bond deals damage equal to that creature's toughness to the creature's controller.
        Effect effect = new DamageAttachedControllerEffect(CreatureBondValue.instance);
        effect.setText("{this} deals damage equal to that creature's toughness to the creature's controller");
        this.addAbility(new DiesAttachedTriggeredAbility(effect, "enchanted creature"));
    }

    private CreatureBond(final CreatureBond card) {
        super(card);
    }

    @Override
    public CreatureBond copy() {
        return new CreatureBond(this);
    }
}

enum CreatureBondValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        // In the case that the enchantment is blinked
        Permanent enchantment = (Permanent) game.getLastKnownInformation(sourceAbility.getSourceId(), Zone.BATTLEFIELD);
        if (enchantment == null) {
            // It was not blinked, use the standard method
            enchantment = game.getPermanentOrLKIBattlefield(sourceAbility.getSourceId());
        }
        if (enchantment == null) {
            return 0;
        }
        Permanent enchanted = game.getPermanentOrLKIBattlefield(enchantment.getAttachedTo());
        return enchanted.getToughness().getValue();
    }

    @Override
    public CreatureBondValue copy() {
        return instance;
    }

    @Override
    public String getMessage() {
        return "that creature's toughness";
    }
}
