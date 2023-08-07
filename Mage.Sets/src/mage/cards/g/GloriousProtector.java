package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.delayed.OnLeaveReturnExiledAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.ForetellAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GloriousProtector extends CardImpl {

    public GloriousProtector(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}{W}");

        this.subtype.add(SubType.ANGEL);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Glorious Protector enters the battlefield, you may exile any number of non-Angel creatures you control until Glorious Protector leaves the battlefield.
        Ability ability = new EntersBattlefieldTriggeredAbility(new GloriousProtectorEffect(),true);
        this.addAbility(ability);

        // Foretell {2}{W}
        this.addAbility(new ForetellAbility(this, "{2}{W}"));
    }

    private GloriousProtector(final GloriousProtector card) {
        super(card);
    }

    @Override
    public GloriousProtector copy() {
        return new GloriousProtector(this);
    }
}

class GloriousProtectorEffect extends OneShotEffect {

    private static final FilterPermanent filter
            = new FilterControlledCreaturePermanent("non-Angel creatures you control");

    static {
        filter.add(Predicates.not(SubType.ANGEL.getPredicate()));
    }

    GloriousProtectorEffect() {
        super(Outcome.Benefit);
        staticText = "exile any number of non-Angel creatures you control until {this} leaves the battlefield";
    }

    private GloriousProtectorEffect(final GloriousProtectorEffect effect) {
        super(effect);
    }

    @Override
    public GloriousProtectorEffect copy() {
        return new GloriousProtectorEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent sourceObject = source.getSourcePermanentIfItStillExists(game);
        if (player == null || sourceObject == null) {
            return false;
        }
        TargetPermanent target = new TargetPermanent(0, Integer.MAX_VALUE, filter, true);
        player.choose(outcome, target, source, game);
        if (target.getTargets().isEmpty()) {
            return false;
        }
        player.moveCardsToExile(
                new CardsImpl(target.getTargets()).getCards(game),
                source, game, true, CardUtil.getExileZoneId(
                        game, source.getSourceId(), source.getSourceObjectZoneChangeCounter()
                ), sourceObject.getIdName()
        );
        game.addDelayedTriggeredAbility(new OnLeaveReturnExiledAbility(), source);
        return true;
    }
}
