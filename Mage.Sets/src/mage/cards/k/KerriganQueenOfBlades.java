package mage.cards.k;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.PlanswalkerEntersWithLoyalityCountersAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.SacrificeOpponentsEffect;
import mage.abilities.effects.common.continuous.BecomesChosenCreatureTypeTargetEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureTargetEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureTypeTargetEffect;
import mage.abilities.effects.common.continuous.GainControlAllEffect;
import mage.constants.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.custom.CreatureToken;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author NinthWorld
 */
public final class KerriganQueenOfBlades extends CardImpl {

    private static final FilterPermanent filterAllZerg = new FilterPermanent("all Zerg");
    private static final FilterControlledPermanent filterZergYouControlled = new FilterControlledPermanent("Zerg you control");

    static {
        filterAllZerg.add(new SubtypePredicate(SubType.ZERG));
        filterZergYouControlled.add(new SubtypePredicate(SubType.ZERG));
    }

    public KerriganQueenOfBlades(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{3}{B}{G}");
        
        this.subtype.add(SubType.KERRIGAN);

        this.addAbility(new PlanswalkerEntersWithLoyalityCountersAbility(4));

        // +1: Target permanent becomes a Zerg creature with base power and toughness 3/3 in addition to its other types.
        Ability ability = new LoyaltyAbility(new KerriganQueenOfBladesFirstEffect(), 1);
        ability.addTarget(new TargetPermanent());
        this.addAbility(ability);

        // -3: Destroy target creature.
        ability = new LoyaltyAbility(new DestroyTargetEffect(), -3);
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

        // -7: Gain control of all Zerg, then each opponent sacrifices a permanent for each Zerg you control.
        ability = new LoyaltyAbility(new GainControlAllEffect(Duration.EndOfGame, filterAllZerg)
                .setText("Gain control of all Zerg, "), -7);
        ability.addEffect(new SacrificeOpponentsEffect(new PermanentsOnBattlefieldCount(filterZergYouControlled), StaticFilters.FILTER_PERMANENT)
                .setText("then each opponent sacrifices a permanent for each Zerg you control"));
        this.addAbility(ability);
    }

    public KerriganQueenOfBlades(final KerriganQueenOfBlades card) {
        super(card);
    }

    @Override
    public KerriganQueenOfBlades copy() {
        return new KerriganQueenOfBlades(this);
    }
}

class KerriganQueenOfBladesFirstEffect extends OneShotEffect {

    public KerriganQueenOfBladesFirstEffect() {
        super(Outcome.BecomeCreature);
        staticText = "Target permanent becomes a Zerg creature with base power and toughness 3/3 in addition to its other types";
    }

    public KerriganQueenOfBladesFirstEffect(final KerriganQueenOfBladesFirstEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanentOrLKIBattlefield(getTargetPointer().getFirst(game, source));
        if (permanent != null) {
            // Target permanent becomes a Zerg creature with base power and toughness 3/3 in addition to its other types.
            if (!permanent.isCreature()) {
                permanent.addCardType(CardType.CREATURE);
            }
            if (!permanent.hasSubtype(SubType.ZERG, game)) {
                permanent.getSubtype(game).add(SubType.ZERG);
            }
            permanent.getPower().modifyBaseValue(3);
            permanent.getToughness().modifyBaseValue(3);
            return true;
        }
        return false;
    }

    @Override
    public KerriganQueenOfBladesFirstEffect copy() {
        return new KerriganQueenOfBladesFirstEffect(this);
    }
}