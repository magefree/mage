package mage.cards.i;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.condition.common.ControlYourCommanderCondition;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.MyriadAbility;
import mage.abilities.triggers.BeginningOfCombatTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class IronwillForger extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent("nonlegendary creature you control");

    static {
        filter.add(Predicates.not(SuperType.LEGENDARY.getPredicate()));
    }

    public IronwillForger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");

        this.subtype.add(SubType.ORC);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Lieutenant -- At the beginning of combat on your turn, if you control your commander, target nonlegendary creature you control gains myriad until end of turn.
        Ability ability = new BeginningOfCombatTriggeredAbility(
                new GainAbilityTargetEffect(new MyriadAbility(false))
        ).withInterveningIf(ControlYourCommanderCondition.instance);
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability.setAbilityWord(AbilityWord.LIEUTENANT));
    }

    private IronwillForger(final IronwillForger card) {
        super(card);
    }

    @Override
    public IronwillForger copy() {
        return new IronwillForger(this);
    }
}
