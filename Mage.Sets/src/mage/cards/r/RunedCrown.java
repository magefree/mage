package mage.cards.r;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInHand;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;
import mage.abilities.costs.mana.GenericManaCost;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 * @author TheElk801
 */
public final class RunedCrown extends CardImpl {

    public RunedCrown(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        this.subtype.add(SubType.EQUIPMENT);

        // When Runed Crown enters the battlefield, you may search your library, hand, and/or graveyard for a Rune card and put it onto the battlefield attached to Runed Crown. If you search your library this way, shuffle it.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new RunedCrownEffect()));

        // Equipped creature gets +1/+1.
        this.addAbility(new SimpleStaticAbility(new BoostEquippedEffect(1, 1)));

        // Equip {2}
        this.addAbility(new EquipAbility(Outcome.BoostCreature, new GenericManaCost(2), new TargetControlledCreaturePermanent(), false));
    }

    private RunedCrown(final RunedCrown card) {
        super(card);
    }

    @Override
    public RunedCrown copy() {
        return new RunedCrown(this);
    }
}

class RunedCrownEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCard("a Rune card");

    static {
        filter.add(SubType.RUNE.getPredicate());
    }

    RunedCrownEffect() {
        super(Outcome.Benefit);
        staticText = "you may search your library, hand, and/or graveyard for a Rune card " +
                "and put it onto the battlefield attached to {this}. If you search your library this way, shuffle";
    }

    private RunedCrownEffect(final RunedCrownEffect effect) {
        super(effect);
    }

    @Override
    public RunedCrownEffect copy() {
        return new RunedCrownEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Card card = null;
        Zone zone = null;
        if (controller.chooseUse(Outcome.Neutral, "Search your graveyard for a Rune card?", source, game)) {
            TargetCardInYourGraveyard target = new TargetCardInYourGraveyard(filter);
            target.setNotTarget(true);
            if (controller.choose(Outcome.PutCardInPlay, controller.getGraveyard(), target, game)) {
                card = game.getCard(target.getFirstTarget());
                if (card != null) {
                    zone = Zone.GRAVEYARD;
                }
            }
        }
        if (card == null && controller.chooseUse(Outcome.Neutral, "Search your hand for a Rune card?", source, game)) {
            TargetCardInHand target = new TargetCardInHand(filter);
            if (controller.choose(Outcome.PutCardInPlay, controller.getHand(), target, game)) {
                card = game.getCard(target.getFirstTarget());
                if (card != null) {
                    zone = Zone.HAND;
                }
            }
        }
        if (card == null) {
            TargetCardInLibrary target = new TargetCardInLibrary(filter);
            if (controller.searchLibrary(target, source, game)) {
                card = game.getCard(target.getFirstTarget());
                if (card != null) {
                    zone = Zone.LIBRARY;
                }
            }
            controller.shuffleLibrary(source, game);
        }
        // aura card found - attach it
        if (card == null) {
            return true;
        }
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        if (permanent != null) {
            game.getState().setValue("attachTo:" + card.getId(), permanent);
        }
        controller.moveCards(card, Zone.BATTLEFIELD, source, game);
        if (permanent != null) {
            return permanent.addAttachment(card.getId(), source, game);
        }
        return true;
    }
}
