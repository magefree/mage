
package mage.cards.n;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.CanBeYourCommanderAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.KorSoldierToken;
import mage.game.permanent.token.NahiriTheLithomancerEquipmentToken;
import mage.game.permanent.token.Token;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCardInHand;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author emerald000
 */
public final class NahiriTheLithomancer extends CardImpl {

    public NahiriTheLithomancer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{3}{W}{W}");
        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.NAHIRI);

        this.setStartingLoyalty(3);

        // +2: Create a 1/1 white Kor Soldier creature token. You may attach an Equipment you control to it.
        this.addAbility(new LoyaltyAbility(new NahiriTheLithomancerFirstAbilityEffect(), 2));

        // -2: You may put an Equipment card from your hand or graveyard onto the battlefield.
        this.addAbility(new LoyaltyAbility(new NahiriTheLithomancerSecondAbilityEffect(), -2));

        // -10: Create a colorless Equipment artifact token named Stoneforged Blade. It has indestructible, "Equipped creature gets +5/+5 and has double strike," and equip {0}.
        Effect effect = new CreateTokenEffect(new NahiriTheLithomancerEquipmentToken());
        effect.setText("Create a colorless Equipment artifact token named Stoneforged Blade. It has indestructible, \"Equipped creature gets +5/+5 and has double strike,\" and equip {0}");
        this.addAbility(new LoyaltyAbility(effect, -10));

        // Nahiri, the Lithomancer can be your commander.
        this.addAbility(CanBeYourCommanderAbility.getInstance());
    }

    private NahiriTheLithomancer(final NahiriTheLithomancer card) {
        super(card);
    }

    @Override
    public NahiriTheLithomancer copy() {
        return new NahiriTheLithomancer(this);
    }
}

class NahiriTheLithomancerFirstAbilityEffect extends OneShotEffect {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("an Equipment you control");

    static {
        filter.add(SubType.EQUIPMENT.getPredicate());
    }

    NahiriTheLithomancerFirstAbilityEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "Create a 1/1 white Kor Soldier creature token. You may attach an Equipment you control to it";
    }

    NahiriTheLithomancerFirstAbilityEffect(final NahiriTheLithomancerFirstAbilityEffect effect) {
        super(effect);
    }

    @Override
    public NahiriTheLithomancerFirstAbilityEffect copy() {
        return new NahiriTheLithomancerFirstAbilityEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Token token = new KorSoldierToken();
            if (token.putOntoBattlefield(1, game, source, source.getControllerId())) {
                for (UUID tokenId : token.getLastAddedTokenIds()) {
                    Permanent tokenPermanent = game.getPermanent(tokenId);
                    if (tokenPermanent != null) {
                        //TODO: Make sure the Equipment can legally enchant the token, preferably on targetting.
                        Target target = new TargetControlledPermanent(0, 1, filter, true);
                        if (target.canChoose(controller.getId(), source, game)
                                && controller.chooseUse(outcome, "Attach an Equipment you control to the created " + tokenPermanent.getIdName() + '?', source, game)) {
                            if (target.choose(Outcome.Neutral, source.getControllerId(), source.getSourceId(), source, game)) {
                                Permanent equipmentPermanent = game.getPermanent(target.getFirstTarget());
                                if (equipmentPermanent != null) {
                                    Permanent attachedTo = game.getPermanent(equipmentPermanent.getAttachedTo());
                                    if (attachedTo != null) {
                                        attachedTo.removeAttachment(equipmentPermanent.getId(), source, game);
                                    }
                                    tokenPermanent.addAttachment(equipmentPermanent.getId(), source, game);
                                }
                            }
                        }
                    }
                }
            }
            return true;

        }
        return false;
    }
}

class NahiriTheLithomancerSecondAbilityEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCard("an Equipment");

    static {
        filter.add(SubType.EQUIPMENT.getPredicate());
    }

    NahiriTheLithomancerSecondAbilityEffect() {
        super(Outcome.PutCardInPlay);
        this.staticText = "You may put an Equipment card from your hand or graveyard onto the battlefield";
    }

    NahiriTheLithomancerSecondAbilityEffect(final NahiriTheLithomancerSecondAbilityEffect effect) {
        super(effect);
    }

    @Override
    public NahiriTheLithomancerSecondAbilityEffect copy() {
        return new NahiriTheLithomancerSecondAbilityEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            if (controller.chooseUse(Outcome.PutCardInPlay, "Put an Equipment from hand? (No = from graveyard)", source, game)) {
                Target target = new TargetCardInHand(0, 1, filter);
                controller.choose(outcome, target, source, game);
                Card card = controller.getHand().get(target.getFirstTarget(), game);
                if (card != null) {
                    controller.moveCards(card, Zone.BATTLEFIELD, source, game);
                }
            } else {
                Target target = new TargetCardInYourGraveyard(0, 1, filter);
                target.choose(Outcome.PutCardInPlay, source.getControllerId(), source.getSourceId(), source, game);
                Card card = controller.getGraveyard().get(target.getFirstTarget(), game);
                if (card != null) {
                    controller.moveCards(card, Zone.BATTLEFIELD, source, game);
                }
            }
            return true;
        }
        return false;
    }
}
