package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.keyword.RepairAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author Styxo
 */
public final class AAT1 extends CardImpl {

    public AAT1(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{1}{W}{U}{B}");
        this.subtype.add(SubType.DROID);
        this.subtype.add(SubType.CONSTRUCT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Whenever a repair counter is removed from a creature card your graveyard, you may pay {W/B}. If you do, target player loses 1 life and you gain 1 life. 
        DoIfCostPaid effect = new DoIfCostPaid(new LoseLifeTargetEffect(1), new ManaCostsImpl<>("{W/B}"));
        Effect additionalEffect = new GainLifeEffect(1);
        additionalEffect.setText("and you gain 1 life");
        effect.addEffect(additionalEffect);
        Ability ability = new AAT1TriggeredAbility(effect);
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);

        // Repair 4
        this.addAbility(new RepairAbility(4));
    }

    private AAT1(final AAT1 card) {
        super(card);
    }

    @Override
    public AAT1 copy() {
        return new AAT1(this);
    }

    private static class AAT1TriggeredAbility extends TriggeredAbilityImpl {

        public AAT1TriggeredAbility(Effect effect) {
            super(Zone.BATTLEFIELD, effect);
            setTriggerPhrase("Whenever a repair counter is removed from a creature card in your graveyard ");
        }

        public AAT1TriggeredAbility(AAT1TriggeredAbility ability) {
            super(ability);
        }

        @Override
        public boolean checkEventType(GameEvent event, Game game) {
            return event.getType() == GameEvent.EventType.COUNTER_REMOVED;
        }

        @Override
        public boolean checkTrigger(GameEvent event, Game game) {
            Card card = game.getCard(event.getTargetId());
            if (card != null
                    && event.getPlayerId().equals(game.getControllerId(sourceId))
                    && card.isCreature(game)
                    && game.getState().getZone(card.getId()) == Zone.GRAVEYARD
                    && event.getData().equals("repair")) {
                return true;
            }
            return false;
        }

        @Override
        public AAT1TriggeredAbility copy() {
            return new AAT1TriggeredAbility(this);
        }
    }
}


