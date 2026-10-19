package mage.cards.f;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.cost.SpellCostReductionSourceEffect;
import mage.abilities.keyword.OffspringAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.util.PerpetualUtil;

import java.util.UUID;

public final class FountainportCharmer extends CardImpl {

    public FountainportCharmer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");
        this.subtype.add(SubType.FROG);
        this.subtype.add(SubType.BARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        this.addAbility(new OffspringAbility("{2}"));
        this.addAbility(new EntersBattlefieldTriggeredAbility(new FountainportCharmerEffect()));
    }

    private FountainportCharmer(final FountainportCharmer card) {
        super(card);
    }

    @Override
    public FountainportCharmer copy() {
        return new FountainportCharmer(this);
    }
}

class FountainportCharmerEffect extends OneShotEffect {

    FountainportCharmerEffect() {
        super(Outcome.Benefit);
        staticText = "creature cards in your hand perpetually gain \"This spell costs {1} less to cast.\"";
    }

    private FountainportCharmerEffect(final FountainportCharmerEffect effect) {
        super(effect);
    }

    @Override
    public FountainportCharmerEffect copy() {
        return new FountainportCharmerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        for (Card card : player.getHand().getCards(StaticFilters.FILTER_CARD_CREATURE, game)) {
            PerpetualUtil.addAbility(card, new SimpleStaticAbility(Zone.ALL,
                    new SpellCostReductionSourceEffect(1)), source, game);
        }
        return true;
    }
}
