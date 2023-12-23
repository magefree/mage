/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mage.cards.t;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SuperType;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AnotherPredicate;

/**
 *
 * @author jeffwadsworth
 */
public class TarriansJournal extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("another artifact or creature");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(Predicates.or(
                CardType.ARTIFACT.getPredicate(),
                CardType.CREATURE.getPredicate()
        ));
    }

    public TarriansJournal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}{B}");
        this.supertype.add(SuperType.LEGENDARY);

        this.secondSideCardClazz = mage.cards.t.TheTombOfAclazotz.class;
        this.color.setBlack(true);

        // T: Sacrifice another artifact or creature: Draw a card.  Activate only as a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(new DrawCardSourceControllerEffect(1), new TapSourceCost());
        ability.addCost(new SacrificeTargetCost(filter));
        this.addAbility(ability);
        
        this.addAbility(new TransformAbility());
        Ability transformAbility = new SimpleActivatedAbility(new TransformSourceEffect(), new ManaCostsImpl("{2}"));
        transformAbility.addCost(new TapSourceCost());
        this.addAbility(transformAbility);

    }

    private TarriansJournal(final TarriansJournal card) {
        super(card);
    }

    @Override
    public TarriansJournal copy() {
        return new TarriansJournal(this);
    }

}
