
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.ExileSourceFromGraveCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DrawCardAllEffect;
import mage.abilities.effects.common.discard.DiscardHandAllEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

/**
 *
 * @author fireshoes
 */
public final class RunehornHellkite extends CardImpl {

    public RunehornHellkite(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{R}");

        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // {5}{R}, Exile Runehorn Hellkite from your graveyard: Each player discards their hand, then draws seven cards.
        Effect effect = new DrawCardAllEffect(7);
        effect.setText(", then draws seven cards");
        Ability ability = new SimpleActivatedAbility(Zone.GRAVEYARD, new DiscardHandAllEffect(), new ManaCostsImpl<>("{5}{R}"));
        ability.addCost(new ExileSourceFromGraveCost());
        ability.addEffect(effect);
        this.addAbility(ability);
    }

    private RunehornHellkite(final RunehornHellkite card) {
        super(card);
    }

    @Override
    public RunehornHellkite copy() {
        return new RunehornHellkite(this);
    }
}
