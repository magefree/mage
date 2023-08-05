package mage.cards.e;

import mage.abilities.Ability;
import mage.abilities.ActivatedAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.effects.common.MillCardsTargetEffect;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.common.TargetControlledPermanent;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class EmbalmersTools extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("untapped Zombie you control");

    static {
        filter.add(TappedPredicate.UNTAPPED);
        filter.add(SubType.ZOMBIE.getPredicate());
    }

    public EmbalmersTools(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        // Activated abilities of creature cards in your graveyard cost {1} less to activate.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new EmbalmersToolsEffect()));

        // Tap an untapped Zombie you control: Target player puts the top card of their library into their graveyard.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new MillCardsTargetEffect(1), new TapTargetCost(new TargetControlledPermanent(filter)));
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    private EmbalmersTools(final EmbalmersTools card) {
        super(card);
    }

    @Override
    public EmbalmersTools copy() {
        return new EmbalmersTools(this);
    }
}

class EmbalmersToolsEffect extends CostModificationEffectImpl {

    private static final String effectText = "Activated abilities of creature cards in your graveyard cost {1} less to activate";
    private static final FilterCreatureCard filter = new FilterCreatureCard();

    static {
        filter.add(TargetController.YOU.getOwnerPredicate());
    }

    public EmbalmersToolsEffect() {
        super(Duration.Custom, Outcome.Benefit, CostModificationType.REDUCE_COST);
        staticText = effectText;
    }

    public EmbalmersToolsEffect(final EmbalmersToolsEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        Player controller = game.getPlayer(abilityToModify.getControllerId());
        if (controller != null) {
            CardUtil.reduceCost(abilityToModify, 1);
            return true;
        }
        return false;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        if (abilityToModify.getAbilityType() == AbilityType.ACTIVATED
                || (abilityToModify.getAbilityType() == AbilityType.MANA && (abilityToModify instanceof ActivatedAbility))) {
            // Activated abilities of creatures
            Card card = game.getCard(abilityToModify.getSourceId());
            if (filter.match(card, source.getControllerId(), source, game) && game.getState().getZone(card.getId()) == Zone.GRAVEYARD) {
                return true;
            }
        }
        return false;
    }

    @Override
    public EmbalmersToolsEffect copy() {
        return new EmbalmersToolsEffect(this);
    }
}
