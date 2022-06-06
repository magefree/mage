
package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.target.Target;
import mage.target.TargetSpell;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class MeletisCharlatan extends CardImpl {

    public MeletisCharlatan(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // {2}{U}, {T}: The controller of target instant or sorcery spell copies it. That player may choose new targets for the copy.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new MeletisCharlatanCopyTargetSpellEffect(), new ManaCostsImpl<>("{2}{U}"));
        ability.addCost(new TapSourceCost());
        Target target = new TargetSpell(StaticFilters.FILTER_SPELL_INSTANT_OR_SORCERY);
        ability.addTarget(target);
        this.addAbility(ability);
    }

    private MeletisCharlatan(final MeletisCharlatan card) {
        super(card);
    }

    @Override
    public MeletisCharlatan copy() {
        return new MeletisCharlatan(this);
    }
}

class MeletisCharlatanCopyTargetSpellEffect extends OneShotEffect {

    public MeletisCharlatanCopyTargetSpellEffect() {
        super(Outcome.Copy);
        staticText = "The controller of target instant or sorcery spell copies it. That player may choose new targets for the copy";
    }

    public MeletisCharlatanCopyTargetSpellEffect(final MeletisCharlatanCopyTargetSpellEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Spell spell = game.getStack().getSpell(targetPointer.getFirst(game, source));
        if (spell != null) {
            spell.createCopyOnStack(game, source, spell.getControllerId(), true);
            return true;
        }
        return false;
    }

    @Override
    public MeletisCharlatanCopyTargetSpellEffect copy() {
        return new MeletisCharlatanCopyTargetSpellEffect(this);
    }

}
