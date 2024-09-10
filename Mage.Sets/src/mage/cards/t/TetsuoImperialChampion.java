package mage.cards.t;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.condition.common.EquippedSourceCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.cost.CastFromHandForFreeEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterCard;
import mage.filter.common.FilterInstantOrSorceryCard;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetAnyTarget;

import java.util.Objects;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TetsuoImperialChampion extends CardImpl {

    private static final Hint hint = new ValueHint(
            "Greatest mana value of attached equipment", TetsuoImperialChampionValue.instance
    );
    private static final FilterCard filter = new FilterInstantOrSorceryCard(
            "an instant or sorcery spell from your hand with mana value " +
                    "less than or equal to the highest mana value among Equipment attached to {this}"
    );

    static {
        filter.add(TetsuoImperialChampionPredicate.instance);
    }

    public TetsuoImperialChampion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{U}{B}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SAMURAI);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever Tetsuo, Imperial Champion attacks, if it's equipped, choose one --
        // * Tetsuo deals damage equal to the highest mana value among Equipment attached to it to any target.
        Ability ability = new ConditionalInterveningIfTriggeredAbility(
                new AttacksTriggeredAbility(new DamageTargetEffect(
                        TetsuoImperialChampionValue.instance, "it"
                ).setText("{this} deals damage equal to the highest mana value " +
                        "among Equipment attached to it to any target")
                ).setTriggerPhrase("Whenever {this} attacks, if it's equipped, "),
                EquippedSourceCondition.instance, null
        );
        ability.addTarget(new TargetAnyTarget());

        // * You may cast an instant or sorcery spell from your hand with mana value less than or equal to the highest mana value among Equipment attached to Tetsuo without paying its mana cost.
        ability.addMode(new Mode(new CastFromHandForFreeEffect(filter)));
        this.addAbility(ability);
    }

    private TetsuoImperialChampion(final TetsuoImperialChampion card) {
        super(card);
    }

    @Override
    public TetsuoImperialChampion copy() {
        return new TetsuoImperialChampion(this);
    }
}

enum TetsuoImperialChampionPredicate implements ObjectSourcePlayerPredicate<Card> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<Card> input, Game game) {
        return input
                .getObject()
                .getManaValue()
                <= TetsuoImperialChampionValue
                .instance
                .calculate(game, input.getSource(), null);
    }
}

enum TetsuoImperialChampionValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        Permanent permanent = sourceAbility.getSourcePermanentOrLKI(game);
        return permanent == null ? 0 : permanent
                .getAttachments()
                .stream()
                .map(game::getPermanent)
                .filter(Objects::nonNull)
                .filter(p -> p.hasSubtype(SubType.EQUIPMENT, game))
                .mapToInt(MageObject::getManaValue)
                .max()
                .orElse(0);
    }

    @Override
    public TetsuoImperialChampionValue copy() {
        return this;
    }

    @Override
    public String getMessage() {
        return "";
    }

    @Override
    public String toString() {
        return "1";
    }
}
