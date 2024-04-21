package mage.cards.v;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.ReachAbility;
import mage.cards.*;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.UUID;

/**
 * @author karapuzz14
 */
public final class VinesoulSpider extends CardImpl {

    public VinesoulSpider(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");
        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.SPIDER);

        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        this.addAbility(ReachAbility.getInstance());

        // At the beginning of your end step, put a random land card from your library into your graveyard.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                new mage.cards.v.VinesoulSpiderEffect(), TargetController.YOU, false
        ));

    }

    private VinesoulSpider(final mage.cards.v.VinesoulSpider card) {
        super(card);
    }

    @Override
    public mage.cards.v.VinesoulSpider copy() {
        return new mage.cards.v.VinesoulSpider(this);
    }
}

class VinesoulSpiderEffect extends OneShotEffect {

    VinesoulSpiderEffect() {
        super(Outcome.Discard);
        this.staticText = "put a random land card from your library into your graveyard";
    }

    private VinesoulSpiderEffect(final mage.cards.v.VinesoulSpiderEffect effect) {
        super(effect);
    }

    @Override
    public mage.cards.v.VinesoulSpiderEffect copy() {
        return new mage.cards.v.VinesoulSpiderEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent sourcePermanent = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (controller == null || sourcePermanent == null) {
            return false;
        }
        controller.putRandomCardInGraveyard(StaticFilters.FILTER_CARD_LAND, source, game);

        return true;
    }
}


