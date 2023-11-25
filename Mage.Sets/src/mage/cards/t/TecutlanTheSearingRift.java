package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.common.CastSpellPaidBySourceTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.keyword.DiscoverEffect;
import mage.abilities.mana.RedManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterSpell;
import mage.filter.predicate.mageobject.PermanentPredicate;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.players.Player;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class TecutlanTheSearingRift extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("a permanent spell");

    static {
        filter.add(PermanentPredicate.instance);
    }

    public TecutlanTheSearingRift(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.CAVE);

        // (Transforms from Brass's Tunnel-Grinder.)
        this.nightCard = true;

        // {T}: Add {R}.
        this.addAbility(new RedManaAbility());

        // Whenever you cast a permanent spell using mana produced by Tecutlan, the Searing Rift, discover X, where X is that spell's mana value.
        this.addAbility(new CastSpellPaidBySourceTriggeredAbility(
                new TecutlanTheSearingRiftEffect(),
                filter, true
        ));
    }

    private TecutlanTheSearingRift(final TecutlanTheSearingRift card) {
        super(card);
    }

    @Override
    public TecutlanTheSearingRift copy() {
        return new TecutlanTheSearingRift(this);
    }
}

class TecutlanTheSearingRiftEffect extends OneShotEffect {

    TecutlanTheSearingRiftEffect() {
        super(Outcome.Benefit);
        staticText = "discover X, where X is that spell's mana value";
    }

    private TecutlanTheSearingRiftEffect(final TecutlanTheSearingRiftEffect effect) {
        super(effect);
    }

    @Override
    public TecutlanTheSearingRiftEffect copy() {
        return new TecutlanTheSearingRiftEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }

        Spell spell = game.getSpellOrLKIStack(getTargetPointer().getFirst(game, source));
        int mv = spell == null ? 0 : Math.max(0, spell.getManaValue());

        DiscoverEffect.doDiscover(controller, mv, game, source);
        return true;
    }

}