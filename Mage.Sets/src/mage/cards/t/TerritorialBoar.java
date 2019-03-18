package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TerritorialBoar extends CardImpl {

    private static final FilterPermanent filter
            = new FilterCreaturePermanent("a creature with power 4 or greater");

    static {
        filter.add(new PowerPredicate(ComparisonType.MORE_THAN, 3));
    }

    public TerritorialBoar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.BOAR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever a creature with power 4 or greater enters the battlefield under your control, Territorial Boar gets +1/+1 and gains vigilance until end of turn.
        Ability ability = new EntersBattlefieldControlledTriggeredAbility(new BoostSourceEffect(
                1, 1, Duration.EndOfTurn
        ).setText("{this} gets +1/+1"), filter);
        ability.addEffect(new GainAbilitySourceEffect(
                VigilanceAbility.getInstance(), Duration.EndOfTurn
        ).setText("and gains vigilance until end of turn"));
        this.addAbility(ability);
    }

    private TerritorialBoar(final TerritorialBoar card) {
        super(card);
    }

    @Override
    public TerritorialBoar copy() {
        return new TerritorialBoar(this);
    }
}
