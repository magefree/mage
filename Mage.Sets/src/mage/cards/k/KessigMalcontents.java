
package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPermanent;
import mage.target.common.TargetPlayerOrPlaneswalker;

/**
 * @author noxx
 */
public final class KessigMalcontents extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("Humans you control");

    static {
        filter.add(SubType.HUMAN.getPredicate());
    }

    public KessigMalcontents(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // When Kessig Malcontents enters the battlefield, it deals damage to target player equal to the number of Humans you control.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DamageTargetEffect(new PermanentsOnBattlefieldCount(filter), "it"));
        ability.addTarget(new TargetPlayerOrPlaneswalker());
        this.addAbility(ability);
    }

    private KessigMalcontents(final KessigMalcontents card) {
        super(card);
    }

    @Override
    public KessigMalcontents copy() {
        return new KessigMalcontents(this);
    }
}
