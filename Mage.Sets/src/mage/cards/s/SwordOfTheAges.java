package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetAnyTarget;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author L_J
 */
public final class SwordOfTheAges extends CardImpl {

    public SwordOfTheAges(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{6}");

        // Sword of the Ages enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // {T}, Sacrifice Sword of the Ages and any number of creatures you control: Sword of the Ages deals X damage to any target, where X is the total power of the creatures sacrificed this way, then exile Sword of the Ages and those creature cards.
        Cost cost = new SacrificeSourceCost();
        cost.setText("Sacrifice {this} and any number of creatures you control");
        Cost cost2 = new SacrificeTargetCost(new TargetControlledCreaturePermanent(0, Integer.MAX_VALUE, new FilterControlledCreaturePermanent(), true));
        cost2.setText("");
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new SwordOfTheAgesEffect(), new TapSourceCost());
        ability.addCost(cost);
        ability.addCost(cost2);
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    private SwordOfTheAges(final SwordOfTheAges card) {
        super(card);
    }

    @Override
    public SwordOfTheAges copy() {
        return new SwordOfTheAges(this);
    }
}

class SwordOfTheAgesEffect extends OneShotEffect {

    public SwordOfTheAgesEffect() {
        super(Outcome.Damage);
        this.staticText = "{this} deals X damage to any target, where X is the total power of the creatures sacrificed this way, then exile {this} and those creature cards";
    }

    private SwordOfTheAgesEffect(final SwordOfTheAgesEffect effect) {
        super(effect);
    }

    @Override
    public SwordOfTheAgesEffect copy() {
        return new SwordOfTheAgesEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Set<Card> cardsToExile = new HashSet<>();
        if (controller != null) {
            int totalPower = 0;
            Card card = game.getCard(source.getSourceId());
            if (card != null) {
                cardsToExile.add(card); // consulted this on mtgjudges - regardless of what zone they end up in after sac, the creature cards and Sword will be moved to exile (unless there's another replacement effect for exiling them)
            }
            for (Cost cost : source.getCosts()) {
                // Sword of the Ages doesn't count itself for total damage if it's a creature: http://www.mtgsalvation.com/forums/the-game/commander-edh/202963-sword-of-the-ages
                if (cost instanceof SacrificeTargetCost) {
                    for (Permanent permanent : ((SacrificeTargetCost) cost).getPermanents()) {
                        totalPower += permanent.getPower().getValue();
                        Card permanentCard = game.getCard(permanent.getId());
                        if (permanentCard != null) {
                            cardsToExile.add(permanentCard);
                        }
                    }
                }
            }
            if (totalPower > 0) {
                Player player = game.getPlayer(this.getTargetPointer().getFirst(game, source));
                if (player != null) {
                    player.damage(totalPower, source.getSourceId(), source, game);
                } else {
                    Permanent creature = game.getPermanent(this.getTargetPointer().getFirst(game, source));
                    if (creature != null) {
                        creature.damage(totalPower, source.getSourceId(), source, game, false, true);
                    }
                }
            }
            return controller.moveCards(cardsToExile, Zone.EXILED, source, game);
        }
        return false;
    }
}
