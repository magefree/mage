package mage.cards.s;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.FilterSpell;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;

import java.util.UUID;

/**
 * @author freaisdead
 */
public final class ShanidSleepersScourge extends CardImpl {
    private static final FilterCreaturePermanent otherLegendaryCreaturesFilter = new FilterCreaturePermanent("other legendary creatures");
    private static final FilterSpell legendarySpellFilter = new FilterSpell("a legendary spell");
    private static final FilterPermanent legendaryLandFilter = new FilterPermanent("a legendary land");

    static {
        otherLegendaryCreaturesFilter.add(SuperType.LEGENDARY.getPredicate());

        legendarySpellFilter.add(SuperType.LEGENDARY.getPredicate());

        legendaryLandFilter.add(CardType.LAND.getPredicate());
        legendaryLandFilter.add(SuperType.LEGENDARY.getPredicate());
    }

    public ShanidSleepersScourge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}{W}{B}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Menace
        this.addAbility(new MenaceAbility());

        // Other legendary creatures you control have menace.
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
                new MenaceAbility(),
                Duration.WhileOnBattlefield,
                otherLegendaryCreaturesFilter,
                true)));
        // Whenever you play a legendary land or cast a legendary spell, you draw a card and you lose 1 life.
        this.addAbility(new DrawAndLoseLife(1,1));
    }

    private ShanidSleepersScourge(final ShanidSleepersScourge card) {
        super(card);
    }

    @Override
    public ShanidSleepersScourge copy() {
        return new ShanidSleepersScourge(this);
    }
}


class DrawAndLoseLife extends TriggeredAbilityImpl {

    public DrawAndLoseLife(int drawAmount, int loseLifeAmount) {
        super(Zone.BATTLEFIELD, new LoseLifeSourceControllerEffect(loseLifeAmount), false);
        this.addEffect(new DrawCardSourceControllerEffect(drawAmount));
    }

    public DrawAndLoseLife(DrawAndLoseLife ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        if(event.getType() == GameEvent.EventType.LAND_PLAYED || event.getType() == GameEvent.EventType.SPELL_CAST){
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return event.getPlayerId().equals(this.getControllerId());
    }

    @Override
    public boolean checkInterveningIfClause(Game game) {
        Player player = game.getPlayer(this.getControllerId());
        if (player != null) {
            if (player.getLandsPlayed() != 1) {
                return true;
            }
        }
        return false;
    }

    @Override
    public DrawAndLoseLife copy() {
        return new DrawAndLoseLife(this);
    }

    @Override
    public String getRule() {
        return "Whenever you play a legendary land or cast a legendary spell, you draw a card and you lose 1 life.";
    }

}