
package mage.cards.m;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.KickedCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.dynamicvalue.common.MultikickerCount;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.keyword.MultikickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author jeffwadsworth
 *
 */
public final class MarshalsAnthem extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Creatures you control");
    private static final FilterCard filterCard = new FilterCard("creature card in your graveyard");

    static {
        filter.add(new ControllerPredicate(TargetController.YOU));
        filterCard.add(new CardTypePredicate(CardType.CREATURE));
    }

    private final UUID originalId;

    public MarshalsAnthem(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{W}{W}");

        // Multikicker {1}{W}
        this.addAbility(new MultikickerAbility("{1}{W}"));

        // Creatures you control get +1/+1.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostAllEffect(1, 1, Duration.WhileOnBattlefield, filter, false)));

        // When Marshal's Anthem enters the battlefield, return up to X target creature cards from your graveyard to the battlefield, where X is the number of times Marshal's Anthem was kicked.
        //TODO this should always trigger, even if it wasn't kicked
        Ability ability = new ConditionalInterveningIfTriggeredAbility(
                new EntersBattlefieldTriggeredAbility(new ReturnFromGraveyardToBattlefieldTargetEffect(), false),
                KickedCondition.instance,
                "When {this} enters the battlefield, return up to X target creature cards from your graveyard to the battlefield, where X is the number of times {this} was kicked.");
        originalId = ability.getOriginalId();
        this.addAbility(ability);

    }

    @Override
    public void adjustTargets(Ability ability, Game game) {
        if (ability.getOriginalId().equals(originalId)) {
            ability.getTargets().clear();
            int numbTargets = new MultikickerCount().calculate(game, ability, null);
            if (numbTargets > 0) {
                ability.addTarget(new TargetCardInYourGraveyard(0, numbTargets, filterCard));
            }
        }
    }

    public MarshalsAnthem(final MarshalsAnthem card) {
        super(card);
        this.originalId = card.originalId;
    }

    @Override
    public MarshalsAnthem copy() {
        return new MarshalsAnthem(this);
    }
}
