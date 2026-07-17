package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.filter.FilterStackObject;
import mage.filter.predicate.other.CreatureSourcePredicate;
import mage.target.common.TargetActivatedOrTriggeredAbility;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CopyTargetStackObjectEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class EchoPerceptiveProdigy extends CardImpl {

    private static final FilterStackObject filter = new FilterStackObject("activated or triggered ability you control from a creature source");

    static {
        filter.add(CreatureSourcePredicate.instance);
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    public EchoPerceptiveProdigy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // {1}, {T}: Copy target activated or triggered ability you control from a creature source. You may choose new targets for the copy.
        Ability ability = new SimpleActivatedAbility(new CopyTargetStackObjectEffect(), new ManaCostsImpl<>("{1}"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetActivatedOrTriggeredAbility(filter));
        this.addAbility(ability);
    }

    private EchoPerceptiveProdigy(final EchoPerceptiveProdigy card) {
        super(card);
    }

    @Override
    public EchoPerceptiveProdigy copy() {
        return new EchoPerceptiveProdigy(this);
    }
}
