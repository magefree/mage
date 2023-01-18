package mage.cards.t;

import mage.ConditionalMana;
import mage.MageObject;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.CorruptedCondition;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.decorator.ConditionalActivatedAbility;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.decorator.ConditionalGainActivatedAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.mana.ColorlessManaAbility;
import mage.abilities.mana.ConditionalAnyColorManaAbility;
import mage.abilities.mana.builder.ConditionalManaBuilder;
import mage.abilities.mana.conditional.CreatureCastManaCondition;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.filter.predicate.mageobject.ToughnessPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

import java.util.Set;
import java.util.UUID;

/**
 *
 * @author AhmadYProjects
 */
public final class TheSeedcore extends CardImpl {
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("1/1 creature");


    static {
        filter.add(new PowerPredicate(ComparisonType.EQUAL_TO, 1));
        filter.add(new ToughnessPredicate(ComparisonType.EQUAL_TO, 1));
    }

    public TheSeedcore(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        this.subtype.add(SubType.SPHERE);

        // {T}: Add {C}.
        this.addAbility(new ColorlessManaAbility());
        // {T}: Add one mana of any color. Spend this mana only to cast Phyrexian creature spells.
        this.addAbility(new ConditionalAnyColorManaAbility(new TapSourceCost(), 1, new TheSeedcoreManaBuilder(), true));
        // Corrupted -- {T}: Target 1/1 creature gets +2/+1 until end of turn. Activate only if an opponent has three or more poison counters.
       /* Effect effect = new BoostTargetEffect(2,1,Duration.EndOfTurn);
        Ability ability = new ConditionalActivatedAbility(
                Zone.BATTLEFIELD,
                effect,
                new TapSourceCost(),
                new TheSeedcoreManaCondition()
                );
        ability.addTarget(new TargetCreaturePermanent(filter));
        this.addAbility(ability);
    }*/
        Ability ability = new ConditionalGainActivatedAbility(Zone.BATTLEFIELD,
                new BoostTargetEffect(2, 1, Duration.EndOfTurn),
                new TapSourceCost(),
                CorruptedCondition.instance,
                "Activate only if an opponent has three or more poison counters.").setAbilityWord(AbilityWord.CORRUPTED).addHint(CorruptedCondition.getHint());
        ability.addTarget(new TargetCreaturePermanent(filter));
        this.addAbility(ability);
    }

    private TheSeedcore(final TheSeedcore card) {
        super(card);
    }

    @Override
    public TheSeedcore copy() {
        return new TheSeedcore(this);
    }
}
class TheSeedcoreManaBuilder extends ConditionalManaBuilder {

    @Override
    public ConditionalMana build(Object... options) {
        this.mana.setFlag(true); // indicates that the mana is from second ability
        return new mage.cards.t.TheSeedcoreConditionalMana(this.mana);
    }

    @Override
    public String getRule() {
        return "Spend this mana only to cast a Phyrexian creature spell.";
    }
}

class TheSeedcoreConditionalMana extends ConditionalMana {

    TheSeedcoreConditionalMana(Mana mana) {
        super(mana);
        staticText = "Spend this mana only to cast a Phyrexian creature spell.";
        addCondition(new mage.cards.t.TheSeedcoreManaCondition());
    }
}

class TheSeedcoreManaCondition extends CreatureCastManaCondition {

    @Override
    public boolean apply(Game game, Ability source, UUID manaProducer, Cost costToPay) {
        if (super.apply(game, source)) {
            MageObject object = game.getObject(source);
            if (object != null && object.hasSubtype(SubType.PHYREXIAN, game)
                    && object.isCreature(game)) {
                return true;
            }
        }
        return false;
    }
}

class TheSeedcoreCondition implements Condition {

    @Override
    public boolean apply(Game game, Ability source) {
        Set<UUID> opponents = game.getOpponents(source.getControllerId());
        for (UUID opponentUuid : opponents) {
            Player opponent = game.getPlayer(opponentUuid);
            if (opponent != null
                    && opponent.isInGame()
                    && opponent.getCounters().getCount(CounterType.POISON) >= 3) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "An opponent has three or more poison counters.";
    }
}