package mage.cards.v;

import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ChooseColorEffect;
import mage.abilities.effects.common.ChooseCreatureTypeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.token.VolrathsLaboratoryToken;

import java.util.UUID;

/**
 * @author emerald000
 */
public final class VolrathsLaboratory extends CardImpl {

    public VolrathsLaboratory(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{5}");

        // As Volrath's Laboratory enters the battlefield, choose a color and a creature type.
        Ability ability = new AsEntersBattlefieldAbility(new ChooseColorEffect(Outcome.Neutral));
        Effect effect = new ChooseCreatureTypeEffect(Outcome.Neutral);
        effect.setText("and a creature type");
        ability.addEffect(effect);
        this.addAbility(ability);

        // {5}, {T}: Create a 2/2 creature token of the chosen color and type.
        ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new VolrathsLaboratoryEffect(), new GenericManaCost(5));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private VolrathsLaboratory(final VolrathsLaboratory card) {
        super(card);
    }

    @Override
    public VolrathsLaboratory copy() {
        return new VolrathsLaboratory(this);
    }
}

class VolrathsLaboratoryEffect extends OneShotEffect {

    VolrathsLaboratoryEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "Create a 2/2 creature token of the chosen color and type";
    }

    VolrathsLaboratoryEffect(final VolrathsLaboratoryEffect effect) {
        super(effect);
    }

    @Override
    public VolrathsLaboratoryEffect copy() {
        return new VolrathsLaboratoryEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        ObjectColor color = (ObjectColor) game.getState().getValue(source.getSourceId() + "_color");
        SubType subType = ChooseCreatureTypeEffect.getChosenCreatureType(source.getSourceId(), game);
        if (subType == null || color == null) {
            return false;
        }
        return new VolrathsLaboratoryToken(color, subType).putOntoBattlefield(1, game, source, source.getControllerId());
    }
}
