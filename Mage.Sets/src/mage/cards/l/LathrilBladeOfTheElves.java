package mage.cards.l;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.permanent.UntappedPredicate;
import mage.game.Game;
import mage.game.permanent.token.ElfToken;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LathrilBladeOfTheElves extends CardImpl {

    private static final FilterControlledPermanent filter
            = new FilterControlledPermanent(SubType.ELF, "untapped Elves you control");

    static {
        filter.add(UntappedPredicate.instance);
    }

    public LathrilBladeOfTheElves(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{G}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.NOBLE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Menace
        this.addAbility(new MenaceAbility());

        // Whenever Lathril, Blade of the Elves deals combat damage to a player, create that many 1/1 green Elf Warrior creature tokens.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new CreateTokenEffect(
                new ElfToken(), LathrilBladeOfTheElvesValue.instance
        ).setText("create that many 1/1 green Elf Warrior creature tokens"), false, true));

        // {T}, Tap ten untapped Elves you control: Each opponent loses 10 life and you gain 10 life.
        Ability ability = new SimpleActivatedAbility(new LoseLifeOpponentsEffect(10), new TapSourceCost());
        ability.addEffect(new GainLifeEffect(10).concatBy("and"));
        ability.addCost(new TapTargetCost(new TargetControlledPermanent(10, filter)));
        this.addAbility(ability);
    }

    private LathrilBladeOfTheElves(final LathrilBladeOfTheElves card) {
        super(card);
    }

    @Override
    public LathrilBladeOfTheElves copy() {
        return new LathrilBladeOfTheElves(this);
    }
}

enum LathrilBladeOfTheElvesValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return (Integer) effect.getValue("damage");
    }

    @Override
    public LathrilBladeOfTheElvesValue copy() {
        return instance;
    }

    @Override
    public String getMessage() {
        return "";
    }
}