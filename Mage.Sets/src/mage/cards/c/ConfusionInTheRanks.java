
package mage.cards.c;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.ExchangeControlTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SetTargetPointer;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;

/**
 *
 * @author anonymous
 */
public final class ConfusionInTheRanks extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("an artifact, creature, or enchantment");

    static {
        filter.add(Predicates.or(
                new CardTypePredicate(CardType.ARTIFACT),
                new CardTypePredicate(CardType.CREATURE),
                new CardTypePredicate(CardType.ENCHANTMENT)
        ));
    }
    private final UUID originalId;

    public ConfusionInTheRanks(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{R}{R}");

        // Whenever an artifact, creature, or enchantment enters the battlefield, its controller chooses target permanent another player controls that shares a card type with it. Exchange control of those permanents.
        Ability ability = new EntersBattlefieldAllTriggeredAbility(
                Zone.BATTLEFIELD,
                new ExchangeControlTargetEffect(
                        Duration.EndOfGame,
                        "its controller chooses target permanent "
                        + "another player controls that shares a card type with it. "
                        + "Exchange control of those permanents"
                ),
                filter, false, SetTargetPointer.PERMANENT, null
        );
        ability.addTarget(new TargetPermanent());
        originalId = ability.getOriginalId();
        this.addAbility(ability);
    }

    public ConfusionInTheRanks(final ConfusionInTheRanks card) {
        super(card);
        this.originalId = card.originalId;
    }

    @Override
    public void adjustTargets(Ability ability, Game game) {
        if (ability.getOriginalId().equals(originalId)) {
            UUID enteringPermanentId = null;
            for (Effect effect : ability.getEffects()) {
                enteringPermanentId = effect.getTargetPointer().getFirst(game, ability);
            }
            if (enteringPermanentId == null) {
                return;
            }
            Permanent enteringPermanent = game.getPermanent(enteringPermanentId);
            if (enteringPermanent == null) {
                return;
            }
            ability.getTargets().clear();
            FilterPermanent filterTarget = new FilterPermanent();
            String message = "";
            filterTarget.add(Predicates.not(new ControllerIdPredicate(enteringPermanent.getControllerId())));
            Set<CardTypePredicate> cardTypesPredicates = new HashSet<>(1);
            for (CardType cardTypeEntering : enteringPermanent.getCardType()) {
                cardTypesPredicates.add(new CardTypePredicate(cardTypeEntering));
                if (!message.isEmpty()) {
                    message += "or ";
                }
                message += cardTypeEntering.toString().toLowerCase(Locale.ENGLISH) + ' ';
            }
            filterTarget.add(Predicates.or(cardTypesPredicates));
            message += "you don't control";
            filterTarget.setMessage(message);
            TargetPermanent target = new TargetPermanent(filterTarget);
            target.setTargetController(enteringPermanent.getControllerId());
            ability.getTargets().add(target);
        }
    }

    @Override
    public ConfusionInTheRanks copy() {
        return new ConfusionInTheRanks(this);
    }
}
