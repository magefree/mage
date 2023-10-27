package mage.abilities.keyword;

import mage.abilities.Ability;
import mage.abilities.ActivatedAbilityImpl;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.abilities.costs.common.ExileSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.constants.Outcome;
import mage.constants.TimingRule;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.game.Game;
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

    private final String description;
    private final String manaString;

    public CraftAbility(String manaString, String description, FilterCard filterCard, FilterPermanent filterPermanent) {
        this(manaString, description, new TargetCardInGraveyardBattlefieldOrStack(1, 1, filterCard, filterPermanent));
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
