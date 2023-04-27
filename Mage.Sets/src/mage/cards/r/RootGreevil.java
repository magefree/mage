
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.ChoiceColor;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterEnchantmentPermanent;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author Derpthemeus
 */
public final class RootGreevil extends CardImpl {

    public RootGreevil(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // {2}{G}, {tap}, Sacrifice Root Greevil: Destroy all enchantments of the color of your choice.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new RootGreevilEffect(), new ManaCostsImpl<>("{2}{G}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private RootGreevil(final RootGreevil card) {
        super(card);
    }

    @Override
    public RootGreevil copy() {
        return new RootGreevil(this);
    }

    static class RootGreevilEffect extends OneShotEffect {

        public RootGreevilEffect() {
            super(Outcome.DestroyPermanent);
            this.staticText = "Destroy all enchantments of the color of your choice";
        }

        public RootGreevilEffect(final RootGreevilEffect effect) {
            super(effect);
        }

        @Override
        public RootGreevilEffect copy() {
            return new RootGreevilEffect(this);
        }

        @Override
        public boolean apply(Game game, Ability source) {
            Player controller = game.getPlayer(source.getControllerId());
            ChoiceColor choice = new ChoiceColor();
            if (controller != null && controller.choose(Outcome.DestroyPermanent, choice, game)) {
                FilterEnchantmentPermanent filter = new FilterEnchantmentPermanent();
                filter.add(new ColorPredicate(choice.getColor()));
                for (Permanent enchantment : game.getBattlefield().getAllActivePermanents(filter, game)) {
                    enchantment.destroy(source, game, false);
                }
                return true;
            }
            return false;
        }
    }
}
