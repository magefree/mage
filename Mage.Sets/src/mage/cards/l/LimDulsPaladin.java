
package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksAndIsNotBlockedTriggeredAbility;
import mage.abilities.common.BecomesBlockedSourceTriggeredAbility;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.costs.common.DiscardTargetCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.effects.common.SacrificeSourceUnlessPaysEffect;
import mage.abilities.effects.common.continuous.AssignNoCombatDamageSourceEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCardInHand;

/**
 *
 * @author LoneFox
 */
public final class LimDulsPaladin extends CardImpl {

    public LimDulsPaladin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}{R}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(0);
        this.toughness = new MageInt(3);

        // Trample
        this.addAbility(TrampleAbility.getInstance());
        // At the beginning of your upkeep, you may discard a card. If you don't, sacrifice Lim-Dul's Paladin and draw a card.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, new LimDulsPaladinEffect(), TargetController.YOU, false));
        // Whenever Lim-Dul's Paladin becomes blocked, it gets +6/+3 until end of turn.
        this.addAbility(new BecomesBlockedSourceTriggeredAbility(new BoostSourceEffect(6, 3, Duration.EndOfTurn), false));
        // Whenever Lim-Dul's Paladin attacks and isn't blocked, it assigns no combat damage to defending player this turn and that player loses 4 life.
        Effect effect = new AssignNoCombatDamageSourceEffect(Duration.EndOfTurn);
        effect.setText("it assigns no combat damage this turn");
        Ability ability = new AttacksAndIsNotBlockedTriggeredAbility(effect, false, true);
        effect = new LoseLifeTargetEffect(4);
        effect.setText("and defending player loses 4 life");
        ability.addEffect(effect);
        this.addAbility(ability);
    }

    private LimDulsPaladin(final LimDulsPaladin card) {
        super(card);
    }

    @Override
    public LimDulsPaladin copy() {
        return new LimDulsPaladin(this);
    }
}

class LimDulsPaladinEffect extends SacrificeSourceUnlessPaysEffect {

    public LimDulsPaladinEffect() {
        super(new DiscardTargetCost(new TargetCardInHand()));
        staticText = "you may discard a card. If you don't, sacrifice {this} and draw a card.";
    }

    private LimDulsPaladinEffect(final LimDulsPaladinEffect effect) {
        super(effect);
    }

    @Override
    public LimDulsPaladinEffect copy() {
        return new LimDulsPaladinEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        if(permanent != null) {
            super.apply(game, source);
            // Not in play anymore -> was sacrificed, draw a card
            if(game.getPermanent(source.getSourceId()) == null) {
                 return new DrawCardSourceControllerEffect(1).apply(game, source);
            }
            return true;
        }
        return false;
    }
}
