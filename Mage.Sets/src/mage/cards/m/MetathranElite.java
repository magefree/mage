package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.EnchantedSourceCondition;
import mage.abilities.decorator.ConditionalRestrictionEffect;
import mage.abilities.effects.common.combat.CantBeBlockedSourceEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;

/**
 *
 * @author jeffwadsworth
 */
public final class MetathranElite extends CardImpl {

    private static final String rule = "{this} can't be blocked as long as it's enchanted.";

    public MetathranElite(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}{U}");

        this.subtype.add(SubType.METATHRAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Metathran Elite is unblockable as long as it's enchanted.
        ConditionalRestrictionEffect effect = new ConditionalRestrictionEffect(
                new CantBeBlockedSourceEffect(), new EnchantedSourceCondition());
        effect.setText(rule);
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect));

    }

    private MetathranElite(final MetathranElite card) {
        super(card);
    }

    @Override
    public MetathranElite copy() {
        return new MetathranElite(this);
    }
}
