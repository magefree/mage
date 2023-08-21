package mage.cards.t;

import mage.ApprovingObject;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.replacement.ThatSpellGraveyardExileReplacementEffect;
import mage.abilities.keyword.BushidoAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public final class ToshiroUmezawa extends CardImpl {
    private static final FilterCard filterInstant = new FilterCard("instant card from your graveyard");

    static {
        filterInstant.add(CardType.INSTANT.getPredicate());
    }

    public ToshiroUmezawa(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}{B}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SAMURAI);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Bushido 1
        this.addAbility(new BushidoAbility(1));
        // Whenever a creature an opponent controls dies, you may cast target instant card from your graveyard. If that card would be put into a graveyard this turn, exile it instead.
        Ability ability = new DiesCreatureTriggeredAbility(new ToshiroUmezawaEffect(), true, StaticFilters.FILTER_OPPONENTS_PERMANENT_A_CREATURE);
        ability.addTarget(new TargetCardInYourGraveyard(1, 1, filterInstant));
        this.addAbility(ability);

    }

    private ToshiroUmezawa(final ToshiroUmezawa card) {
        super(card);
    }

    @Override
    public ToshiroUmezawa copy() {
        return new ToshiroUmezawa(this);
    }
}

class ToshiroUmezawaEffect extends OneShotEffect {

    public ToshiroUmezawaEffect() {
        super(Outcome.Benefit);
        this.staticText = "you may cast target instant card from your graveyard. "
                + ThatSpellGraveyardExileReplacementEffect.RULE_A;
    }

    public ToshiroUmezawaEffect(final ToshiroUmezawaEffect effect) {
        super(effect);
    }

    @Override
    public ToshiroUmezawaEffect copy() {
        return new ToshiroUmezawaEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Card card = game.getCard(getTargetPointer().getFirst(game, source));
        if (controller == null || card == null) {
            return false;
        }
        if (controller.getGraveyard().contains(card.getId())) { // must be in graveyard
            game.getState().setValue("PlayFromNotOwnHandZone" + card.getId(), Boolean.TRUE);
            controller.cast(controller.chooseAbilityForCast(card, game, false),
                    game, false, new ApprovingObject(source, game));
            game.getState().setValue("PlayFromNotOwnHandZone" + card.getId(), null);
            ContinuousEffect effect = new ThatSpellGraveyardExileReplacementEffect(false);
            effect.setTargetPointer(new FixedTarget(card, game));
            game.addEffect(effect, source);
        }
        return true;
    }
}
