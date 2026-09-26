package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.abilities.common.GiveManaAbilityAndCastSourceAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.CastSourceTriggeredAbility;
import mage.abilities.effects.common.UntapAllLandsControllerEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.keyword.WardAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;

/**
 *
 * @author muz
 */
public final class EmrakulTheExigentDoom extends CardImpl {

    public EmrakulTheExigentDoom(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{10}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELDRAZI);
        this.power = new MageInt(12);
        this.toughness = new MageInt(12);

        // When you cast this spell, untap all lands you control.
        this.addAbility(new CastSourceTriggeredAbility(new UntapAllLandsControllerEffect()));

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Ward--Sacrifice three permanents.
        this.addAbility(new WardAbility(new SacrificeTargetCost(3, StaticFilters.FILTER_PERMANENT), false));

        // {3}, Exile this card from your hand: Target land gains "{T}: Add {C}{C}" until this card is cast from exile. You may cast this card for as long as it remains exiled.
        this.addAbility(new GiveManaAbilityAndCastSourceAbility("CC", 3));
    }

    private EmrakulTheExigentDoom(final EmrakulTheExigentDoom card) {
        super(card);
    }

    @Override
    public EmrakulTheExigentDoom copy() {
        return new EmrakulTheExigentDoom(this);
    }
}
