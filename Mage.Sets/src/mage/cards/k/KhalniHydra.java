
package mage.cards.k;

import java.util.Iterator;
import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;

/**
 *
 * @author maurer.it_at_gmail.com
 */
public final class KhalniHydra extends CardImpl {

    private static final FilterControlledCreaturePermanent filter;

    static {
        filter = new FilterControlledCreaturePermanent();
        filter.add(new ColorPredicate(ObjectColor.GREEN));
    }

    public KhalniHydra(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{G}{G}{G}{G}{G}{G}{G}{G}");
        this.subtype.add(SubType.HYDRA);

        this.power = new MageInt(8);
        this.toughness = new MageInt(8);
        this.addAbility(new SimpleStaticAbility(Zone.STACK, new KhalniHydraCostReductionEffect()));
        this.addAbility(TrampleAbility.getInstance());
    }

    public KhalniHydra(final KhalniHydra card) {
        super(card);
    }

    @Override
    public void adjustCosts(Ability ability, Game game) {
        super.adjustCosts(ability, game);
        int reductionAmount = game.getBattlefield().count(filter,  ability.getSourceId(), ability.getControllerId(), game);
        Iterator<ManaCost> iter = ability.getManaCostsToPay().iterator();

        while ( reductionAmount > 0 && iter.hasNext() ) {
            ManaCost manaCost = iter.next();
            if (manaCost.getMana().getGreen() > 0) { // in case another effect adds additional mana cost
                iter.remove();
                reductionAmount--;
            }
        }
    }

    @Override
    public KhalniHydra copy() {
        return new KhalniHydra(this);
    }
}

class KhalniHydraCostReductionEffect extends OneShotEffect {
    private static final String effectText = "{this} costs {G} less to cast for each green creature you control";

    KhalniHydraCostReductionEffect ( ) {
        super(Outcome.Benefit);
        this.staticText = effectText;
    }

    KhalniHydraCostReductionEffect ( KhalniHydraCostReductionEffect effect ) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public KhalniHydraCostReductionEffect copy() {
        return new KhalniHydraCostReductionEffect(this);
    }

}
