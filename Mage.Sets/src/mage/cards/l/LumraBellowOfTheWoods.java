package mage.cards.l;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.LandsYouControlCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.MillCardsControllerEffect;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessSourceEffect;
import mage.abilities.keyword.ReachAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LumraBellowOfTheWoods extends CardImpl {

    public LumraBellowOfTheWoods(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.BEAR);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // Lumra, Bellow of the Woods's power and toughness are each equal to the number of lands you control.
        this.addAbility(new SimpleStaticAbility(
                Zone.ALL, new SetBasePowerToughnessSourceEffect(LandsYouControlCount.instance)
        ));

        // When Lumra enters, mill four cards. Then return all land cards from your graveyard to the battlefield tapped.
        Ability ability = new EntersBattlefieldTriggeredAbility(new MillCardsControllerEffect(4));
        ability.addEffect(new LumraBellowOfTheWoodsEffect());
        this.addAbility(ability);
    }

    private LumraBellowOfTheWoods(final LumraBellowOfTheWoods card) {
        super(card);
    }

    @Override
    public LumraBellowOfTheWoods copy() {
        return new LumraBellowOfTheWoods(this);
    }
}

class LumraBellowOfTheWoodsEffect extends OneShotEffect {

    LumraBellowOfTheWoodsEffect() {
        super(Outcome.Benefit);
        staticText = "Then return all land cards from your graveyard to the battlefield tapped";
    }

    private LumraBellowOfTheWoodsEffect(final LumraBellowOfTheWoodsEffect effect) {
        super(effect);
    }

    @Override
    public LumraBellowOfTheWoodsEffect copy() {
        return new LumraBellowOfTheWoodsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        return player != null && player.moveCards(
                player.getGraveyard().getCards(StaticFilters.FILTER_CARD_LAND, game),
                Zone.BATTLEFIELD, source, game, true, false, false, null
        );
    }
}
