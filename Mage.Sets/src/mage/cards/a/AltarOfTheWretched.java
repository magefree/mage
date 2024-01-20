package mage.cards.a;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToHandEffect;
import mage.abilities.keyword.CraftAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author jeffwadsworth
 */
public final class AltarOfTheWretched extends CardImpl {

    public AltarOfTheWretched(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}{B}");
        this.secondSideCardClazz = mage.cards.w.WretchedBonemass.class;

        // When Altar of the Wretched enters the battlefield, you may sacrifice a nontoken creature. If you do, draw X cards, then mill X cards, where X is that creature’s power.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new AltarOfTheWretchedEffect(), true));

        // Craft with one or more creatures {2}{B}{B}
        this.addAbility(new CraftAbility(
                "{2}{B}{B}", "one or more creatures", "other creatures you control and/or"
                + "creature cards in your graveyard", 1, Integer.MAX_VALUE, CardType.CREATURE.getPredicate()
        ));

        // {2}{B}: Return Altar of the Wretched from your graveyard to your hand.
        this.addAbility(new SimpleActivatedAbility(Zone.GRAVEYARD, new ReturnSourceFromGraveyardToHandEffect(), new ManaCostsImpl<>("{2}{B}")));

    }

    private AltarOfTheWretched(final AltarOfTheWretched card) {
        super(card);
    }

    @Override
    public AltarOfTheWretched copy() {
        return new AltarOfTheWretched(this);
    }
}

class AltarOfTheWretchedEffect extends OneShotEffect {

    AltarOfTheWretchedEffect() {
        super(Outcome.Benefit);
        staticText = "you may sacrifice a nontoken creature. If you do, draw X cards, then mill X cards, where X is that creature's power.";
    }

    private AltarOfTheWretchedEffect(final AltarOfTheWretchedEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            SacrificeTargetCost cost = new SacrificeTargetCost(1, StaticFilters.FILTER_CONTROLLED_CREATURE_NON_TOKEN);
            if (cost.canPay(source, source, controller.getId(), game)
                    && cost.pay(source, game, source, controller.getId(), true)) {
                final int power = cost.getPermanents().get(0).getPower().getValue();
                controller.drawCards(power, source, game);
                controller.millCards(power, source, game);
                return true;
            }
        }
        return false;
    }

    @Override
    public AltarOfTheWretchedEffect copy() {
        return new AltarOfTheWretchedEffect(this);
    }
}
