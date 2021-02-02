package mage.cards.v;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterControlledPermanent;
import mage.target.common.TargetOpponentsCreaturePermanent;

/**
 *
 * @author TheElk801
 */
public final class VolleyVeteran extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("Goblins you control");

    static {
        filter.add(SubType.GOBLIN.getPredicate());
    }

    public VolleyVeteran(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");

        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(2);

        // When Volley Veteran enters the battlefield, it deals damage to target creature an opponent controls equal to the number of Goblins you control.
        Ability ability = new EntersBattlefieldTriggeredAbility(
                new DamageTargetEffect(new PermanentsOnBattlefieldCount(filter))
                        .setText("it deals damage to target creature an opponent controls equal to the number of Goblins you control")
        );
        ability.addTarget(new TargetOpponentsCreaturePermanent());
        this.addAbility(ability);
    }

    private VolleyVeteran(final VolleyVeteran card) {
        super(card);
    }

    @Override
    public VolleyVeteran copy() {
        return new VolleyVeteran(this);
    }
}
