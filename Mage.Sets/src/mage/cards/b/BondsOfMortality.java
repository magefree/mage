
package mage.cards.b;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.LoseAbilityAllEffect;
import mage.abilities.keyword.HexproofAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public final class BondsOfMortality extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creatures your opponents control");

    static {
        filter.add(TargetController.OPPONENT.getControllerPredicate());
    }

    public BondsOfMortality(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{G}");

        // When Bonds of Mortality enters the battlefield, draw a card.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DrawCardSourceControllerEffect(1), false));

        // {G}: Creatures your opponents control lose hexproof and indestructible until end of turn.
        Effect effect = new LoseAbilityAllEffect(HexproofAbility.getInstance(), Duration.EndOfTurn, filter);
        effect.setText("Creatures your opponents control lose hexproof");
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, effect, new ManaCostsImpl("{G}"));
        effect = new LoseAbilityAllEffect(IndestructibleAbility.getInstance(), Duration.EndOfTurn, filter);
        effect.setText("and indestructible until end of turn");
        ability.addEffect(effect);
        this.addAbility(ability);
    }

    private BondsOfMortality(final BondsOfMortality card) {
        super(card);
    }

    @Override
    public BondsOfMortality copy() {
        return new BondsOfMortality(this);
    }
}
