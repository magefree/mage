/*
 *  Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without modification, are
 *  permitted provided that the following conditions are met:
 *
 *     1. Redistributions of source code must retain the above copyright notice, this list of
 *        conditions and the following disclaimer.
 *
 *     2. Redistributions in binary form must reproduce the above copyright notice, this list
 *        of conditions and the following disclaimer in the documentation and/or other materials
 *        provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *  The views and conclusions contained in the software and documentation are those of the
 *  authors and should not be interpreted as representing official policies, either expressed
 *  or implied, of BetaSteward_at_googlemail.com.
 */
package mage.sets.commander2014;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.CanBeYourCommanderAbility;
import mage.abilities.common.PlanswalkerEntersWithLoyalityCountersAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.KorSoldierToken;
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
public class NahiriTheLithomancer extends CardImpl {

    public NahiriTheLithomancer(UUID ownerId) {
        super(ownerId, 10, "Nahiri, the Lithomancer", Rarity.MYTHIC, new CardType[]{CardType.PLANESWALKER}, "{3}{W}{W}");
        this.expansionSetCode = "C14";
        this.subtype.add("Nahiri");

        this.addAbility(new PlanswalkerEntersWithLoyalityCountersAbility(3));

        // +2: Put a 1/1 white Kor Soldier creature token onto the battlefield. You may attach an Equipment you control to it.
        this.addAbility(new LoyaltyAbility(new NahiriTheLithomancerFirstAbilityEffect(), 2));

        // -2: You may put an Equipment card from your hand or graveyard onto the battlefield.
        this.addAbility(new LoyaltyAbility(new NahiriTheLithomancerSecondAbilityEffect(), -2));

        // -10: Put a colorless Equipment artifact token named Stoneforged Blade onto the battlefield. It has indestructible, "Equipped creature gets +5/+5 and has double strike," and equip {0}.
        Effect effect = new CreateTokenEffect(new NahiriTheLithomancerEquipmentToken());
        effect.setText("Put a colorless Equipment artifact token named Stoneforged Blade onto the battlefield. It has indestructible, \"Equipped creature gets +5/+5 and has double strike,\" and equip {0}");
        this.addAbility(new LoyaltyAbility(effect, -10));

        // Nahiri, the Lithomancer can be your commander.
        this.addAbility(CanBeYourCommanderAbility.getInstance());
    }

    public NahiriTheLithomancer(final NahiriTheLithomancer card) {
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
        filter.add(new SubtypePredicate("Equipment"));
    }

    NahiriTheLithomancerFirstAbilityEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "Put a 1/1 white Kor Soldier creature token onto the battlefield. You may attach an Equipment you control to it";
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
            if (token.putOntoBattlefield(1, game, source.getSourceId(), source.getControllerId())) {
                for (UUID tokenId : token.getLastAddedTokenIds()) {
                    Permanent tokenPermanent = game.getPermanent(tokenId);
                    if (tokenPermanent != null) {
                        //TODO: Make sure the Equipment can legally enchant the token, preferably on targetting.
                        Target target = new TargetControlledPermanent(0, 1, filter, true);
                        if (target.canChoose(source.getSourceId(), controller.getId(), game)
                                && controller.chooseUse(outcome, "Attach an Equipment you control to the created " + tokenPermanent.getIdName() + "?", source, game)) {
                            if (target.choose(Outcome.Neutral, source.getControllerId(), source.getSourceId(), game)) {
                                Permanent equipmentPermanent = game.getPermanent(target.getFirstTarget());
                                if (equipmentPermanent != null) {
                                    Permanent attachedTo = game.getPermanent(equipmentPermanent.getAttachedTo());
                                    if (attachedTo != null) {
                                        attachedTo.removeAttachment(equipmentPermanent.getId(), game);
                                    }
                                    tokenPermanent.addAttachment(equipmentPermanent.getId(), game);
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
        filter.add(new SubtypePredicate("Equipment"));
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
                controller.choose(outcome, target, source.getSourceId(), game);
                Card card = controller.getHand().get(target.getFirstTarget(), game);
                if (card != null) {
                    controller.moveCards(card, Zone.BATTLEFIELD, source, game);
                }
            } else {
                Target target = new TargetCardInYourGraveyard(0, 1, filter);
                target.choose(Outcome.PutCardInPlay, source.getControllerId(), source.getSourceId(), game);
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

class NahiriTheLithomancerEquipmentToken extends Token {

    NahiriTheLithomancerEquipmentToken() {
        super("Stoneforged Blade", "colorless Equipment artifact token named Stoneforged Blade with indestructible, \"Equipped creature gets +5/+5 and has double strike,\" and equip {0}");
        cardType.add(CardType.ARTIFACT);
        subtype.add("Equipment");

        this.addAbility(IndestructibleAbility.getInstance());

        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEquippedEffect(5, 5));
        ability.addEffect(new GainAbilityAttachedEffect(DoubleStrikeAbility.getInstance(), AttachmentType.EQUIPMENT, Duration.WhileOnBattlefield, "and has double strike"));
        this.addAbility(ability);

        this.addAbility(new EquipAbility(Outcome.BoostCreature, new GenericManaCost(0)));
    }
}
