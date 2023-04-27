package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.Locale;
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
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new CosmicHorrorEffect(new ManaCostsImpl<>("{3}{B}{B}{B}")), TargetController.YOU, false));
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

    protected Cost cost;

    public CosmicHorrorEffect(Cost cost) {
        super(Outcome.DestroyPermanent);
        this.cost = cost;
        staticText = "destroy {this} unless you pay {3}{B}{B}{B}. If {this} is destroyed this way it deals 7 damage to you";
    }

    public CosmicHorrorEffect(final CosmicHorrorEffect effect) {
        super(effect);
        this.cost = effect.cost.copy();
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent cosmicHorror = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (controller != null && cosmicHorror != null) {
            StringBuilder sb = new StringBuilder(cost.getText()).append('?');
            if (!sb.toString().toLowerCase(Locale.ENGLISH).startsWith("exile ") && !sb.toString().toLowerCase(Locale.ENGLISH).startsWith("return ")) {
                sb.insert(0, "Pay ");
            }
            if (controller.chooseUse(Outcome.Benefit, sb.toString(), source, game)) {
                cost.clearPaid();
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
