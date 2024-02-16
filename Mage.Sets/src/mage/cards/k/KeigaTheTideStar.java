

package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.SuperType;
import mage.target.common.TargetCreaturePermanent;

/**
 * @author Loki
 */
public final class KeigaTheTideStar extends CardImpl {

    public KeigaTheTideStar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{U}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.DRAGON);
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Keiga, the Tide Star dies, gain control of target creature.
        Ability ability = new DiesSourceTriggeredAbility(new GainControlTargetEffect(Duration.Custom));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private KeigaTheTideStar(final KeigaTheTideStar card) {
        super(card);
    }

    @Override
    public KeigaTheTideStar copy() {
        return new KeigaTheTideStar(this);
    }

}
