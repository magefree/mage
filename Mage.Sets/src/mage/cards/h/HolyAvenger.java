package mage.cards.h;

import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageEquippedTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.abilities.keyword.EquipAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.predicate.card.AuraCardCanAttachToPermanentId;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInHand;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HolyAvenger extends CardImpl {

    public HolyAvenger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}{W}");

        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature has double strike.
        this.addAbility(new SimpleStaticAbility(new GainAbilityAttachedEffect(
                DoubleStrikeAbility.getInstance(), AttachmentType.EQUIPMENT
        )));

        // Whenever equipped creature deals combat damage, you may put an Aura card from your hand onto the battlefield attached to it.
        this.addAbility(new DealsCombatDamageEquippedTriggeredAbility(new HolyAvengerEffect()));

        // Equip {2}{W}
        this.addAbility(new EquipAbility(Outcome.AddAbility, new ManaCostsImpl<>("{2}{W}"), false));
    }

    private HolyAvenger(final HolyAvenger card) {
        super(card);
    }

    @Override
    public HolyAvenger copy() {
        return new HolyAvenger(this);
    }
}

class HolyAvengerEffect extends OneShotEffect {

    HolyAvengerEffect() {
        super(Outcome.Benefit);
        staticText = "you may put an Aura card from your hand onto the battlefield attached to it";
    }

    private HolyAvengerEffect(final HolyAvengerEffect effect) {
        super(effect);
    }

    @Override
    public HolyAvengerEffect copy() {
        return new HolyAvengerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent sourcePermanent = source.getSourcePermanentOrLKI(game);
        if (player == null || sourcePermanent == null) {
            return false;
        }
        Permanent permanent = game.getPermanent(sourcePermanent.getAttachedTo());
        if (permanent == null) {
            return false;
        }
        FilterCard filter = new FilterCard("an Aura than can enchant " + permanent.getName());
        filter.add(SubType.AURA.getPredicate());
        filter.add(new AuraCardCanAttachToPermanentId(permanent.getId()));
        TargetCardInHand target = new TargetCardInHand(0, 1, filter);
        player.choose(Outcome.PutCardInPlay, player.getHand(), target, game);
        Card card = game.getCard(target.getFirstTarget());
        if (card == null) {
            return false;
        }
        game.getState().setValue("attachTo:" + card.getId(), permanent);
        player.moveCards(card, Zone.BATTLEFIELD, source, game);
        if (!permanent.addAttachment(card.getId(), source, game)) {
            return false;
        }
        game.informPlayers(player.getLogName() + " put " + card.getLogName()
                + " on the battlefield attached to " + permanent.getLogName() + '.');
        return true;
    }
}
