
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author LevelX2
 */
public final class AleshaWhoSmilesAtDeath extends CardImpl {

    private static final FilterCreatureCard filter = new FilterCreatureCard("creature card with power 2 or less");

    static {
        filter.add(new PowerPredicate(ComparisonType.FEWER_THAN, 3));
    }

    public AleshaWhoSmilesAtDeath(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // Whenever Alesha, Who Smiles at Death attacks, you may pay {W/B}{W/B}. If you do, return target creature card with power 2 or less from your graveyard to the battlefield tapped and attacking.
        Ability ability = new AttacksTriggeredAbility(new DoIfCostPaid(new AleshaWhoSmilesAtDeathEffect(), new ManaCostsImpl("{W/B}{W/B}")), false);
        ability.addTarget(new TargetCardInYourGraveyard(filter));
        this.addAbility(ability);
    }

    public AleshaWhoSmilesAtDeath(final AleshaWhoSmilesAtDeath card) {
        super(card);
    }

    @Override
    public AleshaWhoSmilesAtDeath copy() {
        return new AleshaWhoSmilesAtDeath(this);
    }
}

class AleshaWhoSmilesAtDeathEffect extends OneShotEffect {

    public AleshaWhoSmilesAtDeathEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "return target creature card with power 2 or less from your graveyard to the battlefield tapped and attacking";
    }

    public AleshaWhoSmilesAtDeathEffect(final AleshaWhoSmilesAtDeathEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());

        if (controller != null) {
            Card card = game.getCard(getTargetPointer().getFirst(game, source));
            if (card != null) {
                if (controller.moveCards(card, Zone.BATTLEFIELD, source, game, true, false, false, null)) {
                    game.getCombat().addAttackingCreature(card.getId(), game);
                }
            }
            return true;

        }
        return false;
    }

    @Override
    public AleshaWhoSmilesAtDeathEffect copy() {
        return new AleshaWhoSmilesAtDeathEffect(this);
    }

}
