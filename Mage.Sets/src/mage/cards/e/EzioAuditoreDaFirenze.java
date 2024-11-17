package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.LoseGameTargetPlayerEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledSpellsEffect;
import mage.abilities.keyword.FreerunningAbility;
import mage.constants.*;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.filter.common.FilterNonlandCard;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author Kr4u7
 */
public final class EzioAuditoreDaFirenze extends CardImpl {

    private static final FilterNonlandCard filter = new FilterNonlandCard("assassin spells you cast");

    static {
        filter.add(SubType.ASSASSIN.getPredicate());
    }
    
    public EzioAuditoreDaFirenze(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ASSASSIN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Menace
        this.addAbility(new MenaceAbility(false));

        // Assassin spells you cast have freerunning {B}{B}.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityControlledSpellsEffect(new FreerunningAbility("{B}{B}"), filter)));
        // Whenever Ezio deals combat damage to a player, you may pay {W}{U}{B}{R}{G} if that player has 10 or less life. When you do, that player loses the game.
        this.addAbility(
                new ConditionalTriggeredAbility(
                        new DealsCombatDamageToAPlayerTriggeredAbility(
                                new DoIfCostPaid(
                                        new LoseGameTargetPlayerEffect(),new ManaCostsImpl<>("{W}{U}{B}{R}{G}")
                                ), false, true
                        ), EzioAuditoreDaFirenzeCondition.instance, "Whenever {this} deals combat damage to a player, you may pay {W}{U}{B}{R}{G} if that player has 10 or less life. When you do, that player loses the game."
                )
        );

    }

    private EzioAuditoreDaFirenze(final EzioAuditoreDaFirenze card) {
        super(card);
    }

    @Override
    public EzioAuditoreDaFirenze copy() {
        return new EzioAuditoreDaFirenze(this);
    }

}

enum EzioAuditoreDaFirenzeCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        UUID playerId = source.getEffects().get(0).getTargetPointer().getFirst(game, source);
        if (playerId == null) {
            return false;
        }
        Player player = game.getPlayer(playerId);
        return player != null && player.getLife() <= 10;
    }
}
