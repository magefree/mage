package mage.cards.j;

import mage.MageObject;
import mage.ObjectColor;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.SpellCastAllTriggeredAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.ChooseColorEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.FilterSpell;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class JeweledTorque extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("a spell of the chosen color");

    static {
        filter.add(JeweledTorquePredicate.instance);
    }

    public JeweledTorque(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        // As Jeweled Torque enters the battlefield, choose a color.
        this.addAbility(new AsEntersBattlefieldAbility(new ChooseColorEffect(Outcome.Benefit)));

        // Whenever a player casts a spell of the chosen color, you may pay {2}. If you do, you gain 2 life.
        this.addAbility(new SpellCastAllTriggeredAbility(
                new DoIfCostPaid(
                        new GainLifeEffect(2),
                        new GenericManaCost(2)
                ), filter, false
        ));
    }

    private JeweledTorque(final JeweledTorque card) {
        super(card);
    }

    @Override
    public JeweledTorque copy() {
        return new JeweledTorque(this);
    }
}

enum JeweledTorquePredicate implements ObjectSourcePlayerPredicate<MageObject> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<MageObject> input, Game game) {
        Permanent permanent = game.getPermanent(input.getSourceId());
        if (permanent == null) {
            return false;
        }
        ObjectColor color = (ObjectColor) game.getState().getValue(permanent.getId() + "_color");
        return color != null && input.getObject().getColor(game).shares(color);
    }
}