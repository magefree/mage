package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.constants.*;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.card.AuraCardCanAttachToPermanentId;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInHand;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author weirddan455
 */
public final class DanithaBenaliasHope extends CardImpl {

    public DanithaBenaliasHope(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{W}");

        this.addSuperType(SuperType.LEGENDARY);
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
        FilterCard filter = new FilterCard("an Aura or Equipment card");
        filter.add(Predicates.or(
                Predicates.and(SubType.AURA.getPredicate(), new AuraCardCanAttachToPermanentId(sourcePermanentId)),
                SubType.EQUIPMENT.getPredicate()
        ));
        TargetCard target;
        if (controller.chooseUse(outcome, "Look in Hand or Graveyard?", null, "Hand", "Graveyard", source, game)) {
            target = new TargetCardInHand(filter);
        } else {
            target = new TargetCardInYourGraveyard(filter);
        }
        target.setNotTarget(true);
        if (target.canChoose(controller.getId(), source, game)) {
            controller.chooseTarget(outcome, target, source, game);
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
        }
        return true;
    }
}
