package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.CycleTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.ExertSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.CyclingAbility;
import mage.abilities.keyword.HexproofAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.game.permanent.token.CatToken2;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BasriTomorrowsChampion extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent(SubType.CAT, "");

    public BasriTomorrowsChampion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // {W}, {T}, Exert Basri: Create a 1/1 white Cat creature token with lifelink.
        Ability ability = new SimpleActivatedAbility(new CreateTokenEffect(new CatToken2()), new ManaCostsImpl<>("{W}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new ExertSourceCost());
        this.addAbility(ability);

        // Cycling {2}{W}
        this.addAbility(new CyclingAbility(new ManaCostsImpl<>("{2}{W}")));

        // When you cycle this card, Cats you control gain hexproof and indestructible until end of turn.
        ability = new CycleTriggeredAbility(new GainAbilityControlledEffect(
                HexproofAbility.getInstance(), Duration.EndOfTurn, filter
        ).setText("Cats you control gain hexproof"));
        ability.addEffect(new GainAbilityControlledEffect(
                IndestructibleAbility.getInstance(), Duration.EndOfTurn, filter
        ).setText("and indestructible until end of turn"));
        this.addAbility(ability);
    }

    private BasriTomorrowsChampion(final BasriTomorrowsChampion card) {
        super(card);
    }

    @Override
    public BasriTomorrowsChampion copy() {
        return new BasriTomorrowsChampion(this);
    }
}
