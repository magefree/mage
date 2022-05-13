package mage.cards.d;

import mage.ConditionalMana;
import mage.MageInt;
import mage.MageObject;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.mana.ConditionalColorlessManaAbility;
import mage.abilities.mana.builder.ConditionalManaBuilder;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.EquippedPredicate;
import mage.game.Game;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DalakosCrafterOfWonders extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("equipped creatures");

    static {
        filter.add(EquippedPredicate.instance);
    }

    public DalakosCrafterOfWonders(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}{R}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // {T}: Add {C}{C}. Spend this mana only to cast artifact spells or activate abilities of artifacts.
        this.addAbility(new ConditionalColorlessManaAbility(
                new TapSourceCost(), 2, new DalakosCrafterOfWondersManaBuilder()
        ));

        // Equipped creatures you control have flying and haste.
        Ability ability = new SimpleStaticAbility(new GainAbilityControlledEffect(
                FlyingAbility.getInstance(), Duration.WhileOnBattlefield, filter
        ));
        ability.addEffect(new GainAbilityControlledEffect(
                HasteAbility.getInstance(), Duration.WhileOnBattlefield, filter
        ).setText("and haste"));
        this.addAbility(ability);
    }

    private DalakosCrafterOfWonders(final DalakosCrafterOfWonders card) {
        super(card);
    }

    @Override
    public DalakosCrafterOfWonders copy() {
        return new DalakosCrafterOfWonders(this);
    }
}

class DalakosCrafterOfWondersManaBuilder extends ConditionalManaBuilder {

    @Override
    public ConditionalMana build(Object... options) {
        return new DalakosCrafterOfWondersConditionalMana(this.mana);
    }

    @Override
    public String getRule() {
        return "Spend this mana only to cast artifact spells or activate abilities of artifacts";
    }
}

class DalakosCrafterOfWondersConditionalMana extends ConditionalMana {

    DalakosCrafterOfWondersConditionalMana(Mana mana) {
        super(mana);
        addCondition(DalakosCrafterOfWondersCondition.instance);
    }
}

enum DalakosCrafterOfWondersCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        MageObject object = game.getObject(source);
        return object != null && object.isArtifact(game);
    }
}