
package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.common.continuous.BecomesBasicLandTargetEffect;
import mage.abilities.keyword.PlainswalkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.target.Target;
import mage.target.common.TargetLandPermanent;

import java.util.UUID;

/**
 *
 * @author cbt33, Loki (Contaminated Ground), Plopman (Larceny)
 */
public final class GracefulAntelope extends CardImpl {

    public GracefulAntelope(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{W}{W}");
        this.subtype.add(SubType.ANTELOPE);

        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // Plainswalk
        this.addAbility(new PlainswalkAbility());
        // Whenever Graceful Antelope deals combat damage to a player, you may have target land become a Plains until Graceful Antelope leaves the battlefield.
        Ability ability = new DealsCombatDamageToAPlayerTriggeredAbility(new BecomesBasicLandTargetEffect(Duration.WhileOnBattlefield, SubType.PLAINS), true);
        Target target = new TargetLandPermanent();
        ability.addTarget(target);
        this.addAbility(ability);
    }

    public GracefulAntelope(final GracefulAntelope card) {
        super(card);
    }

    @Override
    public GracefulAntelope copy() {
        return new GracefulAntelope(this);
    }
}
