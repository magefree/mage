package mage.cards.v;

import java.util.UUID;

import mage.ConditionalMana;
import mage.MageObject;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostAdjuster;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.mana.ColorlessManaAbility;
import mage.abilities.mana.ConditionalAnyColorManaAbility;
import mage.abilities.mana.builder.ConditionalManaBuilder;
import mage.abilities.mana.conditional.ManaCondition;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.game.permanent.token.BloodToken;
import mage.players.Player;
import mage.util.CardUtil;

/**
 *
 * @author weirddan455
 */
public final class VoldarenEstate extends CardImpl {

    public VoldarenEstate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // {T}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        // {T}, Pay 1 life: Add one mana of any color. Spend this mana only to cast a Vampire spell.
        Ability ability = new ConditionalAnyColorManaAbility(new TapSourceCost(), 1, new VoldarenEstateManaBuilder(), true);
        ability.addCost(new PayLifeCost(1));
        this.addAbility(ability);

        // {5}, {T}: Create a Blood token. This ability costs {1} less to activate for each Vampire you control.
        ability = new SimpleActivatedAbility(new CreateTokenEffect(new BloodToken()
            ).setText("Create a Blood token. This ability costs {1} less to activate for each Vampire you control"),
        new GenericManaCost(5));
        ability.addCost(new TapSourceCost());
        ability.setCostAdjuster(VoldarenEstateCostAdjuster.instance);
        ability.addHint(VoldarenEstateCostAdjuster.getHint());
        this.addAbility(ability);
    }

    private VoldarenEstate(final VoldarenEstate card) {
        super(card);
    }

    @Override
    public VoldarenEstate copy() {
        return new VoldarenEstate(this);
    }
}

class VoldarenEstateManaBuilder extends ConditionalManaBuilder {

    @Override
    public ConditionalManaBuilder setMana(Mana mana, Ability source, Game game) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source);
        if (controller != null && sourceObject != null && mana.getAny() == 0) {
            game.informPlayers(controller.getLogName() + " produces " + mana.toString() + " with " + sourceObject.getLogName()
                    + " (can only be spent to cast a Vampire spell");
        }
        return super.setMana(mana, source, game);
    }

    @Override
    public ConditionalMana build(Object... options) {
        return new VoldarenEstateConditionalMana(this.mana);
    }

    @Override
    public String getRule() {
        return "Spend this mana only to cast a Vampire spell";
    }
}

class VoldarenEstateConditionalMana extends ConditionalMana {

    public VoldarenEstateConditionalMana(Mana mana) {
        super(mana);
        staticText = "Spend this mana only to cast a Vampire spell";
        addCondition(new VoldarenEstateManaCondition());
    }
}

class VoldarenEstateManaCondition extends ManaCondition implements Condition {

    @Override
    public boolean apply(Game game, Ability source) {
        if (source instanceof SpellAbility) {
            MageObject object = game.getObject(source);
            return object != null && object.hasSubtype(SubType.VAMPIRE, game);
        }
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source, UUID originalId, Cost costToPay) {
        return apply(game, source);
    }
}

enum VoldarenEstateCostAdjuster implements CostAdjuster {
    instance;

    private static final FilterControlledPermanent filter = new FilterControlledPermanent(SubType.VAMPIRE);
    private static final DynamicValue vampireCount = new PermanentsOnBattlefieldCount(filter);
    private static final Hint hint = new ValueHint("Vampires you control", vampireCount);

    public static Hint getHint() {
        return hint;
    }

    @Override
    public void adjustCosts(Ability ability, Game game) {
        CardUtil.reduceCost(ability, vampireCount.calculate(game, ability, null));
    }
}
