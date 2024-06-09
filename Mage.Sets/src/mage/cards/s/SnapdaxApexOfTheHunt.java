package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.MutatesSourceTriggeredAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.abilities.keyword.MutateAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreatureOrPlaneswalkerPermanent;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SnapdaxApexOfTheHunt extends CardImpl {

    private static final FilterPermanent filter
            = new FilterCreatureOrPlaneswalkerPermanent("creature or planeswalker an opponent controls");

    static {
        filter.add(TargetController.OPPONENT.getControllerPredicate());
    }

    public SnapdaxApexOfTheHunt(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}{W}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.DINOSAUR);
        this.subtype.add(SubType.CAT);
        this.subtype.add(SubType.NIGHTMARE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(5);

        // Mutate {2}{B/R}{W}{W}
        this.addAbility(new MutateAbility(this, "{2}{B/R}{W}{W}"));

        // Double Strike
        this.addAbility(DoubleStrikeAbility.getInstance());

        // Whenever this creature mutates, it deals 4 damage to target creature or planeswalker an opponent controls and you gain 4 life.
        Ability ability = new MutatesSourceTriggeredAbility(
                new DamageTargetEffect(4, "it")
        );
        ability.addEffect(new GainLifeEffect(4).concatBy("and"));
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private SnapdaxApexOfTheHunt(final SnapdaxApexOfTheHunt card) {
        super(card);
    }

    @Override
    public SnapdaxApexOfTheHunt copy() {
        return new SnapdaxApexOfTheHunt(this);
    }
}
