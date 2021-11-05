package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CopyTargetSpellEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.TargetController;
import mage.filter.FilterSpell;
import mage.filter.common.FilterInstantOrSorcerySpell;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.target.TargetSpell;
import mage.target.targetadjustment.TargetAdjuster;

/**
 *
 * @author TheElk801
 */
public final class LeagueGuildmage extends CardImpl {

    private static final FilterSpell filter = new FilterInstantOrSorcerySpell("instant or sorcery spell you control with mana value X");

    static {
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    public LeagueGuildmage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{U}{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {3}{U}, {T}: Draw a card.
        Ability ability = new SimpleActivatedAbility(
                new DrawCardSourceControllerEffect(1),
                new ManaCostsImpl("{3}{U}")
        );
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);

        // {X}{R}, {T}: Copy target instant or sorcery spell you control with converted mana cost X. You may choose new targets for the copy.
        ability = new SimpleActivatedAbility(
                new CopyTargetSpellEffect(),
                new ManaCostsImpl("{X}{R}")
        );
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetSpell(filter));
        ability.setTargetAdjuster(LeagueGuildmageAdjuster.instance);
        this.addAbility(ability);
    }

    private LeagueGuildmage(final LeagueGuildmage card) {
        super(card);
    }

    @Override
    public LeagueGuildmage copy() {
        return new LeagueGuildmage(this);
    }
}

enum LeagueGuildmageAdjuster implements TargetAdjuster {
    instance;

    @Override
    public void adjustTargets(Ability ability, Game game) {
        int xValue = ability.getManaCostsToPay().getX();
        FilterSpell spellFilter = new FilterInstantOrSorcerySpell("instant or sorcery you control with mana value " + xValue);
        spellFilter.add(TargetController.YOU.getControllerPredicate());
        spellFilter.add(new ManaValuePredicate(ComparisonType.EQUAL_TO, xValue));
        ability.getTargets().clear();
        ability.addTarget(new TargetSpell(spellFilter));
    }
}
