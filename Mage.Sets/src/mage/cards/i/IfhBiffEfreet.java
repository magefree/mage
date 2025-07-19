
package mage.cards.i;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageAllEffect;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class IfhBiffEfreet extends CardImpl {

    public IfhBiffEfreet(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{G}");
        this.subtype.add(SubType.EFREET);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // {G}: Ifh-Biff Efreet deals 1 damage to each creature with flying and each player. Any player may activate this ability.
        SimpleActivatedAbility ability = new SimpleActivatedAbility(new DamageAllEffect(1, StaticFilters.FILTER_CREATURE_FLYING), new ManaCostsImpl<>("{G}"));
        Effect effect = new DamagePlayersEffect(1);
        effect.setText("and each player");
        ability.addEffect(effect);
        ability.setMayActivate(TargetController.ANY);
        ability.addEffect(new InfoEffect("Any player may activate this ability"));
        this.addAbility(ability);
    }

    private IfhBiffEfreet(final IfhBiffEfreet card) {
        super(card);
    }

    @Override
    public IfhBiffEfreet copy() {
        return new IfhBiffEfreet(this);
    }
}
