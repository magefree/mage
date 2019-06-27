
package mage.cards.w;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.effects.common.UntapSourceEffect;
import mage.abilities.keyword.TransformAbility;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.permanent.token.HumanClericToken;
import mage.target.common.TargetControlledPermanent;

/**
 * @author fireshoes
 */
public final class WestvaleAbbey extends CardImpl {

    public WestvaleAbbey(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        this.transformable = true;
        this.secondSideCardClazz = mage.cards.o.OrmendahlProfanePrince.class;

        // {T}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        // {5}, {T}, Pay 1 life: Create a 1/1 white and black Human Cleric creature token.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CreateTokenEffect(new HumanClericToken()), new GenericManaCost(5));
        ability.addCost(new TapSourceCost());
        ability.addCost(new PayLifeCost(1));
        this.addAbility(ability);

        // {5}, {T}, Sacrifice five creatures: Transform Westvale Abbey and untap it.
        this.addAbility(new TransformAbility());
        ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new TransformSourceEffect(true), new GenericManaCost(5));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeTargetCost(new TargetControlledPermanent(5, 5, new FilterControlledCreaturePermanent("creatures"), true)));
        ability.addEffect(new UntapSourceEffect());
        this.addAbility(ability);
    }

    public WestvaleAbbey(final WestvaleAbbey card) {
        super(card);
    }

    @Override
    public WestvaleAbbey copy() {
        return new WestvaleAbbey(this);
    }
}
