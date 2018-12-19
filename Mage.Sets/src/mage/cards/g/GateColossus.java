package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.common.SimpleEvasionAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.PutOnLibrarySourceEffect;
import mage.abilities.effects.common.combat.CantBeBlockedByCreaturesSourceEffect;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GateColossus extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creatures with power 2 or less");

    static {
        filter.add(new PowerPredicate(ComparisonType.FEWER_THAN, 3));
    }

    public GateColossus(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{8}");

        this.subtype.add(SubType.CONSTRUCT);
        this.power = new MageInt(8);
        this.toughness = new MageInt(8);

        // This spell costs {1} less to cast for each Gate you control.
        this.addAbility(new SimpleStaticAbility(Zone.STACK, new GateColossusCostReductionEffect()));

        // Gate Colossus can't be blocked by creatures with power 2 or less.
        this.addAbility(new SimpleEvasionAbility(new CantBeBlockedByCreaturesSourceEffect(filter, Duration.WhileOnBattlefield)));

        // Whenever a Gate enters the battlefield under your control, you may put Gate Colossus from your graveyard on top of your library.
        this.addAbility(new EntersBattlefieldAllTriggeredAbility(
                Zone.GRAVEYARD,
                new PutOnLibrarySourceEffect(
                        true, "put {this} from your graveyard on top of your library"
                ), GateColossusCostReductionEffect.filter, true
        ));
    }

    public GateColossus(final GateColossus card) {
        super(card);
    }

    @Override
    public GateColossus copy() {
        return new GateColossus(this);
    }
}

class GateColossusCostReductionEffect extends CostModificationEffectImpl {

    static final FilterControlledPermanent filter = new FilterControlledPermanent();

    static {
        filter.add(new SubtypePredicate(SubType.GATE));
    }

    public GateColossusCostReductionEffect() {
        super(Duration.WhileOnStack, Outcome.Benefit, CostModificationType.REDUCE_COST);
        staticText = "This spell costs {1} less to cast for each Gate you control";
    }

    protected GateColossusCostReductionEffect(final GateColossusCostReductionEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        int count = game.getBattlefield().getAllActivePermanents(filter, source.getControllerId(), game).size();
        if (count > 0) {
            CardUtil.reduceCost(abilityToModify, count);
        }
        return true;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        return abilityToModify.getSourceId().equals(source.getSourceId());
    }

    @Override
    public GateColossusCostReductionEffect copy() {
        return new GateColossusCostReductionEffect(this);
    }
}
