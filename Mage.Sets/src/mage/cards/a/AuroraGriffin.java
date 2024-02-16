
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BecomesColorTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.target.TargetPermanent;

/**
 *
 * @author LoneFox
 */
public final class AuroraGriffin extends CardImpl {

    public AuroraGriffin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{W}");
        this.subtype.add(SubType.GRIFFIN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // {W}: Target permanent becomes white until end of turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new BecomesColorTargetEffect(ObjectColor.WHITE,
            Duration.EndOfTurn), new ManaCostsImpl<>("{W}"));
        ability.addTarget(new TargetPermanent());
        this.addAbility(ability);
    }

    private AuroraGriffin(final AuroraGriffin card) {
        super(card);
    }

    @Override
    public AuroraGriffin copy() {
        return new AuroraGriffin(this);
    }
}
