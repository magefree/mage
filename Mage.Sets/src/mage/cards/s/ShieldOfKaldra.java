
package mage.cards.s;

import java.util.UUID;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.NamePredicate;

/**
 *
 * @author North
 */
public final class ShieldOfKaldra extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("Equipment named Sword of Kaldra, Shield of Kaldra, and Helm of Kaldra");

    static {
        filter.add(SubType.EQUIPMENT.getPredicate());
        filter.add(Predicates.or(
                new NamePredicate("Sword of Kaldra"),
                new NamePredicate("Shield of Kaldra"),
                new NamePredicate("Helm of Kaldra")));
    }

    public ShieldOfKaldra(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{4}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.EQUIPMENT);

        // Equipment named Sword of Kaldra, Shield of Kaldra, and Helm of Kaldra have indestructible.
        Effect effect = new GainAbilityAllEffect(IndestructibleAbility.getInstance(), Duration.WhileOnBattlefield, filter, false);
        effect.setText("Equipment named Sword of Kaldra, Shield of Kaldra, and Helm of Kaldra have indestructible");
        this.addAbility(new SimpleStaticAbility(effect));
        // Equipped creature has indestructible.
        effect = new GainAbilityAttachedEffect(IndestructibleAbility.getInstance(), AttachmentType.EQUIPMENT, Duration.WhileOnBattlefield);
        effect.setText("Equipped creature has indestructible");
        this.addAbility(new SimpleStaticAbility(effect));
        // Equip {4}
        this.addAbility(new EquipAbility(Outcome.Benefit, new GenericManaCost(4), false));
    }

    private ShieldOfKaldra(final ShieldOfKaldra card) {
        super(card);
    }

    @Override
    public ShieldOfKaldra copy() {
        return new ShieldOfKaldra(this);
    }
}
