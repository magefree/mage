
package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CantBeRegeneratedTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class FurnaceBrood extends CardImpl {

    public FurnaceBrood(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{R}");
        this.subtype.add(SubType.ELEMENTAL);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // {R}: Target creature can't be regenerated this turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CantBeRegeneratedTargetEffect(Duration.EndOfTurn), new ManaCostsImpl<>("{R}"));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private FurnaceBrood(final FurnaceBrood card) {
        super(card);
    }

    @Override
    public FurnaceBrood copy() {
        return new FurnaceBrood(this);
    }
}
