package mage.cards.a;

import mage.MageInt;
import mage.Mana;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.dynamicvalue.common.GreatestAmongPermanentsValue;
import mage.abilities.hint.Hint;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.PartnerAbility;
import mage.abilities.mana.DynamicManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.EnteredThisTurnPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AlenaKessigTrapper extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent("creatures you control that entered this turn");

    static {
        filter.add(EnteredThisTurnPredicate.instance);
    }

    private static final GreatestAmongPermanentsValue xValue = new GreatestAmongPermanentsValue(GreatestAmongPermanentsValue.Quality.Power, filter);
    private static final Hint hint = xValue.getHint();

    public AlenaKessigTrapper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SCOUT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // {T}: Add an amount of {R} equal to the greatest power among creatures you control that entered the battlefield this turn.
        this.addAbility(new DynamicManaAbility(
                Mana.RedMana(1), xValue, new TapSourceCost(),
                "Add an amount of {R} equal to the greatest power among creatures you control that entered this turn."
        ).addHint(hint));

        // Partner
        this.addAbility(PartnerAbility.getInstance());
    }

    private AlenaKessigTrapper(final AlenaKessigTrapper card) {
        super(card);
    }

    @Override
    public AlenaKessigTrapper copy() {
        return new AlenaKessigTrapper(this);
    }
}