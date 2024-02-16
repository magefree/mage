package mage.abilities.keyword;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.ActivatedAbilityImpl;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.abilities.costs.common.ExileSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterArtifactCard;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterOwnedCard;
import mage.filter.predicate.Predicate;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInGraveyardBattlefieldOrStack;
import mage.util.CardUtil;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public class CraftAbility extends ActivatedAbilityImpl {

    private static final FilterCard artifactFilter = new FilterArtifactCard("artifact");

    static {
        artifactFilter.add(TargetController.YOU.getOwnerPredicate());
    }

    private final String description;
    private final String manaString;

    public CraftAbility(String manaString) {
        this(manaString, "artifact", "another artifact you control or an artifact card from your graveyard", CardType.ARTIFACT.getPredicate());
    }

    public CraftAbility(String manaString, String description, String targetDescription, Predicate<MageObject>... predicates) {
        this(manaString, description, targetDescription, 1, 1, predicates);
    }

    public CraftAbility(String manaString, String description, String targetDescription, int minTargets, int maxTargets, Predicate<MageObject>... predicates) {
        this(manaString, description, makeTarget(minTargets, maxTargets, targetDescription, predicates));
    }

    public CraftAbility(String manaString, String description, TargetCardInGraveyardBattlefieldOrStack target) {
        super(Zone.BATTLEFIELD, new CraftEffect(), new ManaCostsImpl<>(manaString));
        this.addCost(new ExileSourceCost());
        this.addCost(new CraftCost(target));
        this.addSubAbility(new TransformAbility());
        this.timing = TimingRule.SORCERY;
        this.manaString = manaString;
        this.description = description;
    }

    private CraftAbility(final CraftAbility ability) {
        super(ability);
        this.manaString = ability.manaString;
        this.description = ability.description;
    }

    @Override
    public CraftAbility copy() {
        return new CraftAbility(this);
    }

    @Override
    public String getRule() {
        return "Craft with " + description + ' ' + manaString;
    }

    private static TargetCardInGraveyardBattlefieldOrStack makeTarget(int minTargets, int maxTargets, String targetDescription, Predicate<MageObject>... predicates) {
        FilterPermanent filterPermanent = new FilterControlledPermanent();
        filterPermanent.add(AnotherPredicate.instance);
        FilterCard filterCard = new FilterOwnedCard();
        for (Predicate<MageObject> predicate : predicates) {
            filterPermanent.add(predicate);
            filterCard.add(predicate);
        }
        return new TargetCardInGraveyardBattlefieldOrStack(minTargets, maxTargets, filterCard, filterPermanent, targetDescription);
    }
}

class CraftCost extends CostImpl {

    private final TargetCardInGraveyardBattlefieldOrStack target;

    CraftCost(TargetCardInGraveyardBattlefieldOrStack target) {
        super();
        this.target = target;
        target.withNotTarget(true);
    }

    private CraftCost(final CraftCost cost) {
        super(cost);
        this.target = cost.target.copy();
    }

    @Override
    public CraftCost copy() {
        return new CraftCost(this);
    }

    @Override
    public boolean canPay(Ability ability, Ability source, UUID controllerId, Game game) {
        return target.canChoose(controllerId, source, game);
    }

    @Override
    public boolean pay(Ability ability, Game game, Ability source, UUID controllerId, boolean noMana, Cost costToPay) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            paid = false;
            return paid;
        }
        player.chooseTarget(Outcome.Exile, target, source, game);
        Set<Card> cards = target
                .getTargets()
                .stream()
                .map(uuid -> {
                    Permanent permanent = game.getPermanent(uuid);
                    if (permanent != null) {
                        game.fireEvent(GameEvent.getEvent(GameEvent.EventType.EXILED_WHILE_CRAFTING,
                                permanent.getId(), source, player.getId()));
                        return permanent;
                    }
                    return game.getCard(uuid);
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
        player.moveCardsToExile(
                cards, source, game, true,
                CardUtil.getExileZoneId(game, source),
                CardUtil.getSourceName(game, source)
        );
        paid = true;
        return paid;
    }
}

class CraftEffect extends OneShotEffect {

    CraftEffect() {
        super(Outcome.Benefit);
    }

    private CraftEffect(final CraftEffect effect) {
        super(effect);
    }

    @Override
    public CraftEffect copy() {
        return new CraftEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Card card = game.getCard(source.getSourceId());
        if (player == null || card == null || card.getZoneChangeCounter(game) != source.getSourceObjectZoneChangeCounter() + 1) {
            return false;
        }
        game.getState().setValue(TransformAbility.VALUE_KEY_ENTER_TRANSFORMED + source.getSourceId(), Boolean.TRUE);
        player.moveCards(card, Zone.BATTLEFIELD, source, game);
        return true;
    }
}
