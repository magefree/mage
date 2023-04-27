package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldOrAttacksSourceTriggeredAbility;
import mage.abilities.common.delayed.OnLeaveReturnExiledToBattlefieldAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.keyword.CrewAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.ExileZone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MysteriousLimousine extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("other creature");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public MysteriousLimousine(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}{W}{W}");

        this.subtype.add(SubType.VEHICLE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Whenever Mysterious Limousine enters the battlefield or attacks, exile up to one other target creature until Mysterious Limousine leaves the battlefield. If a creature is put into exile this way, return each other card exiled with Mysterious Limousine to the battlefield under its owner's control.
        Ability ability = new EntersBattlefieldOrAttacksSourceTriggeredAbility(new MysteriousLimousineEffect());
        ability.addEffect(new CreateDelayedTriggeredAbilityEffect(new OnLeaveReturnExiledToBattlefieldAbility()));
        ability.addTarget(new TargetPermanent(0, 1, filter));
        this.addAbility(ability);

        // Crew 2
        this.addAbility(new CrewAbility(2));
    }

    private MysteriousLimousine(final MysteriousLimousine card) {
        super(card);
    }

    @Override
    public MysteriousLimousine copy() {
        return new MysteriousLimousine(this);
    }
}

class MysteriousLimousineEffect extends OneShotEffect {

    MysteriousLimousineEffect() {
        super(Outcome.Exile);
        staticText = "exile up to one other target creature until {this} leaves the battlefield. " +
                "If a creature is put into exile this way, return each other card exiled " +
                "with {this} to the battlefield under its owner's control";
    }

    private MysteriousLimousineEffect(final MysteriousLimousineEffect effect) {
        super(effect);
    }

    @Override
    public MysteriousLimousineEffect copy() {
        return new MysteriousLimousineEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (player == null || permanent == null || source.getSourcePermanentIfItStillExists(game) == null) {
            return false;
        }
        UUID exileId = CardUtil.getExileZoneId(game, source);
        Cards cards = new CardsImpl();
        ExileZone exileZone = game.getExile().getExileZone(exileId);
        if (exileZone != null) {
            cards.addAll(exileZone);
        }
        player.moveCardsToExile(permanent, source, game, true, exileId, CardUtil.getSourceName(game, source));
        if (!cards.isEmpty()) {
            player.moveCards(
                    cards.getCards(game), Zone.BATTLEFIELD, source, game,
                    false, false, true, null
            );
        }
        return true;
    }
}
