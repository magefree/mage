package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealtDamageToSourceTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DestroySourceEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author NinthWorld
 */
public final class Corruptor extends CardImpl {

    public Corruptor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");
        
        this.subtype.add(SubType.ZERG);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // {2}{B}, Discard Corruptor: When target creature is dealt damage this turn, destroy that creature.
        Ability ability1 = new DealtDamageToSourceTriggeredAbility(Zone.BATTLEFIELD, new DestroySourceEffect(), false);
        Ability ability = new SimpleActivatedAbility(new GainAbilityTargetEffect(ability1, Duration.EndOfTurn), new ManaCostsImpl("{2}{B}"));
        ability.addCost(new DiscardSourceCost());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    public Corruptor(final Corruptor card) {
        super(card);
    }

    @Override
    public Corruptor copy() {
        return new Corruptor(this);
    }
}
