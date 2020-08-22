
package mage.cards.n;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.ReturnToHandChosenControlledPermanentEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureAllEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.common.FilterControlledEnchantmentPermanent;
import mage.filter.common.FilterControlledLandPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.permanent.token.custom.CreatureToken;

/**
 *
 * @author LoneFox
 *
 */
public final class NaturalEmergence extends CardImpl {

    static final private FilterControlledEnchantmentPermanent filter = new FilterControlledEnchantmentPermanent("red or green creature you control");

    static {
        filter.add(Predicates.or(new ColorPredicate(ObjectColor.RED), new ColorPredicate(ObjectColor.GREEN)));
    }

    public NaturalEmergence(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{R}{G}");

        // When Natural Emergence enters the battlefield, return a red or green enchantment you control to its owner's hand.
        Effect effect = new ReturnToHandChosenControlledPermanentEffect(filter);
        effect.setText("return a red or green enchantment you control to its owner's hand");
        this.addAbility(new EntersBattlefieldTriggeredAbility(effect, false));

        // Lands you control are 2/2 creatures with first strike. They're still lands.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BecomesCreatureAllEffect(
                new CreatureToken(2, 2, "2/2 creatures with first strike").withAbility(FirstStrikeAbility.getInstance()),
                "lands", new FilterControlledLandPermanent("lands you control"), Duration.WhileOnBattlefield, false)));
    }

    public NaturalEmergence(final NaturalEmergence card) {
        super(card);
    }

    @Override
    public NaturalEmergence copy() {
        return new NaturalEmergence(this);
    }
}