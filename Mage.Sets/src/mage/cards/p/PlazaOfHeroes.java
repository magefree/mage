package mage.cards.p;

import mage.ConditionalMana;
import mage.MageObject;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.ExileSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.HexproofAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.abilities.mana.AnyColorPermanentTypesManaAbility;
import mage.abilities.mana.ColorlessManaAbility;
import mage.abilities.mana.ConditionalAnyColorManaAbility;
import mage.abilities.mana.builder.ConditionalManaBuilder;
import mage.abilities.mana.conditional.ManaCondition;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PlazaOfHeroes extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("legendary creature");
    private static final FilterPermanent filter2 = new FilterControlledPermanent("legendary permanents");

    static {
        filter.add(SuperType.LEGENDARY.getPredicate());
        filter2.add(SuperType.LEGENDARY.getPredicate());
    }

    public PlazaOfHeroes(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // {T}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        // {T}: Add one mana of any color. Spend this mana only to cast a legendary spell.
        this.addAbility(new ConditionalAnyColorManaAbility(1, new PlazaOfHeroesManaBuilder()));

        // {T}: Add one mana of any color among legendary permanents you control.
        this.addAbility(new AnyColorPermanentTypesManaAbility(TargetController.YOU, filter2));

        // {3}, {T}, Exile Plaza of Heroes: Target legendary creature gains hexproof and indestructible until end of turn.
        Ability ability = new SimpleActivatedAbility(new GainAbilityTargetEffect(
                HexproofAbility.getInstance(), Duration.EndOfTurn
        ).setText("target legendary creature gains hexproof"), new GenericManaCost(3));
        ability.addEffect(new GainAbilityTargetEffect(
                IndestructibleAbility.getInstance(), Duration.EndOfTurn
        ).setText("and indestructible until end of turn"));
        ability.addTarget(new TargetPermanent(filter));
        ability.addCost(new TapSourceCost());
        ability.addCost(new ExileSourceCost());
        this.addAbility(ability);
    }

    private PlazaOfHeroes(final PlazaOfHeroes card) {
        super(card);
    }

    @Override
    public PlazaOfHeroes copy() {
        return new PlazaOfHeroes(this);
    }
}

class PlazaOfHeroesManaBuilder extends ConditionalManaBuilder {

    @Override
    public ConditionalMana build(Object... options) {
        return new PlazaOfHeroesConditionalMana(this.mana);
    }

    @Override
    public String getRule() {
        return "Spend this mana only to cast a legendary spell";
    }
}

class PlazaOfHeroesConditionalMana extends ConditionalMana {

    public PlazaOfHeroesConditionalMana(Mana mana) {
        super(mana);
        staticText = "Spend this mana only to cast a legendary spell";
        addCondition(new PlazaOfHeroesManaCondition());
    }
}

class PlazaOfHeroesManaCondition extends ManaCondition implements Condition {

    @Override
    public boolean apply(Game game, Ability source) {
        if (source instanceof SpellAbility) {
            MageObject object = game.getObject(source);
            if (object != null && object.isLegendary(game)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source, UUID originalId, Cost costsToPay) {
        return apply(game, source);
    }
}
