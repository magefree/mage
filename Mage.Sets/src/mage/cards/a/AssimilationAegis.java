package mage.cards.a;

import mage.abilities.Ability;
import mage.abilities.common.AttachedToCreatureSourceTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CopyEffect;
import mage.abilities.effects.common.ExileUntilSourceLeavesEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInExile;
import mage.target.common.TargetCreaturePermanent;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class AssimilationAegis extends CardImpl {

    public AssimilationAegis(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}{W}{U}");

        this.subtype.add(SubType.EQUIPMENT);

        // When Assimilation Aegis enters the battlefield, exile up to one target creature until Assimilation Aegis leaves the battlefield.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ExileUntilSourceLeavesEffect());
        ability.addTarget(new TargetCreaturePermanent(0, 1));
        this.addAbility(ability);

        // Whenever Assimilation Aegis becomes attached to a creature, for as long as Assimilation Aegis remains attached to it, that creature becomes a copy of a creature card exiled with Assimilation Aegis.
        this.addAbility(new AttachedToCreatureSourceTriggeredAbility(new AssimilationAegisEffect(), false));

        // Equip {2}
        this.addAbility(new EquipAbility(Outcome.BoostCreature, new GenericManaCost(2)));
    }

    private AssimilationAegis(final AssimilationAegis card) {
        super(card);
    }

    @Override
    public AssimilationAegis copy() {
        return new AssimilationAegis(this);
    }
}

// Similar to Blade of Shared Souls.
class AssimilationAegisEffect extends OneShotEffect {

    AssimilationAegisEffect() {
        super(Outcome.Benefit);
        staticText = "for as long as {this} remains attached to it, " +
                "that creature becomes a copy of a creature card exiled with {this}";
    }

    private AssimilationAegisEffect(final AssimilationAegisEffect effect) {
        super(effect);
    }

    @Override
    public AssimilationAegisEffect copy() {
        return new AssimilationAegisEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent attachedPermanent = (Permanent) getValue("attachedPermanent");
        Permanent equipment = source.getSourcePermanentIfItStillExists(game);
        if (attachedPermanent == null
                || equipment == null
                || !equipment.isAttachedTo(attachedPermanent.getId())) {
            return false;
        }
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        UUID exileId = CardUtil.getExileZoneId(game, source);
        TargetCard target = new TargetCardInExile(StaticFilters.FILTER_CARD_CREATURE, exileId);
        target.withNotTarget(true);
        if (!target.choose(Outcome.Benefit, player.getId(), source.getId(), source, game)) {
            return false;
        }
        Card copyCard = game.getCard(target.getFirstTarget());
        if (copyCard == null) {
            return false;
        }
        game.addEffect(new AssimilationAegisCopyEffect(copyCard, attachedPermanent), source);
        return true;
    }
}

// Similar to Blade of Shared Souls.
class AssimilationAegisCopyEffect extends CopyEffect {

    AssimilationAegisCopyEffect(Card copyCard, Permanent attachedPermanent) {
        super(Duration.Custom, copyCard, attachedPermanent.getId());
    }

    private AssimilationAegisCopyEffect(final AssimilationAegisCopyEffect effect) {
        super(effect);
    }

    @Override
    public AssimilationAegisCopyEffect copy() {
        return new AssimilationAegisCopyEffect(this);
    }

    @Override
    public boolean isInactive(Ability source, Game game) {
        if (super.isInactive(source, game)) {
            return true;
        }
        Permanent sourcePermanent = source.getSourcePermanentIfItStillExists(game);
        if (sourcePermanent == null || !sourcePermanent.isAttachedTo(this.copyToObjectId)) {
            return true;
        }
        return false;
    }
}