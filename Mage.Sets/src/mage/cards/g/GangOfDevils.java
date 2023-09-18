package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.DamageMultiEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetAnyTargetAmount;

/**
 *
 * @author noxx

 */
public final class GangOfDevils extends CardImpl {

    public GangOfDevils(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{R}");
        this.subtype.add(SubType.DEVIL);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When Gang of Devils dies, it deals 3 damage divided as you choose among one, two, or three targets.
        Ability ability = new DiesSourceTriggeredAbility(new DamageMultiEffect(3, "it"));
        ability.addTarget(new TargetAnyTargetAmount(3));
        this.addAbility(ability);
    }

    private GangOfDevils(final GangOfDevils card) {
        super(card);
    }

    @Override
    public GangOfDevils copy() {
        return new GangOfDevils(this);
    }
}
