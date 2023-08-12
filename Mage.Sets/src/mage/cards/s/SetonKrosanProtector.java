
package mage.cards.s;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mage.MageInt;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.effects.mana.BasicManaEffect;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.Game;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author cbt33
 */
public final class SetonKrosanProtector extends CardImpl {
    
    public SetonKrosanProtector(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{G}{G}{G}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.CENTAUR);
        this.subtype.add(SubType.DRUID);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Tap an untapped Druid you control: Add {G}.
        FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("untapped Druid you control");
        filter.add(TappedPredicate.UNTAPPED);
        filter.add(SubType.DRUID.getPredicate());
        this.addAbility(new SimpleManaAbility(
                Zone.BATTLEFIELD, 
                new SetonKrosanProtectorManaEffect(filter),
                new TapTargetCost(new TargetControlledCreaturePermanent(1, 1, filter, true))));
    }

    private SetonKrosanProtector(final SetonKrosanProtector card) {
        super(card);
    }

    @Override
    public SetonKrosanProtector copy() {
        return new SetonKrosanProtector(this);
    }
}

class SetonKrosanProtectorManaEffect extends BasicManaEffect {

    private final FilterPermanent filter;
    
    public SetonKrosanProtectorManaEffect(FilterPermanent filter) {
        super(Mana.GreenMana(1));
        this.filter = filter;
    }

    public SetonKrosanProtectorManaEffect(final SetonKrosanProtectorManaEffect effect) {
        super(effect);
        this.filter = effect.filter.copy();
    }

    @Override
    public SetonKrosanProtectorManaEffect copy() {
        return new SetonKrosanProtectorManaEffect(this);
    }

    @Override
    public List<Mana> getNetMana(Game game, Ability source) {
        if (game != null && game.inCheckPlayableState()) {
            // Because the ability can be used multiple times, multiply with untapped druids
            int count = game.getBattlefield().count(filter, source.getControllerId(), source, game);
            List<Mana> netMana = new ArrayList<>();
            if (count > 0) {
                netMana.add(Mana.GreenMana(count));
            }
            return netMana;
                    
        }
        return super.getNetMana(game, source);
    }

}
