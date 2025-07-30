package mage.cards.w;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.effects.common.UntapSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.HumanClericToken;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class WestvaleAbbey extends TransformingDoubleFacedCard {

    public WestvaleAbbey(UUID ownerId, CardSetInfo setInfo) {
        super(
                ownerId, setInfo,
                new SuperType[]{}, new CardType[]{CardType.LAND}, new SubType[]{}, "",
                "Ormendahl, Profane Prince",
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.CREATURE}, new SubType[]{SubType.DEMON}, "B"
        );
        this.getRightHalfCard().setPT(9, 7);

        this.secondSideCardClazz = mage.cards.o.OrmendahlProfanePrince.class;

        // {T}: Add {C}.
        this.getLeftHalfCard().addAbility(new ColorlessManaAbility());

        // {5}, {T}, Pay 1 life: Create a 1/1 white and black Human Cleric creature token.
        Ability ability = new SimpleActivatedAbility(new CreateTokenEffect(new HumanClericToken()), new GenericManaCost(5));
        ability.addCost(new TapSourceCost());
        ability.addCost(new PayLifeCost(1));
        this.getLeftHalfCard().addAbility(ability);

        // {5}, {T}, Sacrifice five creatures: Transform Westvale Abbey and untap it.
        ability = new SimpleActivatedAbility(new TransformSourceEffect(), new GenericManaCost(5));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeTargetCost(5, StaticFilters.FILTER_PERMANENT_CREATURES));
        ability.addEffect(new UntapSourceEffect().setText("untap it").concatBy(", then"));
        this.getLeftHalfCard().addAbility(ability);

        // Ormendahl, Profane Prince
        // Flying
        this.getRightHalfCard().addAbility(FlyingAbility.getInstance());

        // Lifelink
        this.getRightHalfCard().addAbility(LifelinkAbility.getInstance());

        // Indestructible
        this.getRightHalfCard().addAbility(IndestructibleAbility.getInstance());

        // Haste
        this.getRightHalfCard().addAbility(HasteAbility.getInstance());

        this.finalizeDFC();
    }

    private WestvaleAbbey(final WestvaleAbbey card) {
        super(card);
    }

    @Override
    public WestvaleAbbey copy() {
        return new WestvaleAbbey(this);
    }
}
