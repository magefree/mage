
package mage.cards.i;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.CastFromHandSourcePermanentCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileSourceEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.common.FilterPermanentCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.targetpointer.FixedTarget;
import mage.watchers.common.CastFromHandWatcher;

/**
 *
 * @author LevelX2
 */
public final class InameAsOne extends CardImpl {

    private static final FilterPermanentCard filter = new FilterPermanentCard("Spirit permanent card");

    static {
        filter.add(SubType.SPIRIT.getPredicate());
    }

    public InameAsOne(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{8}{B}{B}{G}{G}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(8);
        this.toughness = new MageInt(8);

        // When Iname as One enters the battlefield, if you cast it from your hand, you may search your library for a Spirit permanent card, put it onto the battlefield, then shuffle your library.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new EntersBattlefieldTriggeredAbility(new SearchLibraryPutInPlayEffect(new TargetCardInLibrary(0, 1, filter)), true),
                CastFromHandSourcePermanentCondition.instance,
                "When {this} enters the battlefield, if you cast it from your hand, you may search your library for a Spirit permanent card, put it onto the battlefield, then shuffle."),
                new CastFromHandWatcher());

        // When Iname as One dies, you may exile it. If you do, return target Spirit permanent card from your graveyard to the battlefield.
        Ability ability = new DiesSourceTriggeredAbility(new InameAsOneEffect(), false);
        ability.addTarget(new TargetCardInYourGraveyard(filter));
        this.addAbility(ability);
    }

    private InameAsOne(final InameAsOne card) {
        super(card);
    }

    @Override
    public InameAsOne copy() {
        return new InameAsOne(this);
    }
}

class InameAsOneEffect extends OneShotEffect {

    public InameAsOneEffect() {
        super(Outcome.Benefit);
        this.staticText = "you may exile it. If you do, return target Spirit permanent card from your graveyard to the battlefield";
    }

    public InameAsOneEffect(final InameAsOneEffect effect) {
        super(effect);
    }

    @Override
    public InameAsOneEffect copy() {
        return new InameAsOneEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source);
        Card targetCard = game.getCard(getTargetPointer().getFirst(game, source));
        if (controller != null && sourceObject != null && targetCard != null) {
            if (controller.chooseUse(outcome, "Exile " + sourceObject.getLogName() + " to return Spirit card?", source, game)) {
                // In a Commander game, you may send Iname to the Command Zone instead of exiling it during the resolution
                // of its ability. If you do, its ability still works. Iname's ability only requires that you attempted to
                // exile it, not that it actually gets to the exile zone. This is similar to how destroying a creature
                // (with, for example, Rest in Peace) doesn't necessarily ensure that creature will end up in the graveyard;
                // it just so happens that the action of exiling something and the exile zone both use the same word: "exile".
                Effect effect = new ReturnFromGraveyardToBattlefieldTargetEffect();
                effect.setTargetPointer(new FixedTarget(targetCard.getId(), targetCard.getZoneChangeCounter(game)));
                new ExileSourceEffect().apply(game, source);
                return effect.apply(game, source);
            }
            return true;
        }
        return false;
    }
}
