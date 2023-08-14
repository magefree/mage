package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardHandCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.combat.CantBeBlockedTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SubiraTulzidiCaravanner extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("another creature with power 2 or less");

    static {
        filter.add(new PowerPredicate(ComparisonType.FEWER_THAN, 3));
        filter.add(AnotherPredicate.instance);
    }

    public SubiraTulzidiCaravanner(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // {1}: Another target creature with power 2 or less can't be blocked this turn.
        Ability ability = new SimpleActivatedAbility(
                new CantBeBlockedTargetEffect(Duration.EndOfTurn)
                        .setText("another target creature with power 2 or less can't be blocked this turn"),
                new GenericManaCost(1)
        );
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);

        // {1}{R}, {T}, Discard your hand: Until end of turn, whenever a creature you control with power 2 or less deals combat damage to a player, draw a card.
        ability = new SimpleActivatedAbility(new CreateDelayedTriggeredAbilityEffect(
                new SubiraTulzidiCaravannerAbility()
        ), new ManaCostsImpl<>("{1}{R}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new DiscardHandCost());
        this.addAbility(ability);
    }

    private SubiraTulzidiCaravanner(final SubiraTulzidiCaravanner card) {
        super(card);
    }

    @Override
    public SubiraTulzidiCaravanner copy() {
        return new SubiraTulzidiCaravanner(this);
    }
}

class SubiraTulzidiCaravannerAbility extends DelayedTriggeredAbility {

    SubiraTulzidiCaravannerAbility() {
        super(new DrawCardSourceControllerEffect(1), Duration.EndOfTurn, false, false);
    }

    private SubiraTulzidiCaravannerAbility(final SubiraTulzidiCaravannerAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        DamagedPlayerEvent dEvent = (DamagedPlayerEvent) event;
        if (!dEvent.isCombatDamage()) {
            return false;
        }
        Permanent permanent = game.getPermanent(event.getSourceId());
        return permanent != null
                && permanent.isControlledBy(getControllerId())
                && permanent.isCreature(game)
                && permanent.getPower().getValue() <= 2;
    }

    @Override
    public SubiraTulzidiCaravannerAbility copy() {
        return new SubiraTulzidiCaravannerAbility(this);
    }

    @Override
    public String getRule() {
        return "Until end of turn, whenever a creature you control with power 2 or less deals combat damage to a player, draw a card.";
    }
}
