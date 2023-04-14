package mage.cards.r;

import mage.MageInt;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.keyword.WardAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RefractionElemental extends CardImpl {

    public RefractionElemental(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "");

        this.subtype.add(SubType.ELEMENTAL);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);
        this.color.setRed(true);
        this.nightCard = true;

        // Ward--Pay 2 life.
        this.addAbility(new WardAbility(new PayLifeCost(2), false));

        // Whenever you cast a spell, Refraction Elemental deals 2 damage to each opponent.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new DamagePlayersEffect(2, TargetController.OPPONENT), false
        ));
    }

    private RefractionElemental(final RefractionElemental card) {
        super(card);
    }

    @Override
    public RefractionElemental copy() {
        return new RefractionElemental(this);
    }
}
