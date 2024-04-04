package mage.cards.t;

import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTappedAsItEntersChooseColorAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.mana.AddManaChosenColorEffect;
import mage.abilities.effects.mana.ManaEffect;
import mage.abilities.mana.ActivatedManaAbilityImpl;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;

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
        this.addAbility(new SimpleManaAbility(Zone.BATTLEFIELD, new AddManaChosenColorEffect(), new TapSourceCost()));

        // {1}, {T}: For each color among monocolored permanents you control, add one mana of that color.
        this.addAbility(new TarnationVistaManaAbility());
    }

    private TarnationVista(final TarnationVista card) {
        super(card);
    }

    @Override
    public TarnationVista copy() {
        return new TarnationVista(this);
    }
}

class TarnationVistaManaAbility extends ActivatedManaAbilityImpl {

    TarnationVistaManaAbility() {
        super(Zone.BATTLEFIELD, new TarnationVistaEffect(), new GenericManaCost(1));
        this.addCost(new TapSourceCost());
    }

    private TarnationVistaManaAbility(final TarnationVistaManaAbility ability) {
        super(ability);
    }

    @Override
    public TarnationVistaManaAbility copy() {
        return new TarnationVistaManaAbility(this);
    }

}

// Inspired by Bloom Tender
class TarnationVistaEffect extends ManaEffect {

    TarnationVistaEffect() {
        super();
        staticText = "For each color among permanents you control, add one mana of that color";
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
        Mana mana = new Mana();
        if (game == null) {
            return mana;
        }
        for (Permanent permanent : game.getBattlefield().getAllActivePermanents(source.getControllerId())) {
            if (permanent.getColor(game).getColorCount() != 1) {
                continue;
            }
            if (mana.getBlack() == 0 && permanent.getColor(game).isBlack()) {
                mana.increaseBlack();
            }
            if (mana.getBlue() == 0 && permanent.getColor(game).isBlue()) {
                mana.increaseBlue();
            }
            if (mana.getRed() == 0 && permanent.getColor(game).isRed()) {
                mana.increaseRed();
            }
            if (mana.getGreen() == 0 && permanent.getColor(game).isGreen()) {
                mana.increaseGreen();
            }
            if (mana.getWhite() == 0 && permanent.getColor(game).isWhite()) {
                mana.increaseWhite();
            }
        }
        return mana;
    }
}
