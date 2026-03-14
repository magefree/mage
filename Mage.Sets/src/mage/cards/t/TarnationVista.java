package mage.cards.t;

import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTappedAsItEntersChooseColorAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.dynamicvalue.common.ColorsAmongControlledPermanentsCount;
import mage.abilities.effects.mana.AddManaChosenColorEffect;
import mage.abilities.effects.mana.ManaEffect;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.Game;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class TarnationVista extends CardImpl {

    public TarnationVista(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // Tarnation Vista enters the battlefield tapped. As it enters, choose a color.
        this.addAbility(new EntersBattlefieldTappedAsItEntersChooseColorAbility());

        // {T}: Add one mana of the chosen color.
        this.addAbility(new SimpleManaAbility(new AddManaChosenColorEffect(), new TapSourceCost()));

        // {1}, {T}: For each color among monocolored permanents you control, add one mana of that color.
        Ability ability = new SimpleManaAbility(new TarnationVistaEffect(), new GenericManaCost(1));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability.addHint(ColorsAmongControlledPermanentsCount.MONOCOLORED_PERMANENTS.getHint()));
    }

    private TarnationVista(final TarnationVista card) {
        super(card);
    }

    @Override
    public TarnationVista copy() {
        return new TarnationVista(this);
    }
}

class TarnationVistaEffect extends ManaEffect {

    TarnationVistaEffect() {
        super();
        staticText = "for each color among monocolored permanents you control, add one mana of that color";
    }

    private TarnationVistaEffect(final TarnationVistaEffect effect) {
        super(effect);
    }

    @Override
    public TarnationVistaEffect copy() {
        return new TarnationVistaEffect(this);
    }

    @Override
    public Mana produceMana(Game game, Ability source) {
        if (game == null) {
            return new Mana();
        }
        return Mana.fromColor(ColorsAmongControlledPermanentsCount.MONOCOLORED_PERMANENTS.getAllControlledColors(game, source));
    }
}
