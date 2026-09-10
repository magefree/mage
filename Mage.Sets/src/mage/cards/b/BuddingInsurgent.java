package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;

/**
 *
 * @author muz
 */
public final class BuddingInsurgent extends CardImpl {

    public BuddingInsurgent(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.DRYAD);
        this.subtype.add(SubType.SCOUT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Sacrifice this creature: Destroy target artifact or enchantment. If that permanent was a legendary enchantment, draw a card. Activate only as a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(new BuddingInsurgentEffect(), new SacrificeSourceCost());
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_PERMANENT_ARTIFACT_OR_ENCHANTMENT));
        this.addAbility(ability);
    }

    private BuddingInsurgent(final BuddingInsurgent card) {
        super(card);
    }

    @Override
    public BuddingInsurgent copy() {
        return new BuddingInsurgent(this);
    }
}


class BuddingInsurgentEffect extends OneShotEffect {

    BuddingInsurgentEffect() {
        super(Outcome.DestroyPermanent);
        this.staticText = "Destroy target artifact or enchantment. If that permanent was a legendary enchantment, draw a card";
    }

    private BuddingInsurgentEffect(final BuddingInsurgentEffect effect) {
        super(effect);
    }

    @Override
    public BuddingInsurgentEffect copy() {
        return new BuddingInsurgentEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (player != null && permanent != null) {
            permanent.destroy(source, game, true);
            game.processAction();
            if (permanent.isLegendary() && permanent.isEnchantment(game)) {
                player.drawCards(1, source, game);
            }
            return true;
        }
        return false;
    }
}
