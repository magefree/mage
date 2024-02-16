package mage.cards.g;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.PutCardFromHandOntoBattlefieldEffect;
import mage.abilities.effects.common.WinGameSourceControllerEffect;
import mage.abilities.hint.ValueHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author PurpleCrowbar
 */
public final class GallifreyStands extends CardImpl {

    private static final FilterCreatureCard filter = new FilterCreatureCard("a Doctor creature card");
    private static final FilterControlledCreaturePermanent filter2 = new FilterControlledCreaturePermanent(SubType.DOCTOR, "Doctors");

    static {
        filter.add(SubType.DOCTOR.getPredicate());
    }

    public GallifreyStands(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{4}{W}{U}");
        this.supertype.add(SuperType.LEGENDARY);

        // When Gallifrey Stands enters the battlefield, return all Doctor cards from your graveyard to your hand.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new GallifreyStandsReturnEffect()));

        // At the beginning of your upkeep, you may put a Doctor creature card from your hand onto the battlefield. Then if you control thirteen or more Doctors, you win the game.
        Ability ability = new BeginningOfUpkeepTriggeredAbility(new PutCardFromHandOntoBattlefieldEffect(filter), TargetController.YOU, false);
        ability.addEffect(new ConditionalOneShotEffect(new WinGameSourceControllerEffect(), new PermanentsOnTheBattlefieldCondition(filter2, ComparisonType.MORE_THAN, 12))
                .setText("Then if you control thirteen or more Doctors, you win the game"));
        this.addAbility(ability.addHint(new ValueHint("Doctors you control", new PermanentsOnBattlefieldCount(filter2))));
    }

    private GallifreyStands(final GallifreyStands card) {
        super(card);
    }

    @Override
    public GallifreyStands copy() {
        return new GallifreyStands(this);
    }
}

class GallifreyStandsReturnEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCard();

    static {
        filter.add(SubType.DOCTOR.getPredicate());
    }

    GallifreyStandsReturnEffect() {
        super(Outcome.Benefit);
        staticText = "return all Doctor cards from your graveyard to your hand";
    }

    private GallifreyStandsReturnEffect(final GallifreyStandsReturnEffect effect) {
        super(effect);
    }

    @Override
    public GallifreyStandsReturnEffect copy() {
        return new GallifreyStandsReturnEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Cards cards = new CardsImpl(player.getGraveyard().getCards(filter, game));
        return player.moveCards(cards, Zone.HAND, source, game);
    }
}
