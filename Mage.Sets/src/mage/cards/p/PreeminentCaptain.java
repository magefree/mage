package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInHand;

/**
 *
 * @author Rafbill
 */
public final class PreeminentCaptain extends CardImpl {

    public PreeminentCaptain(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");
        this.subtype.add(SubType.KITHKIN);
        this.subtype.add(SubType.SOLDIER);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        this.addAbility(FirstStrikeAbility.getInstance());
        // Whenever Preeminent Captain attacks, you may put a Soldier creature
        // card from your hand onto the battlefield tapped and attacking.
        this.addAbility(new AttacksTriggeredAbility(new PreeminentCaptainEffect(), true));
    }

    public PreeminentCaptain(final PreeminentCaptain card) {
        super(card);
    }

    @Override
    public PreeminentCaptain copy() {
        return new PreeminentCaptain(this);
    }
}

class PreeminentCaptainEffect extends OneShotEffect {

    private static final FilterCreatureCard filter = new FilterCreatureCard("a soldier creature card");

    static {
        filter.add(new SubtypePredicate(SubType.SOLDIER));
    }

    public PreeminentCaptainEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "put a Soldier creature card from your hand onto the battlefield tapped and attacking";
    }

    public PreeminentCaptainEffect(final PreeminentCaptainEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        TargetCardInHand target = new TargetCardInHand(filter);
        if (target.canChoose(controller.getId(), game)
                && target.choose(getOutcome(), controller.getId(), source.getSourceId(), game)) {
            if (!target.getTargets().isEmpty()) {
                UUID cardId = target.getFirstTarget();
                Card card = controller.getHand().get(cardId, game);
                if (card != null) {
                    if (controller.moveCards(card, Zone.BATTLEFIELD, source, game, true, false, true, null)) {
                        Permanent permanent = game.getPermanent(card.getId());
                        if (permanent != null) {
                            game.getCombat().addAttackingCreature(permanent.getId(), game);
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public PreeminentCaptainEffect copy() {
        return new PreeminentCaptainEffect(this);
    }

}
