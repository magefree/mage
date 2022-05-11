package mage.cards.b;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mage.MageInt;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.mana.AddManaOfAnyColorEffect;
import mage.abilities.keyword.MorphAbility;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.Game;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class BirchloreRangers extends CardImpl {

    public BirchloreRangers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}");
        this.subtype.add(SubType.ELF, SubType.DRUID, SubType.RANGER);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Tap two untapped Elves you control: Add one mana of any color.
        FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("untapped Elves you control");
        filter.add(TappedPredicate.UNTAPPED);
        filter.add(SubType.ELF.getPredicate());
        this.addAbility(new SimpleManaAbility(
                Zone.BATTLEFIELD,
                new BirchloreRangersManaEffect(filter),
                new TapTargetCost(new TargetControlledCreaturePermanent(2, 2, filter, false))));        
        
        // Morph {G}
        this.addAbility(new MorphAbility(new ManaCostsImpl("{G}")));
    }

    private BirchloreRangers(final BirchloreRangers card) {
        super(card);
    }

    @Override
    public BirchloreRangers copy() {
        return new BirchloreRangers(this);
    }
}

class BirchloreRangersManaEffect extends AddManaOfAnyColorEffect {

    private final FilterPermanent filter;

    public BirchloreRangersManaEffect(FilterPermanent filter) {
        super(1);
        this.filter = filter;
    }

    public BirchloreRangersManaEffect(final BirchloreRangersManaEffect effect) {
        super(effect);
        this.filter = effect.filter.copy();
    }

    @Override
    public BirchloreRangersManaEffect copy() {
        return new BirchloreRangersManaEffect(this);
    }

    @Override
    public List<Mana> getNetMana(Game game, Ability source) {
        if (game != null && game.inCheckPlayableState()) {
            int count = game.getBattlefield().count(filter, source.getControllerId(), source, game) / 2;
            List<Mana> netMana = new ArrayList<>();
            if (count > 0) {
                netMana.add(Mana.AnyMana(count * 2));
            }
            return netMana;
        }
        return super.getNetMana(game, source);
    }

}