package mage.cards.s;

import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.keyword.InvestigateEffect;
import mage.abilities.triggers.BeginningOfCombatTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.GoadedPredicate;
import mage.game.Game;

import java.util.UUID;

/**
 *
 * @author Jmlundeen
 */
public final class SereneSleuth extends CardImpl {

    private final static FilterPermanent filter = new FilterControlledCreaturePermanent("goaded creatures you control");

    static {
        filter.add(GoadedPredicate.instance);
    }

    public SereneSleuth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.DETECTIVE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Serene Sleuth enters the battlefield, investigate.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new InvestigateEffect()));

        // At the beginning of combat on your turn, investigate for each goaded creature you control. Then each creature you control is no longer goaded.
        Ability triggeredAbility = new BeginningOfCombatTriggeredAbility(
                new InvestigateEffect(new PermanentsOnBattlefieldCount(filter))
                        .setText("investigate for each goaded creature you control.")
        );
        triggeredAbility.addEffect(new SereneSleuthEffect(filter)
                .concatBy("Then"));
        this.addAbility(triggeredAbility);
    }

    private SereneSleuth(final SereneSleuth card) {
        super(card);
    }

    @Override
    public SereneSleuth copy() {
        return new SereneSleuth(this);
    }
}
class SereneSleuthEffect extends ContinuousEffectImpl {

    private MageObjectReference sourceMor;
    private final FilterPermanent filter;

    public SereneSleuthEffect(FilterPermanent filter) {
        super(Duration.Custom, Layer.RulesEffects, SubLayer.NA, Outcome.Benefit);
        staticText = "each creature you control is no longer goaded";
        this.filter = filter;
    }

    private SereneSleuthEffect(final SereneSleuthEffect effect) {
        super(effect);
        this.sourceMor = effect.sourceMor;
        this.filter = effect.filter;
    }

    public SereneSleuthEffect copy() {
        return new SereneSleuthEffect(this);
    }

    public MageObjectReference getSourceMor() {
        return sourceMor;
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        // discard previous effect if it exists to not clutter
        for (ContinuousEffect effect : game.getContinuousEffects().getLayeredEffects(game)) {
            if (effect instanceof SereneSleuthEffect) {
                MageObjectReference effectsMor = ((SereneSleuthEffect) effect).getSourceMor();
                if (effectsMor != null && effectsMor.refersTo(source.getSourceId(), game)) {
                    effect.discard();
                    this.affectedObjectList.addAll(effect.getAffectedObjects());
                }
            }
        }
        sourceMor = new MageObjectReference(source.getSourceObject(game), game);
        setAffectedObjectsSet(true);
        game.getBattlefield()
                .getActivePermanents(
                        filter, source.getControllerId(), source, game
                ).stream()
                .map(permanent -> new MageObjectReference(permanent, game))
                .forEach(mor -> {
                    if (!this.affectedObjectList.contains(mor)) {
                        this.affectedObjectList.add(mor);
                    }
                });
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (getAffectedObjectsSet()) {
            this.affectedObjectList.removeIf(mor -> !mor.zoneCounterIsCurrent(game)
                    || mor.getPermanent(game) == null);
            if (affectedObjectList.isEmpty()) {
                discard();
                return false;
            }
            for (MageObjectReference mor : this.affectedObjectList) {
                mor.getPermanent(game).getGoadingPlayers().clear();
            }
            return true;
        }
        return true;
    }
}