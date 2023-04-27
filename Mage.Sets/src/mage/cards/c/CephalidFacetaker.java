package mage.cards.c;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.combat.CantBeBlockedSourceEffect;
import mage.abilities.keyword.CantBeBlockedSourceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.util.functions.CopyApplier;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CephalidFacetaker extends CardImpl {

    public CephalidFacetaker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.subtype.add(SubType.CEPHALID);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // Cephalid Facetaker can't be blocked.
        this.addAbility(new CantBeBlockedSourceAbility());

        // At the beginning of combat on your turn, you may have Cephalid Facetaker become a copy of another target creature until end of turn, except its a 1/4 and has "This creature can't be blocked."
        Ability ability = new BeginningOfCombatTriggeredAbility(
                new CephalidFacetakerEffect(), TargetController.YOU, true
        );
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_CONTROLLED_ANOTHER_CREATURE));
        this.addAbility(ability);
    }

    private CephalidFacetaker(final CephalidFacetaker card) {
        super(card);
    }

    @Override
    public CephalidFacetaker copy() {
        return new CephalidFacetaker(this);
    }
}

class CephalidFacetakerEffect extends OneShotEffect {

    private static final CopyApplier copyApplier = new CopyApplier() {
        @Override
        public boolean apply(Game game, MageObject blueprint, Ability source, UUID targetObjectId) {
            blueprint.getPower().setModifiedBaseValue(1);
            blueprint.getToughness().setModifiedBaseValue(4);
            blueprint.getAbilities().add(new SimpleStaticAbility(
                    new CantBeBlockedSourceEffect().setText("this creature can't be blocked")
            ));
            return true;
        }
    };

    CephalidFacetakerEffect() {
        super(Outcome.Benefit);
        staticText = "you may have {this} become a copy of another target creature until end of turn, " +
                "except it's 1/4 and has \"This creature can't be blocked.\"";
    }

    private CephalidFacetakerEffect(final CephalidFacetakerEffect effect) {
        super(effect);
    }

    @Override
    public CephalidFacetakerEffect copy() {
        return new CephalidFacetakerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent sourcePermanent = source.getSourcePermanentIfItStillExists(game);
        Permanent creature = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (sourcePermanent == null || creature == null) {
            return false;
        }
        game.copyPermanent(Duration.EndOfTurn, creature, sourcePermanent.getId(), source, copyApplier);
        return true;
    }
}
