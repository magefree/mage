package mage.cards.w;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ChooseExpansionSetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.List;
import java.util.UUID;

/**
 * @author L_J
 */
public final class WorldBottlingKit extends CardImpl {

    public WorldBottlingKit(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{5}");

        // {5}, Sacrifice World-Bottling Kit: Choose a Magic set. Exile all permanents with that set’s expansion symbol except for basic lands.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new WorldBottlingKitEffect(), new ManaCostsImpl<>("{5}"));
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private WorldBottlingKit(final WorldBottlingKit card) {
        super(card);
    }

    @Override
    public WorldBottlingKit copy() {
        return new WorldBottlingKit(this);
    }
}

class WorldBottlingKitEffect extends OneShotEffect {

    public WorldBottlingKitEffect() {
        super(Outcome.DestroyPermanent);
        this.staticText = "Choose a Magic set. Exile all permanents with that set's expansion symbol except for basic lands";
    }

    public WorldBottlingKitEffect(final WorldBottlingKitEffect effect) {
        super(effect);
    }

    @Override
    public WorldBottlingKitEffect copy() {
        return new WorldBottlingKitEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            ChooseExpansionSetEffect effect = new ChooseExpansionSetEffect(Outcome.Exile);
            effect.apply(game, source);
            String setChosen = null;
            if (effect.getValue("setchosen") != null) {
                setChosen = (String) effect.getValue("setchosen");
            } else if (game.getState().getValue(this.getId() + "_set") != null) {
                setChosen = (String) game.getState().getValue(this.getId() + "_set");
            }
            if (setChosen != null) {
                game.informPlayers(controller.getLogName() + " has chosen set " + setChosen);
                FilterPermanent filter = new FilterPermanent();
                filter.add(Predicates.not(Predicates.and(CardType.LAND.getPredicate(), SuperType.BASIC.getPredicate())));
                List<Permanent> permanents = game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source, game);
                for (Permanent permanent : permanents) {
                    if (permanent.getExpansionSetCode().equals(setChosen)) {
                        controller.moveCardToExileWithInfo(permanent, null, "", source, game, Zone.BATTLEFIELD, true);
                    }
                }
            }
            return true;
        }
        return false;
    }
}
