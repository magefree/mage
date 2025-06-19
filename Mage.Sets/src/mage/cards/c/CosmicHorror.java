package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class CosmicHorror extends CardImpl {

    public CosmicHorror(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}{B}{B}");
        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(7);
        this.toughness = new MageInt(7);

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // At the beginning of your upkeep, destroy Cosmic Horror unless you pay {3}{B}{B}{B}. If Cosmic Horror is destroyed this way, it deals 7 damage to you.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new CosmicHorrorEffect()));
    }

    private CosmicHorror(final CosmicHorror card) {
        super(card);
    }

    @Override
    public CosmicHorror copy() {
        return new CosmicHorror(this);
    }
}

class CosmicHorrorEffect extends OneShotEffect {

    CosmicHorrorEffect() {
        super(Outcome.DestroyPermanent);
        staticText = "destroy {this} unless you pay {3}{B}{B}{B}. If {this} is destroyed this way, it deals 7 damage to you";
    }

    private CosmicHorrorEffect(final CosmicHorrorEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent cosmicHorror = source.getSourcePermanentIfItStillExists(game);
        if (controller != null && cosmicHorror != null) {
            if (controller.chooseUse(Outcome.Benefit, "Pay {3}{B}{B}{B} to prevent destroy effect?", source, game)) {
                Cost cost = new ManaCostsImpl<>("{3}{B}{B}{B}");
                if (cost.pay(source, game, source, source.getControllerId(), false, null)) {
                    return true;
                }
            }
            if (cosmicHorror.destroy(source, game, false)) {
                controller.damage(7, source.getSourceId(), source, game);
                return true;
            }
        }
        return false;
    }

    @Override
    public CosmicHorrorEffect copy() {
        return new CosmicHorrorEffect(this);
    }
}
