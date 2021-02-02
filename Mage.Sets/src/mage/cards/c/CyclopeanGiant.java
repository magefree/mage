package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.ExileSourceEffect;
import mage.abilities.effects.common.continuous.BecomesBasicLandTargetEffect;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetLandPermanent;

/**
 *
 * @author noahg
 */
public final class CyclopeanGiant extends CardImpl {

    public CyclopeanGiant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{B}");
        
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.GIANT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(2);

        // When Cyclopean Giant dies, target land becomes a Swamp. Exile Cyclopean Giant.
        DiesSourceTriggeredAbility ability = new DiesSourceTriggeredAbility(new BecomesBasicLandTargetEffect(Duration.EndOfGame, SubType.SWAMP));
        ability.addEffect(new ExileSourceEffect());
        ability.addTarget(new TargetLandPermanent());
        this.addAbility(ability);
    }

    private CyclopeanGiant(final CyclopeanGiant card) {
        super(card);
    }

    @Override
    public CyclopeanGiant copy() {
        return new CyclopeanGiant(this);
    }
}
