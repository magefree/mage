package mage.cards.k;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterSpell;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.card.CastFromZonePredicate;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.target.common.TargetOpponent;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KeeperOfSecrets extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("a spell from anywhere other than your hand");

    static {
        filter.add(Predicates.not(new CastFromZonePredicate(Zone.HAND)));
    }

    public KeeperOfSecrets(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{R}");

        this.subtype.add(SubType.DEMON);
        this.power = new MageInt(6);
        this.toughness = new MageInt(4);

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Symphony of Pain -- Whenever you cast a spell from anywhere other than your hand, Keeper of Secrets deals damage equal to that spell's mana value to target opponent.
        Ability ability = new SpellCastControllerTriggeredAbility(
                new DamageTargetEffect(KeeperOfSecretsValue.instance)
                        .setText("{this} deals damage equal to that spell's mana value to target opponent"),
                filter, false
        );
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability.withFlavorWord("Symphony of Pain"));
    }

    private KeeperOfSecrets(final KeeperOfSecrets card) {
        super(card);
    }

    @Override
    public KeeperOfSecrets copy() {
        return new KeeperOfSecrets(this);
    }
}

enum KeeperOfSecretsValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return Optional
                .ofNullable((Spell) effect.getValue("spellCast"))
                .filter(Objects::nonNull)
                .map(Spell::getManaValue)
                .orElse(0);
    }

    @Override
    public KeeperOfSecretsValue copy() {
        return this;
    }

    @Override
    public String getMessage() {
        return "";
    }

    @Override
    public String toString() {
        return "X";
    }
}
