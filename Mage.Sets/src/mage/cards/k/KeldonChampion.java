
package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.EchoAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetPlayerOrPlaneswalker;

/**
 *
 * @author Loki
 */
public final class KeldonChampion extends CardImpl {

    public KeldonChampion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{R}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.BARBARIAN);

        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        this.addAbility(HasteAbility.getInstance());
        // Echo {2}{R}{R}
        this.addAbility(new EchoAbility("{2}{R}{R}"));
        // When Keldon Champion enters the battlefield, it deals 3 damage to target player.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DamageTargetEffect(3, "it"), false);
        ability.addTarget(new TargetPlayerOrPlaneswalker());
        this.addAbility(ability);
    }

    private KeldonChampion(final KeldonChampion card) {
        super(card);
    }

    @Override
    public KeldonChampion copy() {
        return new KeldonChampion(this);
    }
}
