package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.GateYouControlCount;
import mage.abilities.effects.common.PutOnLibrarySourceEffect;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.abilities.effects.common.cost.SpellCostReductionForEachSourceEffect;
import mage.abilities.hint.common.GateYouControlHint;
import mage.abilities.keyword.DauntAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GateColossus extends CardImpl {

    public GateColossus(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{8}");

        this.subtype.add(SubType.CONSTRUCT);
        this.power = new MageInt(8);
        this.toughness = new MageInt(8);

        // This spell costs {1} less to cast for each Gate you control.
        this.addAbility(new SimpleStaticAbility(Zone.ALL,
                new SpellCostReductionForEachSourceEffect(1, GateYouControlCount.instance))
                .addHint(GateYouControlHint.instance)
        );

        // Gate Colossus can't be blocked by creatures with power 2 or less.
        this.addAbility(new DauntAbility());

        // Whenever a Gate enters the battlefield under your control, you may put Gate Colossus from your graveyard on top of your library.
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(
                Zone.GRAVEYARD,
                new PutOnLibrarySourceEffect(
                        true, "put {this} from your graveyard on top of your library"
                ), GateColossusCostReductionEffect.filter, true
        ));
    }

    private GateColossus(final GateColossus card) {
        super(card);
    }

    @Override
    public GateColossus copy() {
        return new GateColossus(this);
    }
}

class GateColossusCostReductionEffect extends CostModificationEffectImpl {

    static final FilterControlledPermanent filter = new FilterControlledPermanent("a Gate");

    static {
        filter.add(SubType.GATE.getPredicate());
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
        CardUtil.reduceCost(abilityToModify, count);
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
