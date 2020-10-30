package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.GainLifeFirstTimeTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.game.permanent.token.CatToken3;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AttendedHealer extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent(SubType.CLERIC, "another target Cleric");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public AttendedHealer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");

        this.subtype.add(SubType.KOR);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Whenever you gain life for the first time each turn, create a 1/1 white Cat creature token.
        this.addAbility(new GainLifeFirstTimeTriggeredAbility(new CreateTokenEffect(new CatToken3())));

        // {2}{W}: Another target Cleric gains lifelink until end of turn.
        Ability ability = new SimpleActivatedAbility(new GainAbilityTargetEffect(
                LifelinkAbility.getInstance(), Duration.EndOfTurn,
                "another target Cleric gains lifelink until end of turn"
        ), new ManaCostsImpl<>("{2}{W}"));
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private AttendedHealer(final AttendedHealer card) {
        super(card);
    }

    @Override
    public AttendedHealer copy() {
        return new AttendedHealer(this);
    }
}
