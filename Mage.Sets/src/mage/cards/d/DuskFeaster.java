
package mage.cards.d;

import java.util.EnumSet;
import java.util.UUID;
import mage.MageInt;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author escplan9 (Derek Monturo - dmontur1 at gmail dot com)
 */
public final class DuskFeaster extends CardImpl {

    public DuskFeaster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{B}{B}");
        this.subtype.add(SubType.VAMPIRE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // <i>Delirium</i> &mdash; Dusk Feaster costs {2} less to cast if there are four or more card types among cards in your graveyard.
        this.addAbility(new SimpleStaticAbility(Zone.STACK, new DuskFeasterCostReductionEffect()));

        // Flying
        this.addAbility(FlyingAbility.getInstance());
    }

    public DuskFeaster(final DuskFeaster card) {
        super(card);
    }

    @Override
    public DuskFeaster copy() {
        return new DuskFeaster(this);
    }
}

class DuskFeasterCostReductionEffect extends CostModificationEffectImpl {

    DuskFeasterCostReductionEffect() {
        super(Duration.Custom, Outcome.Benefit, CostModificationType.REDUCE_COST);
        staticText = "<i>Delirium</i> &mdash; {this} costs {2} less to cast if there are four or more card types among cards in your graveyard";
    }

    DuskFeasterCostReductionEffect(final DuskFeasterCostReductionEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        SpellAbility spellAbility = (SpellAbility) abilityToModify;
        Mana mana = spellAbility.getManaCostsToPay().getMana();
        if (mana.getGeneric() > 0) {
            int newCount = mana.getGeneric() - 2;
            if (newCount < 0) {
                newCount = 0;
            }
            mana.setGeneric(newCount);
            spellAbility.getManaCostsToPay().load(mana.toString());
            return true;
        }
        return false;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {

        boolean hasDelirium = false;
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            EnumSet<CardType> foundCardTypes = EnumSet.noneOf(CardType.class);
            for (Card card : controller.getGraveyard().getCards(game)) {
                foundCardTypes.addAll(card.getCardType());
            }
            int number = foundCardTypes.size();
            hasDelirium = number > 3;
        }

        return abilityToModify.getSourceId().equals(source.getSourceId())
                && (abilityToModify instanceof SpellAbility)
                && hasDelirium;
    }

    @Override
    public DuskFeasterCostReductionEffect copy() {
        return new DuskFeasterCostReductionEffect(this);
    }
}