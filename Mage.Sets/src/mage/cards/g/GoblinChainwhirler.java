
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.DamageAllEffect;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;

/**
 *
 * @author Will
 */
public final class GoblinChainwhirler extends CardImpl {

    static final FilterPermanent filter = new FilterPermanent("creatures and planeswalkers your opponents control");
    
    static {
        filter.add(Predicates.or(
                CardType.CREATURE.getPredicate(),
                CardType.PLANESWALKER.getPredicate()));
        filter.add(TargetController.OPPONENT.getControllerPredicate());
    }

    public GoblinChainwhirler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{R}{R}{R}");
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());
        
        // When Goblin Chainwhirler enters the battlefield, it deals 1 damage to each opponent and each creature and planeswalker they control.
        DamagePlayersEffect effect1 = new DamagePlayersEffect(1, TargetController.OPPONENT);
        effect1.setText("it deals 1 damage to each opponent");

        DamageAllEffect effect2 = new DamageAllEffect(1, filter);
        effect2.setText("and each creature and planeswalker they control");

        Ability ability = new EntersBattlefieldTriggeredAbility(effect1);
        ability.addEffect(effect2);

        this.addAbility(ability);
    }

    private GoblinChainwhirler(final GoblinChainwhirler card) {
        super(card);
    }

    @Override
    public GoblinChainwhirler copy() {
        return new GoblinChainwhirler(this);
    }
}
