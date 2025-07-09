package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.condition.common.EquippedSourceCondition;
import mage.abilities.dynamicvalue.common.GreatestAmongPermanentsValue;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.cost.CastFromHandForFreeEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterInstantOrSorceryCard;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.filter.predicate.permanent.AttachedToSourcePredicate;
import mage.game.Game;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TetsuoImperialChampion extends CardImpl {

    private static final FilterPermanent filterEquipment = new FilterPermanent("Equipment attached to {this}");

    static {
        filterEquipment.add(SubType.EQUIPMENT.getPredicate());
        filterEquipment.add(AttachedToSourcePredicate.instance);
    }

    static final GreatestAmongPermanentsValue xValue = new GreatestAmongPermanentsValue(GreatestAmongPermanentsValue.Quality.ManaValue, filterEquipment);
    private static final FilterCard filter = new FilterInstantOrSorceryCard(
            "an instant or sorcery spell from your hand with mana value " +
                    "less than or equal to the greatest mana value among Equipment attached to {this}"
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
        // * Tetsuo deals damage equal to the greatest mana value among Equipment attached to it to any target.
        Ability ability = new AttacksTriggeredAbility(
                new DamageTargetEffect(xValue, "it")
                        .setText("{this} deals damage equal to the greatest mana value " +
                                "among Equipment attached to it to any target")
        ).withInterveningIf(EquippedSourceCondition.instance);
        ability.addTarget(new TargetAnyTarget());

        // * You may cast an instant or sorcery spell from your hand with mana value less than or equal to the greatest mana value among Equipment attached to Tetsuo without paying its mana cost.
        ability.addMode(new Mode(new CastFromHandForFreeEffect(filter)));
        this.addAbility(ability.addHint(xValue.getHint()));
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
        int value = TetsuoImperialChampion.xValue.calculate(game, input.getSource(), null);
        return input.getObject().getManaValue() <= value;
    }
}
