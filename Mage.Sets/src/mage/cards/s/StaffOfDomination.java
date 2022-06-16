
package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.effects.common.UntapSourceEffect;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Plopman
 */
public final class StaffOfDomination extends CardImpl {

    public StaffOfDomination(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{3}");

        // {1}: Untap Staff of Domination.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new UntapSourceEffect(), new ManaCostsImpl<>("{1}")));
        // {2}, {tap}: You gain 1 life.
        Ability ability2 = new SimpleActivatedAbility(Zone.BATTLEFIELD, new GainLifeEffect(1), new ManaCostsImpl<>("{2}"));
        ability2.addCost(new TapSourceCost());
        this.addAbility(ability2);
        // {3}, {tap}: Untap target creature.
        Ability ability3 = new SimpleActivatedAbility(Zone.BATTLEFIELD, new UntapTargetEffect(), new ManaCostsImpl<>("{3}"));
        ability3.addCost(new TapSourceCost());
        ability3.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability3);
        // {4}, {tap}: Tap target creature.
        Ability ability4 = new SimpleActivatedAbility(Zone.BATTLEFIELD, new TapTargetEffect(), new ManaCostsImpl<>("{4}"));
        ability4.addCost(new TapSourceCost());
        ability4.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability4);
        // {5}, {tap}: Draw a card.
        Ability ability5 = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(1), new ManaCostsImpl<>("{5}"));
        ability5.addCost(new TapSourceCost());
        this.addAbility(ability5);
    }

    private StaffOfDomination(final StaffOfDomination card) {
        super(card);
    }

    @Override
    public StaffOfDomination copy() {
        return new StaffOfDomination(this);
    }
}
