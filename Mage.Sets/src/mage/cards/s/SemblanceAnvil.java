package mage.cards.s;

import java.util.List;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.common.FilterNonlandCard;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetCard;
import mage.util.CardUtil;

/**
 * @author nantuko
 */
public final class SemblanceAnvil extends CardImpl {

    public SemblanceAnvil(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // Imprint - When Semblance Anvil enters the battlefield, you may exile a nonland card from your hand.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SemblanceAnvilEffect(), true).setAbilityWord(AbilityWord.IMPRINT));

        // Spells you cast that share a card type with the exiled card cost {2} less to cast.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new SemblanceAnvilCostReductionEffect()));
    }

    private SemblanceAnvil(final SemblanceAnvil card) {
        super(card);
    }

    @Override
    public SemblanceAnvil copy() {
        return new SemblanceAnvil(this);
    }
}

class SemblanceAnvilEffect extends OneShotEffect {

    private static FilterCard filter = new FilterNonlandCard();

    public SemblanceAnvilEffect() {
        super(Outcome.Benefit);
        staticText = "exile a nonland card from your hand";
    }

    public SemblanceAnvilEffect(SemblanceAnvilEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null && !player.getHand().isEmpty()) {
            TargetCard target = new TargetCard(Zone.HAND, filter);
            player.choose(Outcome.Benefit, player.getHand(), target, source, game);
            Card card = player.getHand().get(target.getFirstTarget(), game);
            if (card != null) {
                card.moveToExile(getId(), "Semblance Anvil (Imprint)", source, game);
                Permanent permanent = game.getPermanent(source.getSourceId());
                if (permanent != null) {
                    permanent.imprint(card.getId(), game);
                }
                return true;
            }
        }
        return true;
    }

    @Override
    public SemblanceAnvilEffect copy() {
        return new SemblanceAnvilEffect(this);
    }

}

class SemblanceAnvilCostReductionEffect extends CostModificationEffectImpl {

    private static final String effectText = "Spells you cast that share a card type with the exiled card cost {2} less to cast";

    SemblanceAnvilCostReductionEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit, CostModificationType.REDUCE_COST);
        staticText = effectText;
    }

    SemblanceAnvilCostReductionEffect(SemblanceAnvilCostReductionEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        SpellAbility spellAbility = (SpellAbility) abilityToModify;
        CardUtil.adjustCost(spellAbility, 2);
        return true;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        if (abilityToModify instanceof SpellAbility
                && abilityToModify.isControlledBy(source.getControllerId())) {
            Card spellCard = ((SpellAbility) abilityToModify).getCharacteristics(game);
            if (spellCard != null) {
                Permanent permanent = game.getPermanent(source.getSourceId());
                if (permanent != null) {
                    List<UUID> imprinted = permanent.getImprinted();
                    if (imprinted != null && !imprinted.isEmpty()) {
                        Card imprintedCard = game.getCard(imprinted.get(0));
                        return imprintedCard != null && imprintedCard.shareTypes(spellCard, game);
                    }
                }
            }
        }
        return false;
    }

    @Override
    public SemblanceAnvilCostReductionEffect copy() {
        return new SemblanceAnvilCostReductionEffect(this);
    }

}
