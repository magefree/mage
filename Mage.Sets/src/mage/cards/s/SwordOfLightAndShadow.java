package mage.cards.s;

import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.DealsDamageToAPlayerAttachedTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author Loki
 */
public final class SwordOfLightAndShadow extends CardImpl {

    public SwordOfLightAndShadow(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");
        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +2/+2 and has protection from white and from black.
        Ability ability = new SimpleStaticAbility(new BoostEquippedEffect(2, 2));
        ability.addEffect(new GainAbilityAttachedEffect(
                ProtectionAbility.from(ObjectColor.WHITE, ObjectColor.BLACK), AttachmentType.EQUIPMENT
        ).setText("and has protection from white and from black"));
        this.addAbility(ability);

        // Whenever equipped creature deals combat damage to a player, you gain 3 life and you may return up to one target creature card from your graveyard to your hand.
        ability = new DealsDamageToAPlayerAttachedTriggeredAbility(
                new GainLifeEffect(3), "equipped creature", false
        );
        ability.addEffect(new SwordOfLightAndShadowEffect());
        ability.addTarget(new TargetCardInYourGraveyard(
                0, 1, StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD
        ));
        this.addAbility(ability);

        // Equip {2}
        this.addAbility(new EquipAbility(Outcome.AddAbility, new GenericManaCost(2), false));
    }

    private SwordOfLightAndShadow(final SwordOfLightAndShadow card) {
        super(card);
    }

    @Override
    public SwordOfLightAndShadow copy() {
        return new SwordOfLightAndShadow(this);
    }
}

class SwordOfLightAndShadowEffect extends OneShotEffect {

    public SwordOfLightAndShadowEffect() {
        super(Outcome.ReturnToHand);
        staticText = "and you may return up to one target creature card from your graveyard to your hand";
    }

    public SwordOfLightAndShadowEffect(final SwordOfLightAndShadowEffect effect) {
        super(effect);
    }

    @Override
    public SwordOfLightAndShadowEffect copy() {
        return new SwordOfLightAndShadowEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Card card = game.getCard(targetPointer.getFirst(game, source));
        return controller != null && card != null && controller.chooseUse(
                outcome, "Return " + card.getName() + " from your graveyard to your hand?", source, game
        ) && controller.moveCards(card, Zone.HAND, source, game);
    }
}
