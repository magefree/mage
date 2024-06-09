package mage.cards.d;

import java.util.Arrays;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BecomesSubtypeAllEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.MenaceAbility;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterSpell;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.game.permanent.token.TreasureToken;

/**
 * Don Andres, the Renegade {1}{U}{B}{R}
 * Legendary Creature - Vampire Pirate
 * Each creature you control but don’t own gets +2/+2, has menace and deathtouch, and is a Pirate in addition to its other types.
 * Whenever you cast a noncreature spell you don’t own, create two tapped Treasure tokens.
 *
 * @author DominionSpy
 */
public final class DonAndresTheRenegade extends CardImpl {

    private static final FilterCreaturePermanent creatureFilter =
            new FilterCreaturePermanent();

    static {
        creatureFilter.add(TargetController.YOU.getControllerPredicate());
        creatureFilter.add(TargetController.NOT_YOU.getOwnerPredicate());
    }

    private static final FilterSpell spellFilter =
            new FilterSpell("a noncreature spell you don't own");

    static {
        spellFilter.add(Predicates.not(CardType.CREATURE.getPredicate()));
        spellFilter.add(TargetController.NOT_YOU.getOwnerPredicate());
    }

    public DonAndresTheRenegade(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}{B}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.PIRATE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Each creature you control but don't own gets +2/+2, has menace and deathtouch, and is a Pirate in addition to its other types.
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD,
                new BoostControlledEffect(2, 2, Duration.WhileOnBattlefield, creatureFilter, false)
                        .setText("Each creature you control but don't own gets +2/+2"));
        ability.addEffect(new GainAbilityControlledEffect(
                new MenaceAbility(), Duration.WhileOnBattlefield, creatureFilter)
                .setText(", has menace"));
        ability.addEffect(new GainAbilityControlledEffect(
                DeathtouchAbility.getInstance(), Duration.WhileOnBattlefield, creatureFilter)
                .setText(" and deathtouch"));
        ability.addEffect(new BecomesSubtypeAllEffect(Duration.WhileOnBattlefield, Arrays.asList(SubType.PIRATE), creatureFilter, false)
                .setText(", and is a Pirate in addition to its other types."));
        this.addAbility(ability);

        // Whenever you cast a noncreature spell you don't own, create two tapped Treasure tokens.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new CreateTokenEffect(new TreasureToken(), 2, true), spellFilter, false));
    }

    private DonAndresTheRenegade(final DonAndresTheRenegade card) {
        super(card);
    }

    @Override
    public DonAndresTheRenegade copy() {
        return new DonAndresTheRenegade(this);
    }
}
