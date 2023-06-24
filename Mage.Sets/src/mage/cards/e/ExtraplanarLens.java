
package mage.cards.e;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.mana.AddManaOfAnyTypeProducedEffect;
import mage.abilities.mana.TriggeredManaAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterLandPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ManaEvent;
import mage.game.events.TappedForManaEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetLandPermanent;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

/**
 *
 * @author jeffwadsworth
 */
public final class ExtraplanarLens extends CardImpl {

    private static final FilterLandPermanent filter = new FilterLandPermanent("land you control");

    static {
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    public ExtraplanarLens(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // Imprint - When Extraplanar Lens enters the battlefield, you may exile target land you control.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ExtraplanarLensImprintEffect(), true);
        ability.setAbilityWord(AbilityWord.IMPRINT);
        ability.addTarget(new TargetLandPermanent(filter));
        this.addAbility(ability);

        // Whenever a land with the same name as the exiled card is tapped for mana, its controller adds one mana of any type that land produced.
        this.addAbility(new ExtraplanarLensTriggeredAbility());

    }

    private ExtraplanarLens(final ExtraplanarLens card) {
        super(card);
    }

    @Override
    public ExtraplanarLens copy() {
        return new ExtraplanarLens(this);
    }
}

class ExtraplanarLensImprintEffect extends OneShotEffect {

    public ExtraplanarLensImprintEffect() {
        super(Outcome.Neutral);
        staticText = "you may exile target land you control";
    }

    public ExtraplanarLensImprintEffect(ExtraplanarLensImprintEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent extraplanarLens = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (controller != null) {
            Permanent targetLand = game.getPermanent(source.getFirstTarget());
            if (targetLand != null) {
                targetLand.moveToExile(null, extraplanarLens.getName() + " (Imprint)", source, game);
                extraplanarLens.imprint(targetLand.getId(), game);
                extraplanarLens.addInfo("imprint", CardUtil.addToolTipMarkTags("[Imprinted card - " + targetLand.getLogName() + ']'), game);
            }
            return true;
        }
        return false;
    }

    @Override
    public ExtraplanarLensImprintEffect copy() {
        return new ExtraplanarLensImprintEffect(this);
    }

}

class ExtraplanarLensTriggeredAbility extends TriggeredManaAbility {

    public ExtraplanarLensTriggeredAbility() {
        super(Zone.BATTLEFIELD, new AddManaOfAnyTypeProducedEffect().setText("its controller adds one mana of any type that land produced"));
        setTriggerPhrase("Whenever a land with the same name as the exiled card is tapped for mana, ");
    }

    public ExtraplanarLensTriggeredAbility(final ExtraplanarLensTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.TAPPED_FOR_MANA;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent landTappedForMana = ((TappedForManaEvent) event).getPermanent(); // need only info about permanent
        Permanent extraplanarLens = game.getPermanent(getSourceId());
        if (extraplanarLens != null
                && landTappedForMana != null
                && !extraplanarLens.getImprinted().isEmpty()) {
            Card imprinted = game.getCard(extraplanarLens.getImprinted().get(0));
            if (imprinted != null
                    && game.getState().getZone(imprinted.getId()) == Zone.EXILED) {
                if (landTappedForMana.getName().equals(imprinted.getName())
                        && landTappedForMana.isLand(game)) {
                    ManaEvent mEvent = (ManaEvent) event;
                    for (Effect effect : getEffects()) {
                        effect.setValue("mana", mEvent.getMana());
                        effect.setValue("tappedPermanent", landTappedForMana);
                    }
                    getEffects().get(0).setTargetPointer(new FixedTarget(landTappedForMana.getId()));
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public ExtraplanarLensTriggeredAbility copy() {
        return new ExtraplanarLensTriggeredAbility(this);
    }

}
