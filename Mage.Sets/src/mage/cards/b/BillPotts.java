package mage.cards.b;

import mage.MageInt;
import mage.abilities.Abilities;
import mage.abilities.AbilitiesImpl;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.ActivateAbilityTriggeredAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.CopyStackObjectEffect;
import mage.abilities.keyword.DoctorsCompanionAbility;
import mage.abilities.meta.OrTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterSpell;
import mage.filter.FilterStackObject;
import mage.filter.common.FilterInstantOrSorcerySpell;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;
import mage.target.Target;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author padfoot
 */
public final class BillPotts extends CardImpl {

    private static final FilterSpell filterInstantOrSorcery = new FilterInstantOrSorcerySpell("an instant or sorcery spell that targets only {this}");
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

enum BillPottsPredicate implements ObjectSourcePlayerPredicate<StackObject> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<StackObject> input, Game game) {
        List<UUID> oneTargetList = Arrays.asList(input.getSourceId());
        return (makeStream(input, game).collect(Collectors.toList()).equals(oneTargetList));
    }

    private static final Stream<UUID> makeStream(ObjectSourcePlayer<StackObject> input, Game game) {
        Abilities<Ability> objectAbilities = new AbilitiesImpl<>();
        if (input.getObject() instanceof Spell) {
            objectAbilities.addAll(((Spell) input.getObject()).getSpellAbilities());
        } else {
            objectAbilities.add(input.getObject().getStackAbility());
        }
        return objectAbilities
                .stream()
                .map(Ability::getModes)
                .flatMap(m -> m.getSelectedModes().stream().map(m::get))
                .filter(Objects::nonNull)
                .map(Mode::getTargets)
                .flatMap(Collection::stream)
                .filter(t -> !t.isNotTarget())
                .map(Target::getTargets)
                .flatMap(Collection::stream)
                .filter(Objects::nonNull)
                .distinct();
    }
}
