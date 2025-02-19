package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.effects.common.cost.AbilitiesCostReductionAttachedEffect;
import mage.abilities.effects.common.cost.SpellsCostReductionAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.*;

public class StrongBack extends CardImpl {

    private static final FilterCard filter = new FilterCard("Aura spells");

    static {
        filter.add(SubType.AURA.getPredicate());
    }

    public StrongBack(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{G}");

        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        this.addAbility(new EnchantAbility(auraTarget));

        // Equip abilities you activate that target enchanted creature cost {3} less to activate.
        this.addAbility(new SimpleStaticAbility(
                new AbilitiesCostReductionAttachedEffect(EquipAbility.class, "Equip", 3)
                        .setText("equip abilities you activate that target enchanted creature cost {3} less to cast")));

        // Aura spells you cast that target enchanted creature cost {3} less to cast.
        this.addAbility(new SimpleStaticAbility(new SpellsCostReductionAttachedEffect(filter, 3)));

        // Enchanted creature gets +2/+2 for each Aura and Equipment attached to it.
        this.addAbility(new SimpleStaticAbility(new BoostEnchantedEffect(StrongBackBoostValue.instance, StrongBackBoostValue.instance)));
    }

    public StrongBack(StrongBack card) {
        super(card);
    }

    @Override
    public StrongBack copy() {
        return new StrongBack(this);
    }

    enum StrongBackBoostValue implements DynamicValue {
        instance;
        @Override
        public int calculate(Game game, Ability sourceAbility, Effect effect) {
            return Optional.ofNullable(sourceAbility.getSourcePermanentOrLKI(game))
                    .map(sourcePermanent -> game.getPermanent(sourcePermanent.getAttachedTo()))
                    .map(permanent -> permanent.getAttachments().stream()
                            .map(game::getPermanent)
                            .filter(Objects::nonNull)
                            .filter(attachment -> attachment.hasSubtype(SubType.AURA, game)
                                    || attachment.hasSubtype(SubType.EQUIPMENT, game))
                            .mapToInt(attachment -> 1)
                            .sum() * 2)
                    .orElse(0);
        }
        @Override
        public DynamicValue copy() {
            return this;
        }
        @Override
        public String getMessage() {
            return "Aura and Equipment attached to it";
        }
    }
}
