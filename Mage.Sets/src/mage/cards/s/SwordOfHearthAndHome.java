package mage.cards.s;

import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.DealsDamageToAPlayerAttachedTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SwordOfHearthAndHome extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("creature you own");

    static {
        filter.add(TargetController.YOU.getOwnerPredicate());
    }

    public SwordOfHearthAndHome(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +2/+2 and has protection from green and from white.
        Ability ability = new SimpleStaticAbility(new BoostEquippedEffect(2, 2));
        ability.addEffect(new GainAbilityAttachedEffect(
                ProtectionAbility.from(ObjectColor.GREEN, ObjectColor.WHITE), AttachmentType.EQUIPMENT
        ).setText("and has protection from green and from white"));
        this.addAbility(ability);

        // Whenever equipped creature deals combat damage to a player, exile up to one target creature you own, then search your library for a basic land card. Put both cards onto the battlefield under your control, then shuffle.
        ability = new DealsDamageToAPlayerAttachedTriggeredAbility(
                new SwordOfHearthAndHomeEffect(), "equipped creature", false
        );
        ability.addTarget(new TargetPermanent(0, 1, filter));
        this.addAbility(ability);

        // Equip {2}
        this.addAbility(new EquipAbility(Outcome.Benefit, new GenericManaCost(2), false));
    }

    private SwordOfHearthAndHome(final SwordOfHearthAndHome card) {
        super(card);
    }

    @Override
    public SwordOfHearthAndHome copy() {
        return new SwordOfHearthAndHome(this);
    }
}

class SwordOfHearthAndHomeEffect extends OneShotEffect {

    SwordOfHearthAndHomeEffect() {
        super(Outcome.Benefit);
        staticText = "exile up to one target creature you own, then search your library for a basic land card. " +
                "Put both cards onto the battlefield under your control, then shuffle";
    }

    private SwordOfHearthAndHomeEffect(final SwordOfHearthAndHomeEffect effect) {
        super(effect);
    }

    @Override
    public SwordOfHearthAndHomeEffect copy() {
        return new SwordOfHearthAndHomeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Cards cards = new CardsImpl();
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent != null) {
            player.moveCards(permanent, Zone.EXILED, source, game);
            cards.add(permanent);
        }
        TargetCardInLibrary target = new TargetCardInLibrary(StaticFilters.FILTER_CARD_BASIC_LAND);
        player.searchLibrary(target, source, game);
        cards.add(player.getLibrary().getCard(target.getFirstTarget(), game));
        player.moveCards(cards, Zone.BATTLEFIELD, source, game);
        player.shuffleLibrary(source, game);
        return true;
    }
}
