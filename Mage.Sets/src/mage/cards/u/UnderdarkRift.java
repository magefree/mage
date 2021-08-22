package mage.cards.u;

import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.costs.common.ExileSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class UnderdarkRift extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("artifact, creature, or planeswalker");

    static {
        filter.add(Predicates.or(
                CardType.ARTIFACT.getPredicate(),
                CardType.CREATURE.getPredicate(),
                CardType.PLANESWALKER.getPredicate()
        ));
    }

    public UnderdarkRift(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // {T}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        // {5}, {T}, Exile Underdark Rift: Roll a d10. Put a target artifact, creature, or planeswalker into its owner's library just beneath the top X cards of that library, where X is the result. Activate only has a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(new UnderdarkRiftEffect(), new GenericManaCost(5));
        ability.addCost(new TapSourceCost());
        ability.addCost(new ExileSourceCost());
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private UnderdarkRift(final UnderdarkRift card) {
        super(card);
    }

    @Override
    public UnderdarkRift copy() {
        return new UnderdarkRift(this);
    }
}

class UnderdarkRiftEffect extends OneShotEffect {

    UnderdarkRiftEffect() {
        super(Outcome.Benefit);
        staticText = "roll a d10. Put target artifact, creature, or planeswalker into its owner's library " +
                "just beneath the top X cards of that library, where X is the result";
    }

    private UnderdarkRiftEffect(final UnderdarkRiftEffect effect) {
        super(effect);
    }

    @Override
    public UnderdarkRiftEffect copy() {
        return new UnderdarkRiftEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (player == null || permanent == null) {
            return false;
        }
        int result = player.rollDice(source, game, 10);
        player.putCardOnTopXOfLibrary(permanent, game, source, result + 1, true);
        return true;
    }
}
