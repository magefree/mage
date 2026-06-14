package mage.cards.s;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.cost.SpellsCostReductionControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledArtifactPermanent;
import mage.filter.predicate.other.AnotherTargetPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.util.functions.CopyApplier;

import java.util.UUID;

/**
 * @author PurpleCrowbar
 */
public final class ShuriWakandanInventor extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledArtifactPermanent("artifact you control");
    private static final FilterPermanent filter2;

    static {
        filter2 = filter.copy();
        filter.add(new AnotherTargetPredicate(1));
        filter2.add(new AnotherTargetPredicate(2));
    }

    public ShuriWakandanInventor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN, SubType.ARTIFICER, SubType.HERO);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Artifact spells you cast cost {1} less to cast.
        this.addAbility(new SimpleStaticAbility(new SpellsCostReductionControllerEffect(StaticFilters.FILTER_CARD_ARTIFACT, 1)
                .setText("Artifact spells you cast cost {1} less to cast")
        ));

        // {1}, {T}: Target artifact you control becomes a copy of a second target artifact you control until end of turn, except it isn't legendary. Activate only as a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(new ShuriWakandanInventorEffect(), new GenericManaCost(1));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetPermanent(filter).setTargetTag(1).withChooseHint("to become a copy"));
        ability.addTarget(new TargetPermanent(filter2).setTargetTag(2).withChooseHint("to be copied"));
        this.addAbility(ability);
    }

    private ShuriWakandanInventor(final ShuriWakandanInventor card) {
        super(card);
    }

    @Override
    public ShuriWakandanInventor copy() {
        return new ShuriWakandanInventor(this);
    }
}

class ShuriWakandanInventorEffect extends OneShotEffect {

    ShuriWakandanInventorEffect() {
        super(Outcome.Benefit);
        staticText = "Target artifact you control becomes a copy of a second target artifact you control until end of turn, except it isn't legendary";
    }

    private ShuriWakandanInventorEffect(final ShuriWakandanInventorEffect effect) {
        super(effect);
    }

    @Override
    public ShuriWakandanInventorEffect copy() {
        return new ShuriWakandanInventorEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent copyTo = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (copyTo == null) {
            return false;
        }
        Permanent copyFrom = game.getPermanentOrLKIBattlefield(source.getTargets().get(1).getFirstTarget());
        if (copyFrom == null) {
            return false;
        }
        game.copyPermanent(Duration.EndOfTurn, copyFrom, copyTo.getId(), source, new CopyApplier() {
            @Override
            public boolean apply(Game game, MageObject blueprint, Ability source, UUID targetObjectId) {
                blueprint.removeSuperType(SuperType.LEGENDARY);
                return true;
            }
        });
        return true;
    }
}
