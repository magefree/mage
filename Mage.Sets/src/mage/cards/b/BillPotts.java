package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.MageItem;
import mage.abilities.common.ActivateAbilityTriggeredAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.CopyStackObjectEffect;
import mage.abilities.meta.OrTriggeredAbility;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.abilities.keyword.DoctorsCompanionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.constants.Zone;
import mage.filter.common.FilterActivatedOrTriggeredAbility;
import mage.filter.common.FilterInstantOrSorcerySpell;
import mage.filter.FilterSpell;
import mage.filter.FilterStackObject;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;
import mage.game.stack.StackObject;
import mage.target.Target;

import java.util.Collection;
import java.util.Objects;
import java.util.stream.Stream;

/**
 *
 * @author padfoot 
 */
public final class BillPotts extends CardImpl {

    private static final FilterSpell filterInstantOrSorcery = new FilterInstantOrSorcerySpell("an instant or sorcery that targets only {this}");
    private static final FilterStackObject filterAbility = new FilterStackObject("an ability that targets only {this}");

    static {
        filterInstantOrSorcery.add(BillPottsPredicate.instance);
        filterAbility.add(BillPottsPredicate.instance);
    }

    public BillPotts(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Whenever you cast an instant or sorcery spell that targets only Bill Potts or activate an ability that targets only Bill Potts, copy that spell or ability. You may choose new targets for the copy. This ability triggers only once each turn.
	this.addAbility(new OrTriggeredAbility(
	        Zone.BATTLEFIELD,
		new CopyStackObjectEffect("that spell or ability"),
		false,
		"",
		new SpellCastControllerTriggeredAbility(
			null, 
			filterInstantOrSorcery, 
			false, 
			SetTargetPointer.SPELL
		),
		new ActivateAbilityTriggeredAbility(
			null, 
			filterAbility, 
			SetTargetPointer.SPELL
		)
        ).setTriggersLimitEachTurn(1));

	// Doctor's companion
        this.addAbility(DoctorsCompanionAbility.getInstance());

    }

    private BillPotts(final BillPotts card) {
        super(card);
    }

    @Override
    public BillPotts copy() {
        return new BillPotts(this);
    }
}

// copied from Silver Wyvern.
enum BillPottsPredicate implements ObjectSourcePlayerPredicate<StackObject> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<StackObject> input, Game game) {
        return makeStream(input, game).anyMatch(input.getSourceId()::equals)
                && makeStream(input, game).allMatch(input.getSourceId()::equals);
    }

    private static final Stream<UUID> makeStream(ObjectSourcePlayer<StackObject> input, Game game) {
        return input.getObject()
                .getStackAbility()
                .getTargets()
                .stream()
                .map(Target::getTargets)
                .flatMap(Collection::stream)
                .map(game::getPermanent)
                .filter(Objects::nonNull)
                .map(MageItem::getId);
    }
}
