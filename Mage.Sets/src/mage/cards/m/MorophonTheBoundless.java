package mage.cards.m;

import mage.MageInt;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ChooseCreatureTypeEffect;
import mage.abilities.effects.common.continuous.BoostAllOfChosenSubtypeEffect;
import mage.abilities.effects.common.cost.SpellsCostReductionControllerEffect;
import mage.abilities.keyword.ChangelingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ChosenSubtypePredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MorophonTheBoundless extends CardImpl {

    private static final FilterCard filter = new FilterCard("Spells of the chosen type");
    private static final FilterCreaturePermanent filter2
            = new FilterCreaturePermanent("creatures you control of the chosen type");

    static {
        filter.add(ChosenSubtypePredicate.TRUE);
        filter2.add(TargetController.YOU.getControllerPredicate());
    }

    public MorophonTheBoundless(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{7}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.SHAPESHIFTER);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Changeling
        this.addAbility(new ChangelingAbility());

        // As Morophon, the Boundless enters the battlefield, choose a creature type.
        this.addAbility(new AsEntersBattlefieldAbility(new ChooseCreatureTypeEffect(Outcome.BoostCreature)));

        // Spells of the chosen type you cast cost {W}{U}{B}{R}{G} less to cast. This effect reduces only the amount of colored mana you pay.
        this.addAbility(new SimpleStaticAbility(new SpellsCostReductionControllerEffect(
                filter, new ManaCostsImpl<>("{W}{U}{B}{R}{G}")
        )));

        // Other creatures you control of the chosen type get +1/+1.
        this.addAbility(new SimpleStaticAbility(new BoostAllOfChosenSubtypeEffect(
                1, 1, Duration.WhileOnBattlefield, filter2, true
        )));
    }

    private MorophonTheBoundless(final MorophonTheBoundless card) {
        super(card);
    }

    @Override
    public MorophonTheBoundless copy() {
        return new MorophonTheBoundless(this);
    }
}
