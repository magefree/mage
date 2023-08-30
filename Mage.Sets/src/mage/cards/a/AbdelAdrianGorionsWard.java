package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ChooseABackgroundAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.delayed.OnLeaveReturnExiledAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterNonlandPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.SoldierToken;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AbdelAdrianGorionsWard extends CardImpl {

    public AbdelAdrianGorionsWard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // When Abdel Adrian, Gorion's Ward enters the battlefield, exile any number of other nonland permanents you control until Abdel Adrian leaves the battlefield. Create a 1/1 white Soldier creature token for each permanent exiled this way.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new AbdelAdrianGorionsWardEffect()));

        // Choose a Background
        this.addAbility(ChooseABackgroundAbility.getInstance());
    }

    private AbdelAdrianGorionsWard(final AbdelAdrianGorionsWard card) {
        super(card);
    }

    @Override
    public AbdelAdrianGorionsWard copy() {
        return new AbdelAdrianGorionsWard(this);
    }
}

class AbdelAdrianGorionsWardEffect extends OneShotEffect {

    private static final FilterPermanent filter
            = new FilterNonlandPermanent("other nonland permanents you control");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(TargetController.YOU.getOwnerPredicate());
    }

    AbdelAdrianGorionsWardEffect() {
        super(Outcome.Benefit);
        staticText = "exile any number of other nonland permanents you control until {this} leaves the battlefield. " +
                "Create a 1/1 white Soldier creature token for each permanent exiled this way";
    }

    private AbdelAdrianGorionsWardEffect(final AbdelAdrianGorionsWardEffect effect) {
        super(effect);
    }

    @Override
    public AbdelAdrianGorionsWardEffect copy() {
        return new AbdelAdrianGorionsWardEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        if (player == null || permanent == null) {
            return false;
        }
        TargetPermanent target = new TargetPermanent(0, Integer.MAX_VALUE, filter, true);
        player.choose(outcome, target, source, game);
        Cards cards = new CardsImpl(target.getTargets());
        player.moveCardsToExile(
                cards.getCards(game), source, game, true,
                CardUtil.getExileZoneId(game, source),
                CardUtil.getSourceName(game, source)
        );
        cards.retainZone(Zone.EXILED, game);
        int count = cards.size();
        if (count > 0) {
            new SoldierToken().putOntoBattlefield(count, game, source);
        }
        game.addDelayedTriggeredAbility(new OnLeaveReturnExiledAbility(), source);
        return true;
    }
}
