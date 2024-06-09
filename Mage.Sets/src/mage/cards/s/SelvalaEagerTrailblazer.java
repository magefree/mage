package mage.cards.s;

import mage.MageInt;
import mage.MageObject;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.hint.common.CovenHint;
import mage.abilities.keyword.VigilanceAbility;
import mage.abilities.mana.DynamicManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.token.MercenaryToken;

import java.util.Objects;
import java.util.UUID;

/**
 * @author Susucr
 */
public final class SelvalaEagerTrailblazer extends CardImpl {

    public SelvalaEagerTrailblazer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.SCOUT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Whenever you cast a creature spell, create a 1/1 red Mercenary creature token with "{T}: Target creature you control gets +1/+0 until end of turn. Activate only as a sorcery."
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new CreateTokenEffect(new MercenaryToken()),
                StaticFilters.FILTER_SPELL_A_CREATURE, false
        ));

        // {T}: Choose a color. Add one mana of that color for each different power among creatures you control.
        this.addAbility(new DynamicManaAbility(
                Mana.AnyMana(1), SelvalaEagerTrailblazerValue.instance, new TapSourceCost(),
                "Choose a color. Add one mana of that color for each different power among creatures you control",
                true
        ).addHint(CovenHint.instance));
    }

    private SelvalaEagerTrailblazer(final SelvalaEagerTrailblazer card) {
        super(card);
    }

    @Override
    public SelvalaEagerTrailblazer copy() {
        return new SelvalaEagerTrailblazer(this);
    }
}

enum SelvalaEagerTrailblazerValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return game
                .getBattlefield()
                .getActivePermanents(
                        StaticFilters.FILTER_CONTROLLED_CREATURE,
                        sourceAbility.getControllerId(), sourceAbility, game
                )
                .stream()
                .filter(Objects::nonNull)
                .map(MageObject::getPower)
                .mapToInt(MageInt::getValue)
                .distinct()
                .map(x -> 1)
                .sum();
    }

    @Override
    public SelvalaEagerTrailblazerValue copy() {
        return this;
    }

    @Override
    public String getMessage() {
        return "different power among creatures you control";
    }

    @Override
    public String toString() {
        return "1";
    }
}
