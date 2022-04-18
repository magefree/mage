
package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.BlocksSourceTriggeredAbility;
import mage.abilities.common.TurnedFaceUpSourceTriggeredAbility;
import mage.abilities.costs.common.RevealTargetFromHandCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageControllerEffect;
import mage.abilities.effects.common.combat.CantBlockTargetEffect;
import mage.abilities.keyword.MorphAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.target.common.TargetCardInHand;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author emerald000
 */
public final class HordeAmbusher extends CardImpl {
    
    private static final FilterCard filter = new FilterCard("a red card in your hand");
    static {
        filter.add(new ColorPredicate(ObjectColor.RED));
    }

    public HordeAmbusher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{R}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.BERSERKER);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever Horde Ambusher blocks, it deals 1 damage to you.
        this.addAbility(new BlocksSourceTriggeredAbility(new DamageControllerEffect(1, "it"), false));
        
        // Morph - Reveal a red card in your hand.
        this.addAbility(new MorphAbility(new RevealTargetFromHandCost(new TargetCardInHand(filter))));
        
        // When Horde Ambusher is turned face up, target creature can't block this turn.
        Effect effect = new CantBlockTargetEffect(Duration.EndOfTurn);
        effect.setText("target creature can't block this turn");
        Ability ability = new TurnedFaceUpSourceTriggeredAbility(effect);
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private HordeAmbusher(final HordeAmbusher card) {
        super(card);
    }

    @Override
    public HordeAmbusher copy() {
        return new HordeAmbusher(this);
    }
}
