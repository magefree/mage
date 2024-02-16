package mage.cards.s;

import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;

import java.util.Objects;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class StonespeakerCrystal extends CardImpl {

    public StonespeakerCrystal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");

        // {T}: Add {C}{C}.
        this.addAbility(new SimpleManaAbility(Zone.BATTLEFIELD, Mana.ColorlessMana(2), new TapSourceCost()));

        // {2}, {T}, Sacrifice Stonespeaker Crystal: Exile any number of target players' graveyards. Draw a card.
        Ability ability = new SimpleActivatedAbility(new StonespeakerCrystalEffect(), new GenericManaCost(2));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        ability.addTarget(new TargetPlayer(0, Integer.MAX_VALUE, false));
        this.addAbility(ability);
    }

    private StonespeakerCrystal(final StonespeakerCrystal card) {
        super(card);
    }

    @Override
    public StonespeakerCrystal copy() {
        return new StonespeakerCrystal(this);
    }
}

class StonespeakerCrystalEffect extends OneShotEffect {

    StonespeakerCrystalEffect() {
        super(Outcome.Benefit);
        staticText = "exile any number of target players' graveyards. Draw a card";
    }

    private StonespeakerCrystalEffect(final StonespeakerCrystalEffect effect) {
        super(effect);
    }

    @Override
    public StonespeakerCrystalEffect copy() {
        return new StonespeakerCrystalEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Cards cards = new CardsImpl();
        this.getTargetPointer()
                .getTargets(game, source)
                .stream()
                .map(game::getPlayer)
                .filter(Objects::nonNull)
                .map(Player::getGraveyard)
                .forEach(cards::addAll);
        controller.moveCards(cards, Zone.EXILED, source, game);
        controller.drawCards(1, source, game);
        return true;
    }
}
