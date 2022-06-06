

package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.TargetSpell;
import mage.target.common.TargetCardInHand;

/**
 * @author LevelX
 */
public final class HisokaMinamoSensei extends CardImpl {

    public HisokaMinamoSensei(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{U}{U}");
        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // {2}{U}, Discard a card: Counter target spell if it has the same converted mana cost as the discarded card.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new HisokaMinamoSenseiCounterEffect(), new ManaCostsImpl<>("{2}{U}"));
        ability.addTarget(new TargetSpell());
        TargetCardInHand targetCard = new TargetCardInHand(new FilterCard("a card"));
        ability.addCost(new HisokaMinamoSenseiDiscardTargetCost(targetCard));
        this.addAbility(ability);
    }

    private HisokaMinamoSensei(final HisokaMinamoSensei card) {
        super(card);
    }

    @Override
    public HisokaMinamoSensei copy() {
        return new HisokaMinamoSensei(this);
    }

}

class HisokaMinamoSenseiDiscardTargetCost extends CostImpl {

    protected Card card = null;

    public HisokaMinamoSenseiDiscardTargetCost(TargetCardInHand target) {
        this.addTarget(target);
        this.text = "Discard " + target.getTargetName();
    }

    public HisokaMinamoSenseiDiscardTargetCost(HisokaMinamoSenseiDiscardTargetCost cost) {
        super(cost);
    }

    @Override
    public boolean pay(Ability ability, Game game, Ability source, UUID controllerId, boolean noMana, Cost costToPay) {
        if (targets.choose(Outcome.Discard, controllerId, source.getSourceId(), source, game)) {
            Player player = game.getPlayer(controllerId);
            if(player != null) {
                for (UUID targetId : targets.get(0).getTargets()) {
                    card = player.getHand().get(targetId, game);
                    if (card == null) {
                        return false;
                    }
                    paid |= player.discard(card, true, source, game);

                }
            }
        }
        return paid;
    }

    @Override
    public boolean canPay(Ability ability, Ability source, UUID controllerId, Game game) {
        return targets.canChoose(controllerId, source, game);
    }

    @Override
    public HisokaMinamoSenseiDiscardTargetCost copy() {
        return new HisokaMinamoSenseiDiscardTargetCost(this);
    }

    public Card getDiscardedCard() {
        return card;
    }

}

class HisokaMinamoSenseiCounterEffect extends OneShotEffect {
    HisokaMinamoSenseiCounterEffect() {
        super(Outcome.Detriment);
        staticText = "Counter target spell if it has the same mana value as the discarded card";
    }

    HisokaMinamoSenseiCounterEffect(final HisokaMinamoSenseiCounterEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Spell spell = game.getStack().getSpell(targetPointer.getFirst(game, source));
        if (spell != null) {
            HisokaMinamoSenseiDiscardTargetCost cost = (HisokaMinamoSenseiDiscardTargetCost) source.getCosts().get(0);
            if (cost != null && cost.getDiscardedCard().getManaValue() == spell.getManaValue()) {
                return game.getStack().counter(targetPointer.getFirst(game, source), source, game);
            }
        }
        return false;
    }

    @Override
    public HisokaMinamoSenseiCounterEffect copy() {
        return new HisokaMinamoSenseiCounterEffect(this);
    }
}