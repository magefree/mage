package mage.cards.c;

import mage.abilities.common.ZoneChangeTriggeredAbility;
import mage.abilities.costs.common.ExileSourceFromGraveCost;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.constants.Zone;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CreepingChill extends CardImpl {

    public CreepingChill(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{B}");

        // Creeping Chill deals 3 damage to each opponent and you gain 3 life.
        this.getSpellAbility().addEffect(
                new DamagePlayersEffect(3, TargetController.OPPONENT)
        );
        this.getSpellAbility().addEffect(
                new GainLifeEffect(3).setText("and you gain 3 life")
        );

        // When Creeping Chill is put into your graveyard from your library, you may exile it. If you do, Creeping Chill deals 3 damage to each opponent and you gain 3 life.
        this.addAbility(new CreepingChillAbility());
    }

    private CreepingChill(final CreepingChill card) {
        super(card);
    }

    @Override
    public CreepingChill copy() {
        return new CreepingChill(this);
    }
}

class CreepingChillAbility extends ZoneChangeTriggeredAbility {

    public CreepingChillAbility() {
        super(
                Zone.LIBRARY, Zone.GRAVEYARD,
                new DoIfCostPaid(
                        new DamagePlayersEffect(3, TargetController.OPPONENT),
                        new ExileSourceFromGraveCost()
                ).addEffect(new GainLifeEffect(3)),
                "", false
        );
    }

    private CreepingChillAbility(final CreepingChillAbility ability) {
        super(ability);
    }

    @Override
    public CreepingChillAbility copy() {
        return new CreepingChillAbility(this);
    }

    @Override
    public String getRule() {
        return "When {this} is put into your graveyard from your library, "
                + "you may exile it. If you do, {this} deals 3 damage "
                + "to each opponent and you gain 3 life.";
    }
}
