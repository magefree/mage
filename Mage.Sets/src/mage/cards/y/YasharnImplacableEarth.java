package mage.cards.y;

import java.util.Optional;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.dynamicvalue.common.SubTypeAssignment;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.cards.*;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.costs.common.PayVariableLifeCost;
import mage.abilities.costs.common.SacrificeAllCost;
import mage.abilities.costs.common.SacrificeAttachedCost;
import mage.abilities.costs.common.SacrificeAttachmentCost;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.SacrificeXTargetCost;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.filter.Filter;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

/**
 * @author TheElk801
 */
public final class YasharnImplacableEarth extends CardImpl {

    public YasharnImplacableEarth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{W}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.BOAR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // When Yasharn, Implacable Earth enters the battlefield, search your library for a basic Forest card and a basic Plains card, reveal those cards, put them into your hand, then shuffle your library.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new SearchLibraryPutInHandEffect(new YasharnImplacableEarthTarget(), true)
                        .setText("search your library for a basic Forest card and a basic Plains card, "
                                + "reveal those cards, put them into your hand, then shuffle")
        ));

        // Players can't pay life or sacrifice nonland permanents to cast spells or activate abilities.
        Ability ability = new SimpleStaticAbility(new YasharnImplacableEarthEffect());
        this.addAbility(ability);
    }

    private YasharnImplacableEarth(final YasharnImplacableEarth card) {
        super(card);
    }

    @Override
    public YasharnImplacableEarth copy() {
        return new YasharnImplacableEarth(this);
    }
}

class YasharnImplacableEarthTarget extends TargetCardInLibrary {

    private static final FilterCard filter
            = new FilterCard("a basic Forest card and a basic Plains card");

    static {
        filter.add(SuperType.BASIC.getPredicate());
        filter.add(Predicates.or(
                SubType.FOREST.getPredicate(),
                SubType.PLAINS.getPredicate()
        ));
    }

    private static final SubTypeAssignment subTypeAssigner = new SubTypeAssignment(SubType.FOREST, SubType.PLAINS);

    YasharnImplacableEarthTarget() {
        super(0, 2, filter);
    }

    private YasharnImplacableEarthTarget(final YasharnImplacableEarthTarget target) {
        super(target);
    }

    @Override
    public YasharnImplacableEarthTarget copy() {
        return new YasharnImplacableEarthTarget(this);
    }

    @Override
    public boolean canTarget(UUID playerId, UUID id, Ability source, Game game) {
        if (!super.canTarget(playerId, id, source, game)) {
            return false;
        }
        Card card = game.getCard(id);
        if (card == null) {
            return false;
        }
        if (this.getTargets().isEmpty()) {
            return true;
        }
        Cards cards = new CardsImpl(this.getTargets());
        cards.add(card);
        return subTypeAssigner.getRoleCount(cards, game) >= cards.size();
    }
}

class YasharnImplacableEarthEffect extends ContinuousRuleModifyingEffectImpl {

    public YasharnImplacableEarthEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Neutral);
        staticText = "Players can't pay life or sacrifice nonland permanents to cast spells or activate abilities";
    }

    public YasharnImplacableEarthEffect(final YasharnImplacableEarthEffect effect) {
        super(effect);
    }

    @Override
    public YasharnImplacableEarthEffect copy() {
        return new YasharnImplacableEarthEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public String getInfoMessage(Ability source, GameEvent event, Game game) {
        MageObject mageObject = game.getObject(source);
        if (mageObject != null) {
            return "Players can't pay life or sacrifice nonland permanents to cast spells or activate abilities.  (" + mageObject.getIdName() + ").";
        }
        return null;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        boolean canTargetLand = true;
        Permanent permanent = game.getPermanentOrLKIBattlefield(event.getSourceId());
        if (event.getType() == GameEvent.EventType.ACTIVATE_ABILITY
                || event.getType() == GameEvent.EventType.CAST_SPELL) {
            if (event.getType() == GameEvent.EventType.ACTIVATE_ABILITY) {
                if (permanent == null) {
                    return false;
                }
            }
            Optional<Ability> ability = game.getAbility(event.getTargetId(), event.getSourceId());
            for (Cost cost : ability.get().getCosts()) {
                if (cost instanceof PayLifeCost
                        || cost instanceof PayVariableLifeCost) {
                    return true;  // can't pay with life
                }
                if (cost instanceof SacrificeSourceCost
                        && !permanent.isLand()) {
                    return true;
                }
                if (cost instanceof SacrificeTargetCost) {
                    SacrificeTargetCost sacrificeCost = (SacrificeTargetCost) cost;
                    Filter filter = sacrificeCost.getTargets().get(0).getFilter();
                    for (Object predicate : filter.getPredicates()) {
                        if (predicate instanceof CardType.CardTypePredicate) {
                            if (!predicate.toString().equals("CardType(Land)")) {
                                canTargetLand = false;
                            }
                        }
                    }
                    return !canTargetLand;  // must be nonland target
                }
                if (cost instanceof SacrificeAllCost) {
                    SacrificeAllCost sacrificeAllCost = (SacrificeAllCost) cost;
                    Filter filter = sacrificeAllCost.getTargets().get(0).getFilter();
                    for (Object predicate : filter.getPredicates()) {
                        if (predicate instanceof CardType.CardTypePredicate) {
                            if (!predicate.toString().equals("CardType(Land)")) {
                                canTargetLand = false;
                            }
                        }
                    }
                    return !canTargetLand;  // must be nonland target
                }
                if (cost instanceof SacrificeAttachedCost) {
                    SacrificeAttachedCost sacrificeAllCost = (SacrificeAttachedCost) cost;
                    Filter filter = sacrificeAllCost.getTargets().get(0).getFilter();
                    for (Object predicate : filter.getPredicates()) {
                        if (predicate instanceof CardType.CardTypePredicate) {
                            if (!predicate.toString().equals("CardType(Land)")) {
                                canTargetLand = false;
                            }
                        }
                    }
                    return !canTargetLand;  // must be nonland target
                }
                if (cost instanceof SacrificeAttachmentCost) {
                    SacrificeAttachmentCost sacrificeAllCost = (SacrificeAttachmentCost) cost;
                    Filter filter = sacrificeAllCost.getTargets().get(0).getFilter();
                    for (Object predicate : filter.getPredicates()) {
                        if (predicate instanceof CardType.CardTypePredicate) {
                            if (!predicate.toString().equals("CardType(Land)")) {
                                canTargetLand = false;
                            }
                        }
                    }
                    return !canTargetLand;  // must be nonland target
                }

                if (cost instanceof SacrificeXTargetCost) {
                    SacrificeXTargetCost sacrificeCost = (SacrificeXTargetCost) cost;
                    Filter filter = sacrificeCost.getFilter();
                    for (Object predicate : filter.getPredicates()) {
                        if (predicate instanceof CardType.CardTypePredicate) {
                            if (!predicate.toString().equals("CardType(Land)")) {
                                canTargetLand = false;
                            }
                        }
                    }
                    return !canTargetLand;  // must be nonland target
                }
            }
        }
        return false;
    }
}
