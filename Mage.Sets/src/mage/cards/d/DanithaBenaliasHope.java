package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.*;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.card.AuraCardCanAttachToPermanentId;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetCard;

import java.util.UUID;

/**
 *
 * @author weirddan455
 */
public final class DanithaBenaliasHope extends CardImpl {

    public DanithaBenaliasHope(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // When Danitha, Benalia's Hope enters the battlefield, you may put an Aura or Equipment card from your hand or graveyard onto the battlefield attached to Danitha.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DanithaBenaliasHopeEffect(), true));
    }

    private DanithaBenaliasHope(final DanithaBenaliasHope card) {
        super(card);
    }

    @Override
    public DanithaBenaliasHope copy() {
        return new DanithaBenaliasHope(this);
    }
}

class DanithaBenaliasHopeEffect extends OneShotEffect {

    public DanithaBenaliasHopeEffect() {
        super(Outcome.PutCardInPlay);
        this.staticText = "put an Aura or Equipment card from your hand or graveyard onto the battlefield attached to Danitha";
    }

    private DanithaBenaliasHopeEffect(final DanithaBenaliasHopeEffect effect) {
        super(effect);
    }

    @Override
    public DanithaBenaliasHopeEffect copy() {
        return new DanithaBenaliasHopeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Permanent sourcePermanent = source.getSourcePermanentIfItStillExists(game);
        UUID sourcePermanentId = sourcePermanent == null ? null : sourcePermanent.getId();
        String sourcePermanentName = sourcePermanent == null ? "" : sourcePermanent.getName();
        FilterCard filter = new FilterCard("an Aura or Equipment card");
        filter.add(Predicates.or(
                Predicates.and(SubType.AURA.getPredicate(), new AuraCardCanAttachToPermanentId(sourcePermanentId)),
                SubType.EQUIPMENT.getPredicate()
        ));
        Cards cards = new CardsImpl();
        cards.addAllCards(controller.getHand().getCards(filter, game));
        cards.addAllCards(controller.getGraveyard().getCards(filter, game));
        TargetCard target = new TargetCard(Zone.ALL, filter);
        target.withNotTarget(true);
        target.withChooseHint("to attach to " + sourcePermanentName);
        controller.choose(outcome, cards, target, source, game);
        Card card = game.getCard(target.getFirstTarget());
        if (card != null) {
            if (sourcePermanent != null) {
                game.getState().setValue("attachTo:" + card.getId(), sourcePermanent);
            }
            controller.moveCards(card, Zone.BATTLEFIELD, source, game);
            if (sourcePermanent != null) {
                sourcePermanent.addAttachment(card.getId(), source, game);
            }
        }
        return true;
    }
}
