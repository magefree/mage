package mage.cards.z;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.ReturnToHandChosenControlledPermanentCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.keyword.FlashAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterPermanentCard;
import mage.filter.predicate.permanent.UnblockedPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInGraveyard;
import mage.target.common.TargetControlledPermanent;
import mage.target.targetadjustment.DamagedPlayerControlsTargetAdjuster;

import java.util.UUID;

/**
 * @author TheElk801, notgreat
 */
public final class ZarethSanTheTrickster extends CardImpl {

    private static final FilterCard filterCardGraveyard
            = new FilterPermanentCard("permanent card from that player's graveyard");
    private static final FilterControlledPermanent filter = new FilterControlledPermanent(SubType.ROGUE, "an unblocked attacking Rogue you control");

    static {
        filter.add(UnblockedPredicate.instance);
    }

    public ZarethSanTheTrickster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // {2}{U}{B}, Return an unblocked attacking Rogue you control to its owner's hand: Put Zareth San, the Trickster from your hand onto the battlefield tapped and attacking.
        Ability ability = new SimpleActivatedAbility(
                Zone.HAND, new ZarethSanTheTricksterEffect(), new ManaCostsImpl<>("{2}{U}{B}")
        );
        ability.addCost(new ReturnToHandChosenControlledPermanentCost(new TargetControlledPermanent(filter)));
        this.addAbility(ability);

        // Whenever Zareth San deals combat damage to a player, you may put target permanent card from that player's graveyard onto the battlefield under your control.
        Ability ability2 = new DealsCombatDamageToAPlayerTriggeredAbility(new ReturnFromGraveyardToBattlefieldTargetEffect(), true, true);
        ability2.addTarget(new TargetCardInGraveyard(filterCardGraveyard));
        ability2.setTargetAdjuster(new DamagedPlayerControlsTargetAdjuster(true));
        this.addAbility(ability2);
    }

    private ZarethSanTheTrickster(final ZarethSanTheTrickster card) {
        super(card);
    }

    @Override
    public ZarethSanTheTrickster copy() {
        return new ZarethSanTheTrickster(this);
    }
}

class ZarethSanTheTricksterEffect extends OneShotEffect {

    ZarethSanTheTricksterEffect() {
        super(Outcome.Benefit);
        staticText = "Put {this} from your hand onto the battlefield tapped and attacking.";
    }

    private ZarethSanTheTricksterEffect(final ZarethSanTheTricksterEffect effect) {
        super(effect);
    }

    @Override
    public ZarethSanTheTricksterEffect copy() {
        return new ZarethSanTheTricksterEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Card card = controller.getHand().get(source.getSourceId(), game);
        if (card == null) {
            return true;
        }
        controller.moveCards(
                card, Zone.BATTLEFIELD, source, game, true,
                false, true, null
        );
        Permanent permanent = game.getPermanent(card.getId());
        if (permanent != null) {
            game.getCombat().addAttackingCreature(permanent.getId(), game);
        }
        return true;
    }
}
