package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.common.cost.SpellsCostReductionControllerEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;

/**
 *
 * @author TheElk801
 */
public final class GoreclawTerrorOfQalSisma extends CardImpl {

    private static final FilterCard filter = new FilterCreatureCard();
    private static final FilterCreaturePermanent filter2 = new FilterCreaturePermanent();

    static {
        filter.add(new PowerPredicate(ComparisonType.MORE_THAN, 3));
        filter2.add(new PowerPredicate(ComparisonType.MORE_THAN, 3));
    }

    public GoreclawTerrorOfQalSisma(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.BEAR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Creature spells you cast with power 4 or greater cost {2} less to cast.
        this.addAbility(new SimpleStaticAbility(
                Zone.BATTLEFIELD,
                new SpellsCostReductionControllerEffect(filter, 2)
                        .setText("Creature spells you cast with power 4 or greater cost {2} less to cast")
        ));

        // Whenever Goreclaw, Terror of Qal Sisma attacks, each creature you control with power 4 or greater gets +1/+1 and gains trample until end of turn.
        Ability ability = new AttacksTriggeredAbility(
                new BoostControlledEffect(
                        1, 1, Duration.EndOfTurn, filter2
                ).setText("each creature you control with power 4 or greater gets +1/+1"), false
        );
        ability.addEffect(new GainAbilityControlledEffect(
                TrampleAbility.getInstance(),
                Duration.EndOfTurn, filter2
        ).setText("and gains trample until end of turn"));
        this.addAbility(ability);
    }

    private GoreclawTerrorOfQalSisma(final GoreclawTerrorOfQalSisma card) {
        super(card);
    }

    @Override
    public GoreclawTerrorOfQalSisma copy() {
        return new GoreclawTerrorOfQalSisma(this);
    }
}
