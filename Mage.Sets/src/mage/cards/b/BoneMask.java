package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.PreventionEffectData;
import mage.abilities.effects.common.PreventNextDamageFromChosenSourceEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.target.Target;

import java.util.Set;
import java.util.UUID;

/**
 * @author awjackson
 */
public final class BoneMask extends CardImpl {

    public BoneMask(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");

        // {2}, {T}: The next time a source of your choice would deal damage to you this turn, prevent that damage. Exile cards from the top of your library equal to the damage prevented this way.
        Ability ability = new SimpleActivatedAbility(
                new PreventNextDamageFromChosenSourceEffect(
                        Duration.EndOfTurn, true,
                        BoneMaskEffectPreventionApplier.instance
                ),
                new GenericManaCost(2)
        );
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private BoneMask(final BoneMask card) {
        super(card);
    }

    @Override
    public BoneMask copy() {
        return new BoneMask(this);
    }
}

enum BoneMaskEffectPreventionApplier implements PreventNextDamageFromChosenSourceEffect.ApplierOnPrevention {
    instance;

    public boolean apply(PreventionEffectData data, Target target, GameEvent event, Ability source, Game game) {
        if (data == null || data.getPreventedDamage() <= 0) {
            return false;
        }
        int prevented = data.getPreventedDamage();
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Set<Card> cards = controller.getLibrary().getTopCards(game, prevented);
        controller.moveCards(cards, Zone.EXILED, source, game);
        return true;
    }

    public String getText() {
        return "Exile cards from the top of your library equal to the damage prevented this way";
    }
}
