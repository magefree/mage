package mage.cards.d;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class DragonlordKolaghan extends CardImpl {

    public DragonlordKolaghan(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}{R}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELDER);
        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(6);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Other creatures you control have haste.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD,
                new GainAbilityControlledEffect(HasteAbility.getInstance(), Duration.WhileOnBattlefield, StaticFilters.FILTER_PERMANENT_CREATURE, true)));

        // Whenever an opponent casts a creature or planeswalker spell with the same name as a card in their graveyard, that player loses 10 life.
        Effect effect = new LoseLifeTargetEffect(10);
        effect.setText("that player loses 10 life");
        this.addAbility(new DragonlordKolaghanTriggeredAbility(effect));

    }

    private DragonlordKolaghan(final DragonlordKolaghan card) {
        super(card);
    }

    @Override
    public DragonlordKolaghan copy() {
        return new DragonlordKolaghan(this);
    }
}

class DragonlordKolaghanTriggeredAbility extends TriggeredAbilityImpl {

    public DragonlordKolaghanTriggeredAbility(Effect effect) {
        super(Zone.BATTLEFIELD, effect, false);
        setTriggerPhrase("Whenever an opponent casts a creature or planeswalker spell with the same name as a card in their graveyard, ");
    }

    public DragonlordKolaghanTriggeredAbility(final DragonlordKolaghanTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public DragonlordKolaghanTriggeredAbility copy() {
        return new DragonlordKolaghanTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Player controller = game.getPlayer(getControllerId());
        if (controller != null && controller.hasOpponent(event.getPlayerId(), game)) {
            Spell spell = game.getStack().getSpell(event.getSourceId());
            if (spell != null && !spell.isFaceDown(game) && (spell.isCreature(game) || spell.isPlaneswalker(game))) {
                Player opponent = game.getPlayer(event.getPlayerId());
                if(opponent != null) {
                    boolean sameName = false;
                    for (Card graveCard : opponent.getGraveyard().getCards(game)) {
                        if (CardUtil.haveSameNames(graveCard, spell)) {
                            sameName = true;
                            break;
                        }
                    }
                    if (sameName) {
                        this.getEffects().get(0).setTargetPointer(new FixedTarget(event.getPlayerId()));
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
