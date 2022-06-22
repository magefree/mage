
package mage.cards.g;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.ExileFromGraveCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreatureCard;
import mage.game.permanent.token.ZombieToken;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author escplan9 (Derek Monturo - dmontur1 at gmail dot com)
 */
public final class GrafHarvest extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("Zombies you control");

    static {
        filter.add(SubType.ZOMBIE.getPredicate());
    }

    public GrafHarvest(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{B}");

        // Zombies you control have menace.
        Effect effect = new GainAbilityAllEffect(new MenaceAbility(), Duration.WhileOnBattlefield, filter);
        effect.setText("Zombies you control have menace. <i>(They can't be blocked except by two or more creatures.)</i>");
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect));

        // {3}{B}, Exile a creature card from your graveyard: Create a 2/2 black Zombie creature token.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CreateTokenEffect(new ZombieToken()), new ManaCostsImpl<>("{3}{B}"));
        ability.addCost(new ExileFromGraveCost(new TargetCardInYourGraveyard(new FilterCreatureCard("a creature card from your graveyard"))));
        this.addAbility(ability);
    }

    private GrafHarvest(final GrafHarvest card) {
        super(card);
    }

    @Override
    public GrafHarvest copy() {
        return new GrafHarvest(this);
    }
}
