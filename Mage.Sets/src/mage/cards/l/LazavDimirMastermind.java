
package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.PutCardIntoGraveFromAnywhereAllTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CopyEffect;
import mage.abilities.keyword.HexproofAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentCard;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;
import mage.util.functions.CopyApplier;

/**
 *
 * @author jeffwadsworth
 */
public final class LazavDimirMastermind extends CardImpl {

    public LazavDimirMastermind(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{U}{U}{B}{B}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SHAPESHIFTER);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Hexproof
        this.addAbility(HexproofAbility.getInstance());

        // Whenever a creature card is put into an opponent's graveyard from anywhere, you may have Lazav, Dimir Mastermind become a copy of that card except its name is Lazav, Dimir Mastermind, it's legendary in addition to its other types, and it has hexproof and this ability.
        this.addAbility(new PutCardIntoGraveFromAnywhereAllTriggeredAbility(
                new LazavDimirMastermindEffect(), true,
                new FilterCreatureCard("a creature card"),
                TargetController.OPPONENT, SetTargetPointer.CARD));
    }

    private LazavDimirMastermind(final LazavDimirMastermind card) {
        super(card);
    }

    @Override
    public LazavDimirMastermind copy() {
        return new LazavDimirMastermind(this);
    }
}

class LazavDimirMastermindEffect extends OneShotEffect {

    LazavDimirMastermindEffect() {
        super(Outcome.Copy);
        staticText = "you may have {this} become a copy of that card, except its name is Lazav, Dimir Mastermind, it's legendary in addition to its other types, and it has hexproof and this ability";
    }

    LazavDimirMastermindEffect(final LazavDimirMastermindEffect effect) {
        super(effect);
    }

    @Override
    public LazavDimirMastermindEffect copy() {
        return new LazavDimirMastermindEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent lazavDimirMastermind = game.getPermanent(source.getSourceId());
        Permanent newBluePrint = null;
        if (controller != null
                && lazavDimirMastermind != null) {
            Card copyFromCard = game.getCard(((FixedTarget) getTargetPointer()).getTarget());
            if (copyFromCard != null) {
                newBluePrint = new PermanentCard(copyFromCard, source.getControllerId(), game);
                newBluePrint.assignNewId();
                CopyApplier applier = new LazavDimirMastermindCopyApplier();
                applier.apply(game, newBluePrint, source, lazavDimirMastermind.getId());
                CopyEffect copyEffect = new CopyEffect(Duration.Custom, newBluePrint, lazavDimirMastermind.getId());
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

class LazavDimirMastermindCopyApplier extends CopyApplier {

    @Override
    public boolean apply(Game game, MageObject blueprint, Ability source, UUID copyToObjectId) {
        Ability ability = new PutCardIntoGraveFromAnywhereAllTriggeredAbility(
                new LazavDimirMastermindEffect(), true,
                new FilterCreatureCard("a creature card"),
                TargetController.OPPONENT, SetTargetPointer.CARD);
        blueprint.getAbilities().add(ability);
        blueprint.setName("Lazav, Dimir Mastermind");
        blueprint.addSuperType(SuperType.LEGENDARY);
        blueprint.getAbilities().add(HexproofAbility.getInstance());
        return true;
    }
}
