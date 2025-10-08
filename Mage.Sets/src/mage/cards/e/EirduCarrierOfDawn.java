package mage.cards.e;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledSpellsEffect;
import mage.abilities.keyword.ConvokeAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.keyword.TransformAbility;
import mage.abilities.triggers.BeginningOfFirstMainTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.common.FilterNonlandCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AbilityPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EirduCarrierOfDawn extends CardImpl {

    private static final FilterNonlandCard filter = new FilterNonlandCard("creature spells you cast");

    static {
        filter.add(CardType.CREATURE.getPredicate());
        filter.add(Predicates.not(new AbilityPredicate(ConvokeAbility.class)));
    }

    public EirduCarrierOfDawn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.GOD);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);
        this.secondSideCardClazz = mage.cards.i.IsiluCarrierOfTwilight.class;

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // Creature spells you cast have convoke.
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledSpellsEffect(new ConvokeAbility(), filter)));

        // At the beginning of your first main phase, you may pay {B}. If you do, transform Eirdu.
        this.addAbility(new TransformAbility());
        this.addAbility(new BeginningOfFirstMainTriggeredAbility(
                new DoIfCostPaid(new TransformSourceEffect(), new ManaCostsImpl<>("{B}"))
        ));
    }

    private EirduCarrierOfDawn(final EirduCarrierOfDawn card) {
        super(card);
    }

    @Override
    public EirduCarrierOfDawn copy() {
        return new EirduCarrierOfDawn(this);
    }
}
