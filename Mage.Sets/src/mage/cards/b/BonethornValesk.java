
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.TurnedFaceUpAllTriggeredAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author TheElk801
 */
public final class BonethornValesk extends CardImpl {

    public BonethornValesk(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}");

        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(4);
        this.toughness = new MageInt(2);

        // Whenever a permanent is turned face up, Bonethorn Valesk deals 1 damage to any target.
        Ability ability = new TurnedFaceUpAllTriggeredAbility(new DamageTargetEffect(1), new FilterPermanent("a permanent"));
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    private BonethornValesk(final BonethornValesk card) {
        super(card);
    }

    @Override
    public BonethornValesk copy() {
        return new BonethornValesk(this);
    }
}
