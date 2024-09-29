

package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetOpponent;

/**
 *
 * @author Loki
 */
public final class PierceStrider extends CardImpl {

    public PierceStrider (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{4}");
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.CONSTRUCT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);
        Ability ability = new EntersBattlefieldTriggeredAbility(new LoseLifeTargetEffect(3));
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    private PierceStrider(final PierceStrider card) {
        super(card);
    }

    @Override
    public PierceStrider copy() {
        return new PierceStrider(this);
    }

}
