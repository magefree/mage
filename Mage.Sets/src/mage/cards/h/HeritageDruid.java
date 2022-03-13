package mage.cards.h;

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
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.Game;
import mage.target.common.TargetControlledPermanent;

/**
 * @author Loki
 */
public final class HeritageDruid extends CardImpl {

    public HeritageDruid(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}");
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.DRUID);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Tap three untapped Elves you control: Add {G}{G}{G}.
        FilterControlledPermanent filter = new FilterControlledPermanent("untapped Elves you control");
        filter.add(TappedPredicate.UNTAPPED);
        filter.add(SubType.ELF.getPredicate());
        this.addAbility(new SimpleManaAbility(
                Zone.BATTLEFIELD,
                new HeritageDruidManaEffect(filter),
                new TapTargetCost(new TargetControlledPermanent(3, 3, filter, true))));
    }

    private HeritageDruid(final HeritageDruid card) {
        super(card);
    }

    @Override
    public HeritageDruid copy() {
        return new HeritageDruid(this);
    }
}

class HeritageDruidManaEffect extends BasicManaEffect {

    private final FilterPermanent filter;

    public HeritageDruidManaEffect(FilterPermanent filter) {
        super(Mana.GreenMana(3));
        this.filter = filter;
    }

    public HeritageDruidManaEffect(final HeritageDruidManaEffect effect) {
        super(effect);
        this.filter = effect.filter.copy();
    }

    @Override
    public HeritageDruidManaEffect copy() {
        return new HeritageDruidManaEffect(this);
    }

    @Override
    public List<Mana> getNetMana(Game game, Ability source) {
        if (game != null && game.inCheckPlayableState()) {
            int count = game.getBattlefield().count(filter, source.getControllerId(), source, game) / 3;
            List<Mana> netMana = new ArrayList<>();
            if (count > 0) {
                netMana.add(Mana.GreenMana(count * 3));
            }
            return netMana;
        }
        return super.getNetMana(game, source);
    }

}
