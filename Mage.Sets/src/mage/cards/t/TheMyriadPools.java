package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.common.CastSpellPaidBySourceTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CopyEffect;
import mage.abilities.mana.BlueManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SuperType;
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
public class TheMyriadPools extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("a permanent spell");

    static {
        filter.add(PermanentPredicate.instance);
    }

    public TheMyriadPools(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.LAND}, null);
        this.supertype.add(SuperType.LEGENDARY);

        // this is the second face of The Everflowing Well
        this.nightCard = true;

        // {T}: Add {U}.
        this.addAbility(new BlueManaAbility());

        // Whenever you cast a permanent spell using mana produced by The Myriad Pools, up to one other target permanent you control becomes a copy of that spell until end of turn.
        Ability ability = new CastSpellPaidBySourceTriggeredAbility(new TheMyriadPoolsCopyEffect(), filter, false);
        ability.addTarget(new TargetPermanent(0, 1, StaticFilters.FILTER_CONTROLLED_ANOTHER_TARGET_PERMANENT));
        this.addAbility(ability);
    }

    private TheMyriadPools(final TheMyriadPools card) {
        super(card);
    }

    @Override
    public TheMyriadPools copy() {
        return new TheMyriadPools(this);
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
        Permanent newBluePrint = new PermanentCard(((Spell)spell).getCard(), source.getControllerId(), game);
        newBluePrint.assignNewId();
        CopyEffect copyEffect = new CopyEffect(Duration.EndOfTurn, newBluePrint, targetPermanentToCopyTo.getId());
        Ability newAbility = source.copy();
        copyEffect.init(newAbility, game);
        game.addEffect(copyEffect, newAbility);
        return true;
    }
}
