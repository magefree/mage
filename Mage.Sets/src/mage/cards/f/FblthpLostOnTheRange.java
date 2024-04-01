package mage.cards.f;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.continuous.LookAtTopCardOfLibraryAnyTimeEffect;
import mage.abilities.keyword.PlotAbility;
import mage.abilities.keyword.WardAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class FblthpLostOnTheRange extends CardImpl {

    public FblthpLostOnTheRange(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HOMUNCULUS);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Ward {2}
        this.addAbility(new WardAbility(new ManaCostsImpl<>("{2}")));

        // You may look at the top card of your library any time.
        this.addAbility(new SimpleStaticAbility(new LookAtTopCardOfLibraryAnyTimeEffect()));

        // The top card of your library has plot. The plot cost is equal to its mana cost.
        this.addAbility(new SimpleStaticAbility(new FblthpLostOnTheRangePlotGivingEffect()));

        // You may plot nonland cards from the top of your library.
        this.addAbility(new SimpleStaticAbility(new FblthpLostOnTheRangePermissionEffect()));
    }

    private FblthpLostOnTheRange(final FblthpLostOnTheRange card) {
        super(card);
    }

    @Override
    public FblthpLostOnTheRange copy() {
        return new FblthpLostOnTheRange(this);
    }
}

class FblthpLostOnTheRangePlotGivingEffect extends ContinuousEffectImpl {

    FblthpLostOnTheRangePlotGivingEffect() {
        super(Duration.WhileOnBattlefield, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
        this.staticText = "The top card of your library has plot. The plot cost is equal to its mana cost.";
    }

    private FblthpLostOnTheRangePlotGivingEffect(final FblthpLostOnTheRangePlotGivingEffect effect) {
        super(effect);
    }

    @Override
    public FblthpLostOnTheRangePlotGivingEffect copy() {
        return new FblthpLostOnTheRangePlotGivingEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Card card = controller.getLibrary().getFromTop(game);
        if (card == null) {
            return false;
        }
        game.getState().addOtherAbility(card, new PlotAbility(card.getManaCost().getText()));
        return true;
    }
}

class FblthpLostOnTheRangePermissionEffect extends ContinuousEffectImpl {

    FblthpLostOnTheRangePermissionEffect() {
        this(Duration.WhileOnBattlefield);
    }

    public FblthpLostOnTheRangePermissionEffect(Duration duration) {
        super(duration, Layer.PlayerEffects, SubLayer.NA, Outcome.Benefit);
        staticText = "You may plot nonland cards from the top of your library";
    }

    private FblthpLostOnTheRangePermissionEffect(final FblthpLostOnTheRangePermissionEffect effect) {
        super(effect);
    }

    @Override
    public FblthpLostOnTheRangePermissionEffect copy() {
        return new FblthpLostOnTheRangePermissionEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            controller.setPlotFromTopOfLibrary(true);
            return true;
        }
        return false;
    }
}