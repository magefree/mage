package mage.cards.i;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterSpell;
import mage.filter.StaticFilters;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.permanent.token.RobotHeroToken;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author muz
 */
public final class IronManTonyStark extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("a red spell");

    static {
        filter.add(new ColorPredicate(ObjectColor.RED));
    }

    public IronManTonyStark(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{3}{R}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Attacking creatures you control get +1/+0.
        this.addAbility(new SimpleStaticAbility(new BoostControlledEffect(
            1, 0, Duration.WhileOnBattlefield,
            StaticFilters.FILTER_ATTACKING_CREATURES
        )));

        // Whenever you cast a red spell, create a 2/1 colorless Robot Hero artifact creature token with flying.
        this.addAbility(new SpellCastControllerTriggeredAbility(new CreateTokenEffect(new RobotHeroToken()), filter, false));
    }

    private IronManTonyStark(final IronManTonyStark card) {
        super(card);
    }

    @Override
    public IronManTonyStark copy() {
        return new IronManTonyStark(this);
    }
}
