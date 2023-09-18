package mage.cards.v;

import mage.MageInt;
import mage.abilities.common.DealtDamageToSourceTriggeredAbility;
import mage.abilities.common.OneOrMoreDiceRolledTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DamageSelfEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.game.permanent.token.VrondissRageOfAncientsToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class VrondissRageOfAncients extends CardImpl {

    public VrondissRageOfAncients(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.DRAGON);
        this.subtype.add(SubType.BARBARIAN);
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // Enrage — Whenever Vrondiss, Rage of Ancients is dealt damage, you may create a 5/4 red and green Dragon Spirit creature token with "When this creature deals damage, sacrifice it."
        this.addAbility(new DealtDamageToSourceTriggeredAbility(
                new CreateTokenEffect(new VrondissRageOfAncientsToken()), true, true
        ));

        // Whenever you roll one or more dice, you may have Vrondiss, Rage of Ancients deal 1 damage to itself.
        this.addAbility(new OneOrMoreDiceRolledTriggeredAbility(
                new DamageSelfEffect(1).setText("have {this} deal 1 damage to itself"), true
        ));
    }

    private VrondissRageOfAncients(final VrondissRageOfAncients card) {
        super(card);
    }

    @Override
    public VrondissRageOfAncients copy() {
        return new VrondissRageOfAncients(this);
    }
}
