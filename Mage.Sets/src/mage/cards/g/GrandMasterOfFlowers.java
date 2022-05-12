package mage.cards.g;

import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.combat.CantAttackTargetEffect;
import mage.abilities.effects.common.combat.CantBlockTargetEffect;
import mage.abilities.effects.common.search.SearchLibraryGraveyardPutInHandEffect;
import mage.abilities.keyword.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GrandMasterOfFlowers extends CardImpl {

    private static final FilterPermanent filter
            = new FilterCreaturePermanent("creature without first strike, double strike, or vigilance");
    private static final FilterCard filter2
            = new FilterCard("Monk of the Open Hand");

    static {
        filter.add(Predicates.not(new AbilityPredicate(FirstStrikeAbility.class)));
        filter.add(Predicates.not(new AbilityPredicate(DoubleStrikeAbility.class)));
        filter.add(Predicates.not(new AbilityPredicate(VigilanceAbility.class)));
        filter2.add(new NamePredicate("Monk of the Open Hand"));
    }

    public GrandMasterOfFlowers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{2}{W}{W}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.BAHAMUT);
        this.setStartingLoyalty(3);

        // As long as Grand Master of Flowers has seven or more loyalty counters on him, he's a 7/7 Dragon God creature with flying and indestructible.
        this.addAbility(new SimpleStaticAbility(new GrandMasterOfFlowersEffect()));

        // +1: Target creature without first strike, double strike, or vigilance can't attack or block until your next turn.
        Ability ability = new LoyaltyAbility(new CantAttackTargetEffect(Duration.UntilYourNextTurn)
                .setText("target creature without first strike, double strike"), 1);
        ability.addEffect(new CantBlockTargetEffect(Duration.UntilYourNextTurn)
                .setText(", or vigilance can't attack or block until your next turn"));
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);

        // +1: Search your library and/or graveyard for a card named Monk of the Open Hand, reveal it, and put it into your hand. If you search your library this way, shuffle it.
        this.addAbility(new LoyaltyAbility(new SearchLibraryGraveyardPutInHandEffect(
                filter2, false, false
        ), 1));
    }

    private GrandMasterOfFlowers(final GrandMasterOfFlowers card) {
        super(card);
    }

    @Override
    public GrandMasterOfFlowers copy() {
        return new GrandMasterOfFlowers(this);
    }
}

class GrandMasterOfFlowersEffect extends ContinuousEffectImpl {

    GrandMasterOfFlowersEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "as long as {this} has seven or more loyalty counters on him, " +
                "he's a 7/7 Dragon God creature with flying and indestructible";
    }

    private GrandMasterOfFlowersEffect(final GrandMasterOfFlowersEffect effect) {
        super(effect);
    }

    @Override
    public GrandMasterOfFlowersEffect copy() {
        return new GrandMasterOfFlowersEffect(this);
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        if (permanent == null || permanent.getCounters(game).getCount(CounterType.LOYALTY) < 7) {
            return false;
        }
        switch (layer) {
            case TypeChangingEffects_4:
                permanent.removeAllCardTypes(game);
                permanent.addCardType(game, CardType.CREATURE);
                permanent.removeAllSubTypes(game);
                permanent.addSubType(game, SubType.DRAGON);
                permanent.addSubType(game, SubType.GOD);
                return true;
            case AbilityAddingRemovingEffects_6:
                permanent.addAbility(FlyingAbility.getInstance(), source.getSourceId(), game);
                permanent.addAbility(IndestructibleAbility.getInstance(), source.getSourceId(), game);
                return true;
            case PTChangingEffects_7:
                if (sublayer == SubLayer.SetPT_7b) {
                    permanent.getPower().setValue(7);
                    permanent.getToughness().setValue(7);
                    return true;
                }
        }
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean hasLayer(Layer layer) {
        switch (layer) {
            case TypeChangingEffects_4:
            case AbilityAddingRemovingEffects_6:
            case PTChangingEffects_7:
                return true;
        }
        return false;
    }
}
