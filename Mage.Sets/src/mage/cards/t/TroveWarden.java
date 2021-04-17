package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.common.LandfallAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileTargetForSourceEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.common.FilterPermanentCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.ExileZone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TroveWarden extends CardImpl {

    private static final FilterCard filter
            = new FilterPermanentCard("permanent card with mana value 3 or less from your graveyard");

    static {
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, 4));
    }

    public TroveWarden(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}{W}");

        this.subtype.add(SubType.CAT);
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Landfall — Whenever a land enters the battlefield under your control, exile target permanent card with converted mana cost 3 or less from your graveyard.
        Ability ability = new LandfallAbility(new ExileTargetForSourceEffect());
        ability.addTarget(new TargetCardInYourGraveyard(filter));
        this.addAbility(ability);

        // When Trove Warden dies, put each permanent card exiled with it onto the battlefield under the control of that card's owner.
        this.addAbility(new DiesSourceTriggeredAbility(new TroveWardenEffect()));
    }

    private TroveWarden(final TroveWarden card) {
        super(card);
    }

    @Override
    public TroveWarden copy() {
        return new TroveWarden(this);
    }
}

class TroveWardenEffect extends OneShotEffect {

    TroveWardenEffect() {
        super(Outcome.Benefit);
        staticText = "put each permanent card exiled with it onto the battlefield under the control of that card's owner";
    }

    private TroveWardenEffect(final TroveWardenEffect effect) {
        super(effect);
    }

    @Override
    public TroveWardenEffect copy() {
        return new TroveWardenEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent permanent = (Permanent) getValue("permanentLeftBattlefield");
        if (controller == null || permanent == null) {
            return false;
        }
        ExileZone exileZone = game.getExile().getExileZone(CardUtil.getExileZoneId(
                game, permanent.getId(), permanent.getZoneChangeCounter(game)
        ));
        if (exileZone == null) {
            return true;
        }
        return controller.moveCards(
                exileZone.getCards(game), Zone.BATTLEFIELD, source, game,
                false, false, true, null
        );
    }
}
