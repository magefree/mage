
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetPlayerOrPlaneswalker;

/**
 *
 * @author Backfir3
 */
public final class GoretuskFirebeast extends CardImpl {

    public GoretuskFirebeast(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{R}");
        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.BOAR);
        this.subtype.add(SubType.BEAST);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Goretusk Firebeast enters the battlefield, it deals 4 damage to target player.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DamageTargetEffect(4, "it"), false);
        ability.addTarget(new TargetPlayerOrPlaneswalker());
        this.addAbility(ability);
    }

    private GoretuskFirebeast(final GoretuskFirebeast card) {
        super(card);
    }

    @Override
    public GoretuskFirebeast copy() {
        return new GoretuskFirebeast(this);
    }
}
