package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.CantPayLifeOrSacrificeEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;

import java.util.UUID;

/**
 * @author noxx
 */
public final class AngelOfJubilation extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creatures");

    public AngelOfJubilation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}{W}{W}");
        this.subtype.add(SubType.ANGEL);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        this.addAbility(FlyingAbility.getInstance());

        // Other nonblack creatures you control get +1/+1.
        this.addAbility(new SimpleStaticAbility(new BoostControlledEffect(1, 1, Duration.WhileOnBattlefield, StaticFilters.FILTER_PERMANENT_CREATURES_NON_BLACK, true)));

        // Players can't pay life or sacrifice creatures to cast spells or activate abilities.
        Ability ability = new SimpleStaticAbility(new CantPayLifeOrSacrificeEffect(filter));
        this.addAbility(ability);
    }

    private AngelOfJubilation(final AngelOfJubilation card) {
        super(card);
    }

    @Override
    public AngelOfJubilation copy() {
        return new AngelOfJubilation(this);
    }
}