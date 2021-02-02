
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.effects.common.continuous.AddChosenSubtypeEffect;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.ChooseCreatureTypeEffect;
import mage.abilities.effects.common.continuous.BoostAllOfChosenSubtypeEffect;
import mage.abilities.effects.common.enterAttribute.EnterAttributeAddChosenSubtypeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;

/**
 * @author nantuko
 */
public final class AdaptiveAutomaton extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creatures you control of the chosen type");

    static {
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    public AdaptiveAutomaton(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{3}");
        this.subtype.add(SubType.CONSTRUCT);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // As Adaptive Automaton enters the battlefield, choose a creature type.
        // Adaptive Automaton is the chosen type in addition to its other types.
        AsEntersBattlefieldAbility ability = new AsEntersBattlefieldAbility(new ChooseCreatureTypeEffect(Outcome.BoostCreature));
        ability.addEffect(new EnterAttributeAddChosenSubtypeEffect());
        this.addAbility(ability);

        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new AddChosenSubtypeEffect()));
        // Other creatures you control of the chosen type get +1/+1.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostAllOfChosenSubtypeEffect(1, 1, Duration.WhileOnBattlefield, filter, true)));
    }

    private AdaptiveAutomaton(final AdaptiveAutomaton card) {
        super(card);
    }

    @Override
    public AdaptiveAutomaton copy() {
        return new AdaptiveAutomaton(this);
    }
}

