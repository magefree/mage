package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.common.CastSpellPaidBySourceTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.DescendCondition;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CopyEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.MillCardsControllerEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.mana.BlueManaAbility;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.*;
import mage.filter.FilterSpell;
import mage.filter.StaticFilters;
import mage.filter.predicate.mageobject.PermanentPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentCard;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public class TheEverflowingWell extends TransformingDoubleFacedCard {

    private static final FilterSpell filter = new FilterSpell("a permanent spell");

    static {
        filter.add(PermanentPredicate.instance);
    }

    public TheEverflowingWell(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.ARTIFACT}, new SubType[]{}, "{2}{U}",
                "The Myriad Pools",
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.ARTIFACT, CardType.LAND}, new SubType[]{}, ""
        );

        // The Everflowing Well
        // When the Everflowing Well enters the battlefield, mill two cards, then draw two cards.
        Ability entersAbility = new EntersBattlefieldTriggeredAbility(new MillCardsControllerEffect(2));
        entersAbility.addEffect(new DrawCardSourceControllerEffect(2).concatBy(", then"));
        this.getLeftHalfCard().addAbility(entersAbility);

        // Descend 8 -- At the beginning of your upkeep, if there are eight or more permanent cards in your graveyard, transform The Everflowing Well.
        this.getLeftHalfCard().addAbility(new BeginningOfUpkeepTriggeredAbility(new TransformSourceEffect())
                .withInterveningIf(DescendCondition.EIGHT).setAbilityWord(AbilityWord.DESCEND_8).addHint(DescendCondition.getHint()));

        // The Myriad Pools
        // {T}: Add {U}.
        this.getRightHalfCard().addAbility(new BlueManaAbility());

        // Whenever you cast a permanent spell using mana produced by The Myriad Pools, up to one other target permanent you control becomes a copy of that spell until end of turn.
        Ability ability = new CastSpellPaidBySourceTriggeredAbility(new TheMyriadPoolsCopyEffect(), filter, false);
        ability.addTarget(new TargetPermanent(0, 1, StaticFilters.FILTER_CONTROLLED_ANOTHER_TARGET_PERMANENT));
        this.getRightHalfCard().addAbility(ability);
    }

    private TheEverflowingWell(final TheEverflowingWell card) {
        super(card);
    }

    @Override
    public TheEverflowingWell copy() {
        return new TheEverflowingWell(this);
    }

}

class TheMyriadPoolsCopyEffect extends OneShotEffect {

    TheMyriadPoolsCopyEffect() {
        super(Outcome.Neutral);
        this.staticText = "up to one other target permanent you control becomes a copy of that spell until end of turn";
    }

    private TheMyriadPoolsCopyEffect(final TheMyriadPoolsCopyEffect effect) {
        super(effect);
    }

    @Override
    public TheMyriadPoolsCopyEffect copy() {
        return new TheMyriadPoolsCopyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent targetPermanentToCopyTo = game.getPermanent(getTargetPointer().getFirst(game, source));
        Player controller = game.getPlayer(source.getControllerId());
        Object spell = getValue("spellCast");
        if (controller == null || targetPermanentToCopyTo == null || !(spell instanceof Spell)) {
            return false;
        }
        Permanent newBluePrint = new PermanentCard(((Spell) spell).getCard(), source.getControllerId(), game);
        newBluePrint.assignNewId();
        CopyEffect copyEffect = new CopyEffect(Duration.EndOfTurn, newBluePrint, targetPermanentToCopyTo.getId());
        Ability newAbility = source.copy();
        copyEffect.init(newAbility, game);
        game.addEffect(copyEffect, newAbility);
        return true;
    }
}
