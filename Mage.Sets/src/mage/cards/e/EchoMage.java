
package mage.cards.e;

import mage.MageInt;
import mage.abilities.Abilities;
import mage.abilities.AbilitiesImpl;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CopyTargetSpellEffect;
import mage.abilities.keyword.LevelUpAbility;
import mage.abilities.keyword.LevelerCardBuilder;
import mage.cards.CardSetInfo;
import mage.cards.LevelerCard;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.target.TargetSpell;

import java.util.UUID;

/**
 * @author North
 */
public final class EchoMage extends LevelerCard {

    public EchoMage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}{U}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);

        this.color.setBlue(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Level up {1}{U}
        this.addAbility(new LevelUpAbility(new ManaCostsImpl<>("{1}{U}")));
        // LEVEL 2-3
        // 2/4
        // {U}{U}, {tap}: Copy target instant or sorcery spell. You may choose new targets for the copy.
        Abilities<Ability> abilities1 = new AbilitiesImpl<>();
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CopyTargetSpellEffect(), new ManaCostsImpl<>("{U}{U}"));
        ability.addTarget(new TargetSpell(StaticFilters.FILTER_SPELL_INSTANT_OR_SORCERY));
        ability.addCost(new TapSourceCost());
        abilities1.add(ability);
        // LEVEL 4+
        // 2/5
        // {U}{U}, {tap}: Copy target instant or sorcery spell twice. You may choose new targets for the copies.
        Abilities<Ability> abilities2 = new AbilitiesImpl<>();
        ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new EchoMageEffect(), new ManaCostsImpl<>("{U}{U}"));
        ability.addTarget(new TargetSpell(StaticFilters.FILTER_SPELL_INSTANT_OR_SORCERY));
        ability.addCost(new TapSourceCost());
        abilities2.add(ability);

        this.addAbilities(LevelerCardBuilder.construct(
                new LevelerCardBuilder.LevelAbility(2, 3, abilities1, 2, 4),
                new LevelerCardBuilder.LevelAbility(4, -1, abilities2, 2, 5)));
        setMaxLevelCounters(4);
    }

    private EchoMage(final EchoMage card) {
        super(card);
    }

    @Override
    public EchoMage copy() {
        return new EchoMage(this);
    }
}

class EchoMageEffect extends OneShotEffect {

    public EchoMageEffect() {
        super(Outcome.Copy);
        this.staticText = "Copy target instant or sorcery spell twice. You may choose new targets for the copies";
    }

    public EchoMageEffect(final EchoMageEffect effect) {
        super(effect);
    }

    @Override
    public EchoMageEffect copy() {
        return new EchoMageEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Spell spell = game.getStack().getSpell(targetPointer.getFirst(game, source));
        if (spell != null) {
            spell.createCopyOnStack(game, source, source.getControllerId(), true, 2);
            return true;
        }
        return false;
    }
}
