package mage.cards.h;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.delayed.OnLeaveReturnExiledAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HostageTaker extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("another target artifact or creature");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(Predicates.or(
                CardType.ARTIFACT.getPredicate(),
                CardType.CREATURE.getPredicate()
        ));
    }

    public HostageTaker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.PIRATE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // When Hostage Taker enters the battlefield, exile another target artifact or creature until Hostage Taker leaves the battlefield. You may cast that card as long as it remains exiled, and you may spend mana as though it were mana of any type to cast that spell.
        Ability ability = new EntersBattlefieldTriggeredAbility(new HostageTakerExileEffect());
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private HostageTaker(final HostageTaker card) {
        super(card);
    }

    @Override
    public HostageTaker copy() {
        return new HostageTaker(this);
    }
}

class HostageTakerExileEffect extends OneShotEffect {

    HostageTakerExileEffect() {
        super(Outcome.Benefit);
        this.staticText = "exile another target creature or artifact until {this} leaves the battlefield. "
                + "You may cast that card for as long as it remains exiled, "
                + "and you may spend mana as though it were mana of any type to cast that spell";
    }

    private HostageTakerExileEffect(final HostageTakerExileEffect effect) {
        super(effect);
    }

    @Override
    public HostageTakerExileEffect copy() {
        return new HostageTakerExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent card = game.getPermanent(getTargetPointer().getFirst(game, source));
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        if (permanent == null || card == null) {
            return false;
        }
        Player controller = game.getPlayer(card.getControllerId());
        if (controller == null) {
            return false;
        }
        // move card to exile
        UUID exileId = CardUtil.getCardExileZoneId(game, source);
        controller.moveCardToExileWithInfo(card, exileId, permanent.getIdName(), source, game, Zone.BATTLEFIELD, true);
        // allow to cast the card and you may spend mana as though it were mana of any color to cast it
        CardUtil.makeCardPlayable(game, source, card, Duration.Custom, true);
        game.addDelayedTriggeredAbility(new OnLeaveReturnExiledAbility(), source);
        return true;
    }
}
