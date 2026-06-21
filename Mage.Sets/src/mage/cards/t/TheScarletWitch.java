package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.SourcePermanentPowerValue;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.CostModificationType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.common.FilterInstantOrSorceryCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.util.CardUtil;

/**
 *
 * @author muz
 */
public final class TheScarletWitch extends CardImpl {

    public TheScarletWitch(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.MUTANT);
        this.subtype.add(SubType.WARLOCK);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Instant and sorcery spells you cast with mana value 4 or greater cost {X} less to cast, where X is The Scarlet Witch's power.
        this.addAbility(new SimpleStaticAbility(new TheScarletWitchEffect()));
    }

    private TheScarletWitch(final TheScarletWitch card) {
        super(card);
    }

    @Override
    public TheScarletWitch copy() {
        return new TheScarletWitch(this);
    }
}

class TheScarletWitchEffect extends CostModificationEffectImpl {

    private static final FilterInstantOrSorceryCard filter = new FilterInstantOrSorceryCard();

    static {
        filter.add(new ManaValuePredicate(ComparisonType.OR_GREATER, 4));
    }

    TheScarletWitchEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit, CostModificationType.REDUCE_COST);
        staticText = "Instant and sorcery spells you cast with mana value 4 or greater cost " +
            "{X} less to cast, where X is {this}'s power";
    }

    private TheScarletWitchEffect(final TheScarletWitchEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        int power = SourcePermanentPowerValue.NOT_NEGATIVE.calculate(game, source, this);
        CardUtil.reduceCost(abilityToModify, power);
        return true;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        if (!(abilityToModify instanceof SpellAbility) || !abilityToModify.isControlledBy(source.getControllerId())) {
            return false;
        }
        return filter.match(
            ((SpellAbility) abilityToModify).getCharacteristics(game),
            source.getControllerId(),
            source, game
        );
    }

    @Override
    public TheScarletWitchEffect copy() {
        return new TheScarletWitchEffect(this);
    }
}
