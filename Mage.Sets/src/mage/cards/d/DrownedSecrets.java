package mage.cards.d;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.MillCardsTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterSpell;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.target.TargetPlayer;

/**
 *
 * @author TheElk801
 */
public final class DrownedSecrets extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("a blue spell");

    static {
        filter.add(new ColorPredicate(ObjectColor.BLUE));
    }

    public DrownedSecrets(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{U}");

        // Whenever you cast a blue spell, target player puts the top two cards if their library into their graveyard.
        Ability ability = new SpellCastControllerTriggeredAbility(
                new MillCardsTargetEffect(2), filter, false
        );
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    private DrownedSecrets(final DrownedSecrets card) {
        super(card);
    }

    @Override
    public DrownedSecrets copy() {
        return new DrownedSecrets(this);
    }
}
