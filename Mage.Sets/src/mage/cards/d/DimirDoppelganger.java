
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CopyEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentCard;
import mage.players.Player;
import mage.target.common.TargetCardInGraveyard;
import mage.util.functions.CopyApplier;

/**
 *
 * @author jeffwadsworth
 */
public final class DimirDoppelganger extends CardImpl {

    public DimirDoppelganger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}{B}");
        this.subtype.add(SubType.SHAPESHIFTER);

        this.power = new MageInt(0);
        this.toughness = new MageInt(2);

        // {1}{U}{B}: Exile target creature card from a graveyard. Dimir Doppelganger becomes a copy of that card, except it has this ability.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DimirDoppelgangerEffect(), new ManaCostsImpl<>("{1}{U}{B}"));
        ability.addTarget(new TargetCardInGraveyard(new FilterCreatureCard("creature card in a graveyard")));
        this.addAbility(ability);

    }

    private DimirDoppelganger(final DimirDoppelganger card) {
        super(card);
    }

    @Override
    public DimirDoppelganger copy() {
        return new DimirDoppelganger(this);
    }
}

class DimirDoppelgangerEffect extends OneShotEffect {

    DimirDoppelgangerEffect() {
        super(Outcome.Copy);
        staticText = "Exile target creature card from a graveyard. {this} becomes a copy of that card, except it has this ability";
    }

    DimirDoppelgangerEffect(final DimirDoppelgangerEffect effect) {
        super(effect);
    }

    @Override
    public DimirDoppelgangerEffect copy() {
        return new DimirDoppelgangerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent dimirDoppelganger = game.getPermanent(source.getSourceId());
        Permanent newBluePrint = null;
        if (controller != null
                && dimirDoppelganger != null) {
            Card copyFromCard = game.getCard(source.getFirstTarget());
            if (copyFromCard != null) {
                Cards cardsToExile = new CardsImpl();
                cardsToExile.add(copyFromCard);
                controller.moveCards(cardsToExile, Zone.EXILED, source, game);
                newBluePrint = new PermanentCard(copyFromCard, source.getControllerId(), game);
                newBluePrint.assignNewId();
                CopyApplier applier = new DimirDoppelgangerCopyApplier();
                applier.apply(game, newBluePrint, source, dimirDoppelganger.getId());
                CopyEffect copyEffect = new CopyEffect(Duration.Custom, newBluePrint, dimirDoppelganger.getId());
                copyEffect.newId();
                copyEffect.setApplier(applier);
                Ability newAbility = source.copy();
                copyEffect.init(newAbility, game);
                game.addEffect(copyEffect, newAbility);
            }
            return true;
        }
        return false;
    }
}

class DimirDoppelgangerCopyApplier extends CopyApplier {

    @Override
    public boolean apply(Game game, MageObject blueprint, Ability source, UUID copyToObjectId) {
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DimirDoppelgangerEffect(), new ManaCostsImpl<>("{1}{U}{B}"));
        ability.addTarget(new TargetCardInGraveyard(new FilterCreatureCard("creature card in a graveyard")));
        blueprint.getAbilities().add(ability);
        return true;
    }
}
