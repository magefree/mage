
package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.SourceAttackingCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.constants.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;

/**
 *
 * @author JayDi85
 */
public final class KitesailCorsair extends CardImpl {

    public KitesailCorsair(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.PIRATE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Kitesail Corsair has flying as long as it's attacking.
        ContinuousEffect gainEffect = new GainAbilitySourceEffect(FlyingAbility.getInstance(), Duration.WhileOnBattlefield);
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD,  new ConditionalContinuousEffect(
                gainEffect,
                SourceAttackingCondition.instance,
                "{this} has flying as long as it's attacking."
        )));
    }

    private KitesailCorsair(final KitesailCorsair card) {
        super(card);
    }

    @Override
    public KitesailCorsair copy() {
        return new KitesailCorsair(this);
    }
}